#!/bin/sh
### ====================================================================== ###
##                                                                          ##
##  CustomizedTools Bootstrap Script                                        ##
##                                                                          ##
### ====================================================================== ###

### $Id: run.sh 112347 2011-10-14 06:57:48Z jiwils $ ###

DIRNAME=`dirname $0`
PROGNAME=`basename $0`
GREP="grep"

#
# Helper to complain.
#
warn() {
    echo "${PROGNAME}: $*"
}


#
# Helper to puke.
#
die() {
    warn $*
    exit 1
}

# Read an optional running configuration file
if [ "x$RUN_CONF" = "x" ]; then
    RUN_CONF="$DIRNAME/cts.conf"
fi
if [ -r "$RUN_CONF" ]; then
    . "$RUN_CONF"
fi


# Setup CTS_HOME
if [ "x$CTS_HOME" = "x" ]; then
    # get the full path (without any relative bits)
    CTS_HOME=`cd $DIRNAME; pwd`
fi
export CTS_HOME

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
        JAVA="$JAVA_HOME/bin/java"
    else
        JAVA="java"
    fi
fi

# Setup the classpath
runjar="$CTS_HOME/run.jar"
if [ ! -f "$runjar" ]; then
    die "Missing required file: $runjar"
fi
CTS_BOOT_CLASSPATH="$runjar"


if [ "x$JBOSS_CLASSPATH" = "x" ]; then
    CTS_CLASSPATH="$CTS_BOOT_CLASSPATH"
fi

CTS_CLASSPATH="$CTS_CLASSPATH:$CTS_HOME/lib/jars/log4j-1.2.16.jar"

JAVA_OPTS="$JAVA_OPTS -Dcts.baseDir=$CTS_HOME"


# Display our environment
echo "========================================================================="
echo ""
echo "  CustomizedTools Bootstrap Environment"
echo ""
echo "  CTS_HOME: $CTS_HOME"
echo ""
echo "  JAVA: $JAVA"
echo ""
echo "  JAVA_OPTS: $JAVA_OPTS"
echo ""
echo "  CLASSPATH: $CTS_CLASSPATH"
echo ""
echo "========================================================================="
echo ""


eval \"$JAVA\" $JAVA_OPTS \
         -classpath \"$CTS_CLASSPATH\" \
         com.customized.tools.startup.Tools


