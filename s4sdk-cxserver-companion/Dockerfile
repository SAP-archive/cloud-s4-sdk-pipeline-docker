FROM node:9-alpine

ARG npm_registry=https://registry.npmjs.org/

RUN npm config set registry=$npm_registry

WORKDIR /cx-server

COPY package.json .
COPY package-lock.json .

RUN npm install

RUN npm config delete registry

COPY checkversion.js .
COPY updateversion.js .
