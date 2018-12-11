#!/usr/bin/env node

'use strict';

if (!process.argv[2]) {
    console.error("Missing parameter: Configuration object is not specified");
    process.exit(-1);
}

const configString = process.argv[2];
const appConfig = JSON.parse(configString);

const expectDownloadCacheIsRunning = Boolean(appConfig.cache_enabled) || (appConfig.cache_enabled == '');

const {
    spawnSync
} = require('child_process');
const ps = spawnSync('docker', ['ps', '--no-trunc', '--format', '{{ json . }}', '--filter', 'name=s4sdk']);

const containers = ps.stdout.toString().split('\n').filter(line => line.length > 3).map(jsonLine => JSON.parse(jsonLine))

if (expectDownloadCacheIsRunning) {
    if (containers.filter(c => c.Names.includes("s4sdk-nexus")).length == 0) {
        console.error("⚠️ Expected Download cache to be running, but it is not. Most likely, this is caused by low memory in Docker." + 
        "To fix this, please ensure that Docker has at least 4 GB memory, and restart Cx Server.")
    }
}

if (containers.length == 0) {
    console.log('Cx Server is not running.')
} else {
    console.log('Running Cx Server containers:')
    console.log(spawnSync('docker', ['ps', '--filter', 'name=s4sdk']).stdout.toString())
}

