#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Please specify release binary"
    exit 1
fi

BINARY_FILE=$1
SYSTEM_DIR="system"

mkdir $SYSTEM_DIR

cd $SYSTEM_DIR
rm *.jar
rm -rf *.lib
cd ..

tar -xzvf $BINARY_FILE -C $SYSTEM_DIR

