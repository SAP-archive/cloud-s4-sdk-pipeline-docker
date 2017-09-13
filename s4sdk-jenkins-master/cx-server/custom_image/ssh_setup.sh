#!/bin/bash -ex

if [ "$#" -ne 2 ]; then
  echo "Usage: $0 <GitHub-Email> <DescriptionOnGitHub>" >&2
  exit 1
fi

clear
echo 'This script helps you to configure SSH keys for your Docker Jenkins instance'

privatekey=/var/jenkins_home/.ssh/id_rsa
directory=$(dirname $privatekey)

email=$1
description=$2

echo 'Adding github.wdf.sap.corp to list of known hosts (/var/jenkins_home/.ssh/known_hosts)...'
mkdir -p $directory
ssh-keyscan -H github.wdf.sap.corp > $directory/known_hosts

if [ -e "$privatekey" ]; then
  echo 'Private key already exists...'ssh
else
  echo 'Generating public/private key pair...'
  echo -e 'y\n' | ssh-keygen -t rsa -b 4096 -C ${email} -f $directory/id_rsa -N ''
fi

echo
echo 'The public key now will be maintained in your Github account.'
echo -e '\033[31mIMPORTANT:\e[0m enter your \033[31mGITHUB\e[0m password when you will be prompted!'
header='Content-Type: application/json'
body='{ "title": "'$description'", "key": "'$(<$directory/id_rsa.pub)'" }'
curl -H "$header" -X POST -u "$email" -d "$body" --fail https://github.wdf.sap.corp/api/v3/user/keys