FROM docker.wdf.sap.corp:51162/s4sdk/jenkins-master:latest

USER root

# download SAP root certificates
RUN curl -sSL -f -k http://aia.pki.co.sap.com/aia/SAPNetCA_G2.crt -o /usr/local/share/ca-certificates/SAPNetCA_G2.crt && \
    curl -sSL -f -k http://aia.pki.co.sap.com/aia/SAP%20Global%20Root%20CA.crt -o /usr/local/share/ca-certificates/SAP%20Global%20Root%20CA.crt && \
    keytool -noprompt -import -alias sapglobalrootca -file /usr/local/share/ca-certificates/SAP%20Global%20Root%20CA.crt -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit && \
    keytool -noprompt -import -alias sapnetcag2 -file /usr/local/share/ca-certificates/SAPNetCA_G2.crt  -keystore /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts -storepass changeit && \
    update-ca-certificates

COPY proxy.xml /usr/share/jenkins/ref/proxy.xml

# copy default certificates
COPY ssl/cert /var/jenkins_ssl/cert
COPY ssl/pk /var/jenkins_ssl/pk
# copy ssh setup script
COPY ssh_setup.sh /var/jenkins_ssl/ssh_setup.sh
RUN chown -R jenkins /var/jenkins_ssl

USER jenkins

ENV http_proxy http://proxy.wdf.sap.corp:8080
ENV https_proxy http://proxy.wdf.sap.corp:8080
ENV no_proxy *.sap.corp,*.corp.sap,.sap.corp,.corp.sap,localhost

#For 2.x-derived images, to indicate that this Jenkins installation is fully configured.
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

VOLUME /var/jenkins_ssl

ENV JENKINS_OPTS "${JENKINS_OPTS} --httpsPort=1443 --httpsCertificate=/var/jenkins_ssl/cert --httpsPrivateKey=/var/jenkins_ssl/pk"
ENV JAVA_OPTS "${JAVA_OPTS} -Dhttp.proxyHost=proxy.wdf.sap.corp -Dhttp.proxyPort=8080 -Dhttps.proxyHost=proxy.wdf.sap.corp -Dhttps.proxyPort=8080 -Dhttp.nonProxyHosts=localhost|*.sap.corp|*.corp.sap"

EXPOSE 1443
