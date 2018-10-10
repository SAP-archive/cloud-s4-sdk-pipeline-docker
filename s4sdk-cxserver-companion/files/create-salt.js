#!/usr/bin/env node

'use strict';

/***
 * Write a salt for hashing sensitive data.
 * The salt is private to the premises where cx-server is running and never transmitted over the wire.
 */

const fs = require('fs');
const crypto = require('crypto');

const salt = crypto.randomBytes(20).toString('hex')
const jenkinsHome = '/jenkins_home'
const saltFile = `${jenkinsHome}/s4sdk-salt`

if (fs.existsSync(jenkinsHome) && !fs.existsSync(saltFile)) {
    try {
        fs.writeFileSync(saltFile, salt, {
            encoding: 'utf8',
            mode: 0o666
        })
    } catch (exception) {
        console.log(`Could not write salt file, with exception: ${exception}`)
    }
}
