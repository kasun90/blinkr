#!/bin/bash

SYSTEM_DIR="system"

blinkrbuild() {
    SOURCE_DIR="source"
    RELEASE_DIR="release"
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
        *)
        shift
        ;;
    esac
    done

    echo VERSION = "$BLINKRVERSION"
    echo GITUSERNAME = "$GITUSERNAME"
    echo GITPASSWORD = "$GITPASSWORD"
}

blinkrinstall() {
    echo install
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
    blinkrbuild $@
    ;;
    install)
    blinkrinstall
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

