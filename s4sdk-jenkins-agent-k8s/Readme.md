# s4sdk-jenkins-agent-k8s

This is a docker version of Jenkins JNLP agent  which is an extension to [jenkinsci/jnlp-slave](https://hub.docker.com/r/jenkinsci/jnlp-slave/).

A user **piper** with uid **1000** has been added to avoid the file permission issue in the pod. 