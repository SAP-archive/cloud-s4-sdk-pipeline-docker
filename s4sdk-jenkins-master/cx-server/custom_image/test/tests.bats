#!/usr/bin/env bats

SUT_IMAGE=bats-jenkins2-sap
SUT_CONTAINER=bats-jenkins2-sap

load test_helpers

@test "build image" {
  cd $BATS_TEST_DIRNAME/..
  docker build -t $SUT_IMAGE .
}

@test "check proxy configuration" {
  run docker rm -f $SUT_CONTAINER

  run docker run --rm -i --entrypoint curl $SUT_IMAGE -Lv http://www.sap.com
  echo $output | grep "HTTP/1.1 200 OK"
  [ "$status" -eq 0 ]
}

@test "check that SAP root certificates are available in Java keystore" {
  run docker rm -f $SUT_CONTAINER

  run docker run --rm -i --entrypoint keytool $SUT_IMAGE -list -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit
  
  echo $output | grep 'sapglobalrootca'
  echo $output | grep 'sapnetcag2'
}

@test "check that SAP root certificates are available in Linux keystore" {
  run docker rm -f $SUT_CONTAINER

  run docker run --rm -i --entrypoint curl $SUT_IMAGE https://github.wdf.sap.corp
  [ "$status" -eq 0 ]
}

@test "Jenkins running successfully" {
    run docker rm -f $SUT_CONTAINER

    run docker run -d --name $SUT_CONTAINER -p 8099:8080 $SUT_IMAGE
    echo $output
    [ "$status" -eq 0 ]
    
    run get_jenkins_url
    echo "Tested URL: $output" >> test_trace.log

    run retry 30 5 test_url /api/json
    [ "$status" -eq 0 ]

    docker rm -f $SUT_CONTAINER
}

@test "check that docker can be executed by jenkins user" {
    run docker rm -f $SUT_CONTAINER
    uid=$(id -u)
    
    echo "UID: ${uid}"

    [ ! -z "${uid}" ]

    mkdir jenkins_home

    run docker run -u ${uid} -v ${PWD}/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --rm -i $SUT_IMAGE docker ps
    echo $output
    [ "$status" -eq 0 ]
}


