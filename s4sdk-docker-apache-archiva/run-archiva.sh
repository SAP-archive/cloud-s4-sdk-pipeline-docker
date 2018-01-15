#!/bin/bash

set VERSION=2.2.3

# apply ARCHIVA_CONTEXT_PATH
sed -i "s|name=\"contextPath\">/<|name=\"contextPath\">$ARCHIVA_CONTEXT_PATH<|g" \
  /opt/archiva/apache-archiva-$VERSION/contexts/archiva.xml

# create config and data dirs if needed
# allows archiva to start fresh, or have these dirs mounted in from the host
if [ ! -d /var/archiva/logs ]; then
  mkdir -p /var/archiva/logs
fi

if [ ! -d /var/archiva/data ]; then
  mkdir -p /var/archiva/data
fi

if [ ! -d /var/archiva/temp ]; then
  mkdir -p /var/archiva/temp
fi

if [ ! -d /var/archiva/conf ]; then
  cp -r /opt/archiva/apache-archiva-$VERSION/conf /var/archiva
fi

# start archiva in console mode to keep in foreground
/opt/archiva/apache-archiva-$VERSION/bin/archiva console

