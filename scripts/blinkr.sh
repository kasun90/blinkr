#!/bin/bash

SYSTEM_DIR="system"

getblinkrreleasename() {
    echo "blinkr-${1}.tar.gz"
}

blinkrbuild() {
    SOURCE_DIR="source"
    RELEASE_DIR="release"
    NORELEASE=false
    RELEASE_BINARY_DIR="office-runner"
    GITUSERNAME=""
    GITPASSWORD=""
    BLINKRVERSION=""

    while [[ $# -gt 0 ]]
    do
    key="$1"

    case $key in
        -v|--version)
        BLINKRVERSION="$2"
        shift
        shift
        ;;
        -u|--username)
        GITUSERNAME="$2"
        shift
        shift
        ;;
        -p|--password)
        GITPASSWORD="$2"
        shift
        shift
        ;;
        --norelease)
        NORELEASE=true
        shift
        ;;
        -d|--dir)
        RELEASE_BINARY_DIR="$2"
        shift
        shift
        ;;
        *)
        shift
        ;;
    esac
    done

    if [ -z "$BLINKRVERSION" ]
    then
        echo -e "\e[31mPlease provide version with option -v | --version"
        exit 1
    fi

    if [ "$NORELEASE" = false ]
    then
        echo Attempting to authenticate with GitHub to create a release. You can skip this step by supplying \'--norelease\'
        if [ -z "$GITUSERNAME" ]
        then
            echo -n Please enter your GitHub username:
            read GITUSERNAME
        fi

        if [ -z "$GITPASSWORD" ]
        then
            echo -n Please enter your GitHub password:
            read -s GITPASSWORD
        fi

        echo -e "\nAuthenticating..."
        result=`curl -sS -u "$GITUSERNAME:$GITPASSWORD" https://api.github.com/user | grep id`

        if [ -z "$result" ]
        then
            echo -e "\e[31mGitHub authentication failed. Exiting.."
            exit 1
        fi
    fi

    mkdir $SOURCE_DIR
    mkdir $RELEASE_DIR
    cd $SOURCE_DIR
    git clone git@github.com:kasun90/blinkr.git .
    mvn versions:set -DnewVersion=$BLINKRVERSION
    mvn clean install -DskipTests

    if [ $? -ne 0 ]; then
        echo -e "\e[31mMaven build failed. Exiting.."
        exit 1
    fi

    TAG_NAME="v${BLINKRVERSION}"
    TAG_MESSAGE="Blinkr Release ${TAG_NAME}"
    RELEASE_NAME=$(getblinkrreleasename $BLINKRVERSION)
    git tag -a $TAG_NAME -m "${TAG_MESSAGE}"
    git push origin $TAG_NAME


    cd ..
    cp $SOURCE_DIR/$RELEASE_BINARY_DIR/target/*.jar $RELEASE_DIR
    if [ $? -ne 0 ]; then
        echo -e "\e[31mCouldn't find release binaries. Exiting.."
        exit 1
    fi
    cp -r $SOURCE_DIR/$RELEASE_BINARY_DIR/target/blinkr-$BLINKRVERSION.lib $RELEASE_DIR
    cp -r $SOURCE_DIR/static/ $RELEASE_DIR
    cp $SOURCE_DIR/*.conf $RELEASE_DIR
    rm -rf $SOURCE_DIR
    cd $RELEASE_DIR
    tar -czvf ../$RELEASE_NAME *
    cd ..

    if [ "$NORELEASE" = false ]
    then
        result=`curl -X POST -H "Content-Type: application/json" -u "$GITUSERNAME:$GITPASSWORD" -d '{"tag_name":"'"$TAG_NAME"'","name":"'"$TAG_MESSAGE"'","draft":false,"prerelease":false}' https://api.github.com/repos/kasun90/blinkr/releases | grep upload_url`
        splits=($result)
        url=${splits[1]}
        url=`sed -e 's/^"//' -e 's/"$//' <<< "$url" | awk '{split($url,a,"{"); print a[1]}'`
        url+="?name=$RELEASE_NAME"
        echo Uploading release
        curl -X POST -H "Content-Type: application/gzip" -u "$GITUSERNAME:$GITPASSWORD" --data-binary @$RELEASE_NAME $url
        echo -e "\e[32mFinished"
    fi
}

blinkrinstall() {
    echo $(getblinkrreleasename $1)
}

blinkrstop() {
    echo stop
}

blinkrstart() {
    echo $"Start $SYSTEM_DIR"
}

blinkrstatus() {
    echo status
}

COMMAND=$1
shift

case $COMMAND in
    build)
    blinkrbuild "$@"
    ;;
    install)
    blinkrinstall "hello"
    ;;
    stop)
    blinkrstop
    ;;
    start)
    blinkrstart
    ;;
    status)
    blinkrstatus
    ;;
    *)    # unknown option
    echo $"Usage: $0 {start|stop|build|install|status}"
    exit 1
    ;;
esac

