FROM node:10-alpine

ARG npm_registry=https://registry.npmjs.org/

RUN npm config set registry=$npm_registry

WORKDIR /cx-server

COPY package.json .
COPY package-lock.json .

RUN npm install

RUN npm config delete registry

COPY checkversion.js .
COPY updateversion.js .

# Install dependencies for running the cx server script in this container
RUN apk add --no-cache bash docker curl
COPY cx-server-companion.sh .
# If the repository was cloned on Windows, the script might have \CR\LF line endings. Ensure it has only \LF.
RUN dos2unix cx-server-companion.sh
RUN chmod +x cx-server-companion.sh
