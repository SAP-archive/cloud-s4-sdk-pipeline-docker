# Dockerfiles for the SAP S/4HANA Cloud SDK Pipeline
 
## Description

This collection of Dockerfiles build the foundation for the Docker images used by the [Pipeline for the SAP S/4HANA Cloud SDK](https://github.com/SAP/cloud-s4-sdk-pipeline).
It contains two type of Dockerfiles. 
The folder s4sdk-jenkins-master contains a Dockerfile for a Jenkins server, which is preconfigured to run the pipeline.
The other folders contain Dockerfiles for Docker images which are used in the pipeline. 

## Requirements

In order to run and build the Docker images you have to install [Docker](https://www.docker.com/).

## Download and Installation

To build a Docker image, such as s4sdk-jenkins-master, go to this folder and execute the following command:
```shell
 docker build -t jenkins-master-image .
```
 
Afterwards the image is avaialbe and a new container can be spawned with following command:
```shell
 docker run -p 8080:8080 --name my-jenkins jenkins-master-image
```

The same applies for all other Dockerfiles.

## Known Issues
Currently, there are no known issues.

## How to obtain support
If you need any support, have any question or have found a bug, please report it as issue in the repository.

## License
Copyright (c) 2017 SAP SE or an SAP affiliate company. All rights reserved.
This file is licensed under the Apache Software License, v. 2 except as noted otherwise in the [LICENSE file](LICENSE).
