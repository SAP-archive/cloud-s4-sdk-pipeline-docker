# node-10 is LTS and required for sap approuter. EOL: 2021-04-01
FROM node:10-stretch 

RUN apt-get update && \
    apt-get install -y chromium=70.0.3538.110-1~deb9u1 firefox-esr xvfb libxi6 libgconf-2-4 default-jre && \
    rm -rf /var/lib/apt/lists/* /var/cache/apt/*

RUN ln -s /usr/bin/chromium /usr/bin/google-chrome

RUN npm config set @sap:registry https://npm.sap.com --global

USER node
