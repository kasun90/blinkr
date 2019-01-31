#!/bin/bash

SYSTEM_DIR="system"

getblinkrreleasename() {
    echo "blinkr-${1}.tar.gz"
}

getblinkrwebroot() {
    cat ${1} | grep ${2} | awk '{split($0,a,":"); print a[2]}' | tr -d '[:space:],"'
}

blinkrbuild() {
    SOURCE_DIR="source"
    UISOURCE_DIR="uisource"
    RELEASE_DIR="release"
    NORELEASE=false
    BUILDUI=false
    BUILDDOCKER=false
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
        --build-ui)
        BUILDUI=true
        shift
        ;;
        --build-docker)
        BUILDDOCKER=true
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
        echo -e "\e[31mPlease provide version with option -v | --version\e[0m"
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
            echo -e "\e[31mGitHub authentication failed. Exiting..\e[0m"
            exit 1
        fi
    fi

    mkdir $SOURCE_DIR
    mkdir $RELEASE_DIR
    mkdir $UISOURCE_DIR
    rm *.tar.gz
    rm -rf $RELEASE_DIR/*
    cd $SOURCE_DIR
    git clone git@github.com:kasun90/blinkr.git .
    git config user.email kpiyumal90@gmail.com
    mvn versions:set -DnewVersion=$BLINKRVERSION
    mvn clean install -DskipTests

    if [ $? -ne 0 ]; then
        echo -e "\e[31mMaven build failed. Exiting..\e[0m"
        exit 1
    fi

    cd ..

    if [ "$BUILDUI" = true ]
    then
        echo Going to build UIs
        cd $UISOURCE_DIR
        git clone git@github.com:kasun90/blink-app.git .
        npm install
        npm run build
        if [ $? -ne 0 ]; then
            echo -e "\e[31mClient UI build failed. Exiting..\e[0m"
            exit 1
        fi
        cd ..
        clientFiles=$(getblinkrwebroot $SOURCE_DIR/blink.conf clientRoot)
        rm -rf $SOURCE_DIR/$clientFiles/*
        cp -r $UISOURCE_DIR/build/* $SOURCE_DIR/$clientFiles
        if [ $? -ne 0 ]; then
            echo -e "\e[31mCouldn't copy client UI files. Exiting..\e[0m"
            exit 1
        fi

        rm -rf $UISOURCE_DIR
        mkdir $UISOURCE_DIR
        cd $UISOURCE_DIR
        git clone git@github.com:kasun90/blink-admin-app.git .
        npm install
        ng build --prod
        if [ $? -ne 0 ]; then
            echo -e "\e[31mAdmin UI build failed. Exiting..\e[0m"
            exit 1
        fi

        cd ..
        adminFiles=$(getblinkrwebroot $SOURCE_DIR/blink.conf adminRoot)
        rm -rf $SOURCE_DIR/$adminFiles/*
        cp -r $UISOURCE_DIR/dist/blink-admin-app/* $SOURCE_DIR/$adminFiles
        if [ $? -ne 0 ]; then
            echo -e "\e[31mCouldn't copy admin UI files. Exiting..\e[0m"
            exit 1
        fi
        rm -rf $UISOURCE_DIR

        cd $SOURCE_DIR
        git add -A $clientFiles
        git add -A $adminFiles
        git commit -m "Web files for v${BLINKRVERSION}"
        git push
        cd ..
    fi


    cp $SOURCE_DIR/$RELEASE_BINARY_DIR/target/*.jar $RELEASE_DIR
    if [ $? -ne 0 ]; then
        echo -e "\e[31mCouldn't find release binaries. Exiting..\e[0m"
        exit 1
    fi
    cp -r $SOURCE_DIR/$RELEASE_BINARY_DIR/target/blinkr-$BLINKRVERSION.lib $RELEASE_DIR
    cp -r $SOURCE_DIR/static/ $RELEASE_DIR
    cp -r $SOURCE_DIR/scripts/ $RELEASE_DIR
    cp -r $SOURCE_DIR/hawtio/ $RELEASE_DIR
    cp $SOURCE_DIR/*.conf $RELEASE_DIR
    echo $BLINKRVERSION > $RELEASE_DIR/version.txt

    if [ "$BUILDDOCKER" = true ]
    then
        docker build --build-arg release_dir=$RELEASE_DIR --build-arg system_dir=$SYSTEM_DIR -f $SOURCE_DIR/docker/Dockerfile -t kpiyumal/blinkr .
        if [ $? -ne 0 ]; then
            echo -e "\e[31mDocker build failed. Exiting..\e[0m"
            exit 1
        fi
        docker login
        docker push kpiyumal/blinkr
    fi

    cd $SOURCE_DIR
    TAG_NAME="v${BLINKRVERSION}"
    TAG_MESSAGE="Blinkr Release ${TAG_NAME}"
    RELEASE_NAME=$(getblinkrreleasename $BLINKRVERSION)
    git tag -a $TAG_NAME -m "${TAG_MESSAGE}"
    git push origin $TAG_NAME
    cd ..

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
        echo -e "\e[32mFinished\e[0m"
    fi
}

blinkrinstall() {
    INSTALLVERSION=""

    while [[ $# -gt 0 ]]
    do
    key="$1"

    case $key in
        -v|--version)
        INSTALLVERSION="$2"
        shift
        shift
        ;;
        *)
        shift
        ;;
    esac
    done

    if [ -z "$INSTALLVERSION" ]
    then
        echo -e "\e[31mPlease provide version with option -v | --version\e[0m"
        exit 1
    fi

    rm *.tar.gz

    BINARY_FILE=$(getblinkrreleasename $INSTALLVERSION)
    wget https://github.com/kasun90/blinkr/releases/download/v$INSTALLVERSION/blinkr-$INSTALLVERSION.tar.gz

    if [ $? -ne 0 ]; then
        echo -e "\e[31mCouldn't download the release. Check whether the release exists. Exiting..\e[0m"
        exit 1
    fi

    mkdir $SYSTEM_DIR

    cd $SYSTEM_DIR
    rm *.jar
    rm -rf *.lib
    clientFiles=$(getblinkrwebroot blink.conf clientRoot)
    rm -rf $clientFiles
    adminFiles=$(getblinkrwebroot blink.conf adminRoot)
    rm -rf $adminFiles
    cd ..

    tar -xzvf $BINARY_FILE -C $SYSTEM_DIR
    echo -e "\e[32mInstallation done\e[0m"
}

blinkrstatus() {
    grepresult=`ps -ef | grep java | grep blinkr`

    if [ -z "$grepresult" ]
    then
        echo -e "\e[31mBlinkr is not running\e[0m"
        false
        return
    fi

    blinkpid=`awk '/[b]linkr/{print $2}' <<< "$grepresult"`
    echo -e "\e[32mBlinkr is running. PID=$blinkpid\e[0m"
    true
    return
}

blinkrstop() {
    blinkpidstop=`pgrep -f "jar blinkr"`
    if [ ! -z "$blinkpidstop" ]
    then
        echo -e "Going to kill $blinkpidstop"
        kill -15 $blinkpidstop
    fi
    sleep 5
    blinkrstatus
}

blinkrstart() {
    INTERACTIVE=false

    while [[ $# -gt 0 ]]
    do
    key="$1"

    case $key in
        -i|--interactive)
        INTERACTIVE=true
        shift
        ;;
        *)
        shift
        ;;
    esac
    done

    if blinkrstatus
    then
        echo -e "\nAlready running. Nothing to do."
        return
    fi
    cd $SYSTEM_DIR
    if [ $? -ne 0 ]; then
        echo -e "\e[31mPlease install Blinkr first\e[0m"
        exit 1
    fi
    appjarfile=`find . -name "*-app.jar" -type f -printf "%f\n"`
    if [ -z "$appjarfile" ]
    then
        echo -e "\e[31mCouldn't find main jar file\e[0m"
        exit 1
    fi
    if [ "$INTERACTIVE" = false ]
    then
        nohup java -Xms256m -Xmx512m -jar $appjarfile --prod > blinkr.out 2> blinkr.err < /dev/null &
    else
        java -Xms256m -Xmx512m -jar $appjarfile
    fi

    cd ..
    blinkrstatus
}

blinkrconfigure() {
    cd $SYSTEM_DIR
    if [ $? -ne 0 ]; then
        echo -e "\e[31mPlease install Blinkr first\e[0m"
        exit 1
    fi
    consolejarfile=`find . -name "*-console.jar" -type f -printf "%f\n"`
    if [ -z "$consolejarfile" ]
    then
        echo -e "\e[31mCouldn't find main jar file\e[0m"
        exit 1
    fi
    java -jar $consolejarfile
    cd ..
}


COMMAND=$1
shift

case $COMMAND in
    build)
    blinkrbuild "$@"
    ;;
    install)
    blinkrinstall "$@"
    ;;
    stop)
    blinkrstop
    ;;
    start)
    blinkrstart "$@"
    ;;
    status)
    blinkrstatus
    ;;
    configure)
    blinkrconfigure
    ;;
    *)    # unknown option
    echo $"Usage: blinkr (start|stop|build|install|status|configure)"
    exit 1
    ;;
esac

