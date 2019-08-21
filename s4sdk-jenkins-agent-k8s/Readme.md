# s4sdk-jenkins-agent-k8s
:exclamation: **DEPRECATED!** This image is deprecated. Please use the [jenkins/jnlp-slave](https://hub.docker.com/r/jenkins/jnlp-slave/) instead.


This is a docker version of Jenkins JNLP agent  which is an extension to [jenkinsci/jnlp-slave](https://hub.docker.com/r/jenkins/jnlp-slave/).

The s4sdk/jenkins-master runs with a user id `1000`. Hence, a user **piper** with uid **1000** has been added to the JNLP
agent as well avoid the possible issue with the access rights of the files that needs to be accessed by both master and the agent. 
