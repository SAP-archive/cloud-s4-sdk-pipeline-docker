FROM buildpack-deps:stretch-curl

ENV VERSION 0.1

# ps needs to be available to be able to be used in docker.inside, see https://issues.jenkins-ci.org/browse/JENKINS-40101
RUN apt-get update && apt-get install -y --no-install-recommends procps && rm -rf /var/lib/apt/lists/*

# add group & user
RUN addgroup -gid 1000 piper
RUN useradd piper --uid 1000 --gid 1000 --shell /bin/bash --create-home

# install cf command line and blue-green plugin
RUN curl -L "https://cli.run.pivotal.io/stable?release=linux64-binary&source=github" | tar -zx -C /usr/local/bin && \
    cf --version

USER piper

WORKDIR /home/piper

RUN cf add-plugin-repo CF-Community https://plugins.cloudfoundry.org && \
    cf install-plugin blue-green-deploy -f -r CF-Community
