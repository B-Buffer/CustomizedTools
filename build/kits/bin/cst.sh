#!/bin/sh

DIRNAME=`dirname "$0"`
PROGNAME=`basename "$0"`

# Read an optional running configuration file
if [ "x$RUN_CONF" = "x" ]; then
    RUN_CONF="$DIRNAME/run.conf"
fi
if [ -r "$RUN_CONF" ]; then
    . "$RUN_CONF"
fi


# Setup CST_HOME
RESOLVED_CST_HOME=`cd "$DIRNAME/.."; pwd`
if [ "x$CST_HOME" = "x" ]; then
    # get the full path (without any relative bits)
    CST_HOME=$RESOLVED_CST_HOME
else
 SANITIZED_CST_HOME=`cd "$CST_HOME"; pwd`
 if [ "$RESOLVED_CST_HOME" != "$SANITIZED_CST_HOME" ]; then
   echo "WARNING CST_HOME may be pointing to a different installation - unpredictable results may occur."
   echo ""
 fi
fi
export CST_HOME

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
        JAVA="$JAVA_HOME/bin/java"
    else
        JAVA="java"
    fi
fi

# Display our environment
echo "========================================================================="
echo ""
echo "  CustomizedTools Bootstrap Environment"
echo ""
echo "  CST_HOME: $CST_HOME"
echo ""
echo "  JAVA: $JAVA"
echo ""
echo "  JAVA_OPTS: $JAVA_OPTS"
echo ""
echo "========================================================================="
echo ""

if [ "x$LAUNCH_CST_IN_BACKGROUND" = "x" ]; then
    # Execute the JVM in the foreground
    eval \"$JAVA\" $JAVA_OPTS \
        -Dcst.home=\"$CST_HOME\" \
        -jar \"$CST_HOME/jboss-modules-${version.jboss-modules}.jar\" \
        -mp \"$CST_HOME/modules\" \
        com.customized.tools \
        "$@"
    CST_STATUS=$?
    echo "CustomizedTools Status: $CST_STATUS"
else
    # Execute the JVM in the background
    eval \"$JAVA\" $JAVA_OPTS \
        -DCST.home.dir=\"$CST_HOME\" \
        -jar \"$CST_HOME/${version.jboss-modules}.jar\" \
        -mp \"$CST_HOME/modules\" \
        com.customized.tools \
        "$@" "&"
    CST_PID=$!
    echo "CustomizedTools PID: $CST_PID"
fi
