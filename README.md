# Dockerfiles for the SAP S/4HANA Cloud SDK Pipeline
 
## Description

This collection of Dockerfiles build the foundation for the Docker images used by the [Pipeline for the SAP S/4HANA Cloud SDK](https://github.com/SAP/cloud-s4-sdk-pipeline).
It contains two type of Dockerfiles. 
The folder s4sdk-jenkins-master contains a Dockerfile for a Jenkins server, which is preconfigured to run the pipeline.
The other folders contain Dockerfiles for Docker images which are used in the pipeline to run steps, such as deployments to the SAP Cloud Platform. 

## Requirements

In order to run and build the Docker images you have to install [Docker](https://www.docker.com/).

## Download and Installation

To build a Docker image, such as s4sdk-jenkins-master, go to this folder and execute the following command:
```shell
 $ docker build -t jenkins-master-image .
```
 
Afterwards the image is avaialbe and a new container can be spawned with following command:
```shell
 $ docker run -p 8080:8080 --name my-jenkins jenkins-master-image
```

The same applies for all other Dockerfiles.

The SAP S/4HANA Cloud SDK already provides a script called `cx-server` to avoid these manual steps. It can be found in the same named folder on the root of each SAP S/4HANA Cloud SDK project archetype. Together with the server.cfg file, this is all you need for starting your instance of the SAP S/4HANA Cloud SDK Cx Server.  
For instantiating the SAP S/4HANA Cloud SDK Cx Server, you need to provide a suitable host with a linux operating system and Docker installed. Please also ensure that the user with whom you start the Cx Server belongs to the [docker group](https://docs.docker.com/engine/installation/linux/linux-postinstall/).

To create a new project using the SDK execute the following command:
 
 ```shell
  $ mvn archetype:generate -DarchetypeGroupId=com.sap.cloud.s4hana.archetypes -DarchetypeArtifactId=scp-cf-tomee -DarchetypeVersion=1.0.0
 ```
 
 In the new project there is a folder called cx-server.
 This folder needs to be copied to the future host on which the Cx Server is intended to run.
 
 On the host machine execute the following command in the folder cx-server.
 This will start the Jenkins server.
 ```shell
  $ ./cx-server start
 ```

## Known Issues
Currently, there are no known issues.

## How to obtain support
If you need any support, have any question or have found a bug, please report it as issue in the repository.

## License
Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
This file is licensed under the Apache Software License, v. 2 except as noted otherwise in the [LICENSE file](LICENSE).
