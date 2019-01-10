#!/bin/bash

mongod --config /etc/mongod.conf --fork
./blinkr.sh "$@"

