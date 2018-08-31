#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Please specify release binary"
    exit 1
fi

BINARY_FILE=$1
SYSTEM_DIR="system"

mkdir $SYSTEM_DIR

tar -xzvf $BINARY_FILE -C $SYSTEM_DIR

