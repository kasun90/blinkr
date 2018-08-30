#!/bin/bash

if [ $# -lt 2 ]; then
    echo "Please specify valid arguments: <root-dir> <version>"
    exit 1
fi

SOURCE_DIR="source"
RELEASE_DIR="release"


mkdir $SOURCE_DIR
mkdir $RELEASE_DIR
cd $SOURCE_DIR
git clone git@github.com:kasun90/blinkr.git .
mvn versions:set -DnewVersion=$2
mvn clean install -DskipTests

if [ $? -ne 0 ]; then
    echo "Maven step failed. Exiting"
    exit 1
fi

TAG_NAME="v${1}"
git tag -a $TAG_NAME -m "Blinkr Release ${TAG_NAME}"
git push origin $TAG_NAME


cd ..
cp $SOURCE_DIR/$1/target/blinkr-$2.jar $RELEASE_DIR
cp -r $SOURCE_DIR/$1/target/blinkr-$2.lib $RELEASE_DIR
cp -r $SOURCE_DIR/static/ $RELEASE_DIR
cp $SOURCE_DIR/blink.conf $RELEASE_DIR
cp $SOURCE_DIR/system.conf $RELEASE_DIR
rm -rf $SOURCE_DIR
tar -zcvf blinkr-$2.tar.gz $RELEASE_DIR
