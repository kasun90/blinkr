#!/bin/bash

if [ $# -lt 4 ]; then
    echo "Please specify valid arguments: <root-dir> <version> <username> <password>"
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

TAG_NAME="v${2}"
TAG_MESSAGE="Blinkr Release ${TAG_NAME}"
RELEASE_NAME="blinkr-${2}.tar.gz"
git tag -a $TAG_NAME -m "${TAG_MESSAGE}"
git push origin $TAG_NAME


cd ..
cp $SOURCE_DIR/$1/target/*.jar $RELEASE_DIR
cp -r $SOURCE_DIR/$1/target/blinkr-$2.lib $RELEASE_DIR
cp -r $SOURCE_DIR/static/ $RELEASE_DIR
cp $SOURCE_DIR/*.conf $RELEASE_DIR
rm -rf $SOURCE_DIR
cd $RELEASE_DIR
tar -czvf ../$RELEASE_NAME *
cd ..

result=`curl -X POST -H "Content-Type: application/json" -u "${3}:${4}" -d '{"tag_name":"'"$TAG_NAME"'","name":"'"$TAG_MESSAGE"'","draft":false,"prerelease":false}' https://api.github.com/repos/kasun90/blinkr/releases | grep upload_url`
splits=($result)
url=${splits[1]}
url=`sed -e 's/^"//' -e 's/"$//' <<< "$url" | awk '{split($url,a,"{"); print a[1]}'`
url+="?name=$RELEASE_NAME"
echo $url
echo "Uploading"
curl -X POST -H "Content-Type: application/gzip" -u "${3}:${4}" --data-binary @$RELEASE_NAME $url
echo "Finished"