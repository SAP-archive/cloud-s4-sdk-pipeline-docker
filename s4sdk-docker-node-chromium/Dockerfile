FROM node:stretch
RUN apt-get update && apt-get install -y chromium libxi6 libgconf-2-4 xvfb x11vnc
RUN ln -s /usr/bin/chromium /usr/bin/google-chrome

RUN npm config set @sap:registry https://npm.sap.com