#!/usr/bin/env groovy

@Library(['pipelines-testing-lib'])

def dockerImages = [
        ['folder': 's4sdk-cxserver-companion', 'name': 's4sdk/cxserver-companion'],
        ['folder': 's4sdk-docker-cf-cli', 'name': 's4sdk/docker-cf-cli'],
        ['folder': 's4sdk-docker-neo-cli', 'name': 's4sdk/docker-neo-cli'],
        ['folder': 's4sdk-docker-node-browsers', 'name': 's4sdk/docker-node-browsers'],
        ['folder': 's4sdk-jenkins-master', 'name': 's4sdk/jenkins-master'],
        ['folder': 's4sdk-jenkins-agent', 'name': 's4sdk/jenkins-agent'],
        ['folder': 's4sdk-docker-maven-npm', 'name': 's4sdk/docker-maven-npm'],
        ['folder': 's4sdk-jenkins-agent-k8s', 'name': 's4sdk/jenkins-agent-k8s']
]

node {
    properties([
        buildDiscarder(logRotator(daysToKeepStr: '5', numToKeepStr: '5')),
    ])
    try {
        buildDockerImages(dockerImages)
    } finally {
        final int GRACE_PERIOD_THREE_DAYS = 3600 * 24 * 3
        cleanUpDocker script: this, excludeContainers: ['s4sdk-nexus', 's4sdk-jenkins-master', 'nexus'], forceContainerRemoval: 1, forceImageRemoval: 1, gracePeriodSeconds: GRACE_PERIOD_THREE_DAYS
    }
}
