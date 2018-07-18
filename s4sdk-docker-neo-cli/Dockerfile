FROM openjdk:8-jdk

RUN apt-get update 
RUN apt-get install -y maven

RUN mvn com.sap.cloud:neo-javaee6-wp-maven-plugin:2.139.3.1:install-sdk -DsdkInstallPath=sdk -Dincludes=tools/**,license/**,sdk.version

RUN chmod -R 777 sdk && \
    ln -s /sdk/tools/neo.sh /usr/bin/neo.sh
