FROM openjdk:8-jre-slim

ENV JENKINS_HOME /var/jenkins_home
  
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
         apt-transport-https \
         ca-certificates \
         curl \
         git \
         gnupg \
         openssh-client \
         software-properties-common && \
    curl -fsSL https://apt.dockerproject.org/gpg | apt-key add - && \
    apt-key fingerprint 58118E89F3A912897C070ADBF76221572C52609D && \
    add-apt-repository "deb https://apt.dockerproject.org/repo/ debian-$(lsb_release -cs) main" && \
    apt-get update && \
    apt-get -y install docker-engine && \
    rm -rf /var/lib/apt/lists/* /var/cache/apt/*

# add group & user
RUN addgroup -gid 1000 jenkins
RUN useradd jenkins -d "$JENKINS_HOME" --uid 1000 --gid 1000 --shell /bin/bash --create-home

VOLUME /var/jenkins_home

USER jenkins
