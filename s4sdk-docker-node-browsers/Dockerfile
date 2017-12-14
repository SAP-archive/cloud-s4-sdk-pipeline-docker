FROM node:stretch

RUN apt-get update && \
    apt-get install -y chromium firefox-esr xvfb && \
    rm -rf /var/lib/apt/lists/* /var/cache/apt/*

RUN ln -s /usr/bin/chromium /usr/bin/google-chrome

RUN npm config set @sap:registry https://npm.sap.com
