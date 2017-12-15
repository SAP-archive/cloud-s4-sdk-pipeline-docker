#!/usr/bin/env groovy

@Library(['pipelines-testing-lib'])

def dockerImages = [
  ['folder': 's4sdk-docker-cf-cli', 'name': 's4sdk/docker-cf-cli'],
  ['folder': 's4sdk-docker-neo-cli', 'name': 's4sdk/docker-neo-cli'],
  ['folder': 's4sdk-docker-node-browsers', 'name': 's4sdk/docker-node-browsers'],
  ['folder': 's4sdk-docker-node-chromium', 'name': 's4sdk/docker-node-chromium'],
  ['folder': 's4sdk-jenkins-master', 'name': 's4sdk/jenkins-master']
]

node {
  buildDockerImages(dockerImages)
}
