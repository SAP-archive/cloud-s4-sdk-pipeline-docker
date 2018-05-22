# Cx Server Development Guide

The Cx Server script was moved into the companion Docker image, the remaining script is only a wrapper to invoke Docker.
This allows us to make the Cx Server work on Windows easily.

This has a few consequences for developing the script, which are described in this document.

When you make changes to `s4sdk-cxserver-companion/cx-server-companion.sh`, you need to build the `s4sdk/cxserver-companion` image locally.
From this directory (`s4sdk-jenkins-master/cx-server`), the command to do so is:

```bash
docker build -t s4sdk/cxserver-companion ../../s4sdk-cxserver-companion
```

Usually, when running `cx-server`, the companion image is automatically pulled from Docker Hub.
This is designed for simple usage, but if you've built the image yourself, it will overwrite your changes.
To prevent this, set the environment variable `DEVELOPER_MODE` to _any_ value.

Run in developer mode on Bash
```bash
export DEVELOPER_MODE=1
./cx-server [command]
```

Run in developer mode in `cmd.exe`
```
set DEVELOPER_MODE=1
cx-server.bat [command]
```

Run in developer mode in Powershell
```
$env:DEVELOPER_MODE = 1
cx-server.bat [command]
```

To switch back to the "normal" behavior, unset the environment variable `DEVELOPER_MODE`.

Also __please note__ that the `cx-server` script always worked with the assumption that it was invoked from its directory, which is still the case.
