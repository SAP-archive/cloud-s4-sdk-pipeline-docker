import jenkins.model.*

def instance = Jenkins.instance

if(instance.getNumExecutors() < 4) {
    instance.setNumExecutors(4)
}