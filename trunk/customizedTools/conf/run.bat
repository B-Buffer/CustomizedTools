@echo off
rem -------------------------------------------------------------------------
rem CTS Bootstrap Script for Windows
rem -------------------------------------------------------------------------

rem $Id: run.bat 111635 2011-06-16 19:40:52Z ksoong $

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT" setlocal enableextensions enabledelayedexpansion

if "%OS%" == "Windows_NT" (
  set "DIRNAME=!~dp0!"
) else (
  set DIRNAME=.\
)

rem Read an optional configuration file.
if "x%RUN_CONF%" == "x" (
   set "RUN_CONF=%DIRNAME%cts.conf.bat"
)
if exist "%RUN_CONF%" (
   echo Calling %RUN_CONF%
   call "%RUN_CONF%" %*
) else (
   echo Config file not found %RUN_CONF%
)

pushd %DIRNAME%..
if "x%CTS_HOME%" == "x" (
  set "CTS_HOME=%CD%"
)
popd

set DIRNAME=

if "%OS%" == "Windows_NT" (
  set "PROGNAME=%~nx0%"
) else (
  set "PROGNAME=cts.bat"
)

REM Force use of IPv4 stack
set JAVA_OPTS=%JAVA_OPTS% -Djava.net.preferIPv4Stack=true

rem Setup JBoss specific properties
set JAVA_OPTS=%JAVA_OPTS% -Dprogram.name=%PROGNAME%

if "x%JAVA_HOME%" == "x" (
  set  JAVA=java
  echo JAVA_HOME is not set. Unexpected results may occur.
  echo Set JAVA_HOME to the directory of your local JDK to avoid this message.
) else (
  set "JAVA=%JAVA_HOME%\bin\java"
  if exist "%JAVA_HOME%\lib\tools.jar" (
    set "JAVAC_JAR=%JAVA_HOME%\lib\tools.jar"
  )
)

rem Add -server to the JVM options, if supported
"%JAVA%" -server -version 2>&1 | findstr /I hotspot > nul
if not errorlevel == 1 (
  set "JAVA_OPTS=%JAVA_OPTS% -server"
)

:WITHOUT_JBOSS_NATIVE
rem Find run.jar, or we can't continue

if exist "%CTS_HOME%\run.jar" (
  if "x%JAVAC_JAR%" == "x" (
    set "RUNJAR=%CTS_HOME%\run.jar"
  ) else (
    set "RUNJAR=%JAVAC_JAR%;%CTS_HOME%\run.jar"
  )
) else (
  echo Could not locate "%CTS_HOME%\run.jar".
  echo Please check that you are in the bin directory when running this script.
  goto END
)

rem If JBOSS_CLASSPATH empty, don't include it, as this will
rem result in including the local directory in the classpath, which makes
rem error tracking harder.
if "x%CTS_CLASSPATH%" == "x" (
  set "RUN_CLASSPATH=%RUNJAR%"
) else (
  set "RUN_CLASSPATH=%CTS_CLASSPATH%;%RUNJAR%"
)

set CTS_CLASSPATH=%RUN_CLASSPATH%;%CTS_HOME%\lib\jars\log4j-1.2.16.jar

rem Setup cts specific properties


echo ===============================================================================
echo.
echo   CTSBootstrap Environment
echo.
echo   CTS_HOME: %JBOSS_HOME%
echo.
echo   JAVA: %JAVA%
echo.
echo   JAVA_OPTS: %JAVA_OPTS%
echo.
echo   CLASSPATH: %CTS_CLASSPATH%
echo.
echo ===============================================================================
echo.

:RESTART
"%JAVA%" %JAVA_OPTS% ^
   -classpath "%JBOSS_CLASSPATH%" ^
   com.customized.tools.startup.Tools %*

if ERRORLEVEL 10 goto RESTART

:END
if "x%NOPAUSE%" == "x" pause

:END_NO_PAUSE
