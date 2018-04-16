FROM node:9-alpine

WORKDIR /cx-server

COPY package.json .
COPY package-lock.json .

RUN npm install

COPY checkversion.js .
COPY updateversion.js .