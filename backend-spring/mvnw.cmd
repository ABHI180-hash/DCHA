@echo off
setlocal
set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_HOME=%DIRNAME%
set WRAPPER_JAR="%APP_HOME%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

java -Dmaven.multiModuleProjectDirectory="%APP_HOME:~0,-1%" -classpath %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
