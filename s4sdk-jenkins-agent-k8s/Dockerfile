FROM jenkins/jnlp-slave:latest
USER root

# add group & user
RUN addgroup -gid 1000 piper
RUN useradd piper --uid 1000 --gid 1000 --shell /bin/bash --create-home


USER piper
WORKDIR /home/piper
