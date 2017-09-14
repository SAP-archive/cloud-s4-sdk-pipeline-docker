FROM openjdk:8-jdk

RUN apt-get update 
RUN apt-get install -y maven

RUN mvn com.sap.cloud:neo-java-web-maven-plugin:1.133.4:install-sdk -DsdkInstallPath=sdk -Dincludes=tools/**,license/**,sdk.version

RUN chmod -R 777 sdk