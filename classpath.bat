@ECHO OFF
REM Batch script to set
REM the classpath

set CLASSPATH=
set JAR_LIST=

set WD=%cd%
for /r %WD%\lib %%i in (*.jar) do call :setjar %%i
set CLASSPATH=%JAR_LIST%;
echo %CLASSPATH%

:setjar
set JAR_LIST=%JAR_LIST%;%1
