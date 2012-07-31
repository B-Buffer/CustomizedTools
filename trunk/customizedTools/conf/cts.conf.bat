rem ### -*- batch file -*- ######################################################
rem #                                                                          ##
rem #  CTS Bootstrap Script Configuration                                    ##
rem #                                                                          ##
rem #############################################################################

rem # $Id: cts.conf.bat 112288 2012-08-01 14:59:56Z mbenitez $

rem #
rem # This batch file is executed by run.bat to initialize the environment 
rem # variables that run.bat uses. It is recommended to use this file to
rem # configure these variables, rather than modifying run.bat itself. 
rem #

if not "x%JAVA_OPTS%" == "x" goto JAVA_OPTS_SET



rem # JVM memory allocation pool parameters - modify as appropriate.
set "JAVA_OPTS=-Xms512m -Xmx512m -XX:MaxPermSize=64m -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Dsun.lang.ClassLoader.allowArraySyntax=true"

rem # Specify the Security Manager options.
set "JAVA_OPTS=%JAVA_OPTS% -Djava.net.preferIPv4Stack=true"


rem # Sample JPDA settings for remote socket debugging
rem set "JAVA_OPTS=%JAVA_OPTS% -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"

rem # Sample JPDA settings for shared memory debugging 
rem set "JAVA_OPTS=%JAVA_OPTS% -Xrunjdwp:transport=dt_shmem,address=jboss,server=y,suspend=n"

:JAVA_OPTS_SET
