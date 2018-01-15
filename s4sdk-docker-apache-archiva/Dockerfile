FROM openjdk:8u121

ENV VERSION 2.2.3

### dependencies
RUN apt-get update \
    && apt-get install -y \
    wget \
    && apt-get clean \
    && apt-get autoclean \
    && apt-get autoremove

### download archiva binary
WORKDIR /opt/archiva
RUN wget http://apache.spinellicreations.com/archiva/$VERSION/binaries/apache-archiva-$VERSION-bin.tar.gz \
    && tar -xzf apache-archiva-$VERSION-bin.tar.gz

### separate config and data dirs from binary
ENV ARCHIVA_BASE /var/archiva

### expose contextPath as environment variable
ENV ARCHIVA_CONTEXT_PATH /

ADD run-archiva.sh /opt/archiva/run-archiva.sh

### start archiva, creating config and data dirs if needed
### allows starting fresh or mounting in ARCHIVA_BASE from host
CMD ["/opt/archiva/run-archiva.sh"]
