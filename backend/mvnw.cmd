@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.3.2
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_SAVE_ERRORLEVEL__=
@SET __MVNW_SAVE_CD__=
@setlocal

@SET __MVNW_SAVE_CD__=%CD%
@SET BASEDIR=%~dp0

:mvnw_find_maven_basedir
@IF EXIST "%BASEDIR%.mvn\" (
  GOTO mvnw_end_find_maven_basedir
)
@IF NOT EXIST "%BASEDIR%.." (
  GOTO mvnw_end_find_maven_basedir
)
@SET "LAST_BASEDIR=%BASEDIR%"
@CD ..
@SET "BASEDIR=%CD%"
@IF NOT "%BASEDIR%"=="%LAST_BASEDIR%" GOTO mvnw_find_maven_basedir
:mvnw_end_find_maven_basedir

@SET MAVEN_PROJECTBASEDIR=%BASEDIR%

@SET WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
@SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@SET DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"

@FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties") DO (
    @IF "%%A"=="wrapperUrl" SET DOWNLOAD_URL=%%B
)

@IF EXIST %WRAPPER_JAR% (
    @IF "%MVNW_VERBOSE%"=="true" @ECHO Found %WRAPPER_JAR%
) ELSE (
    @IF NOT "%MVNW_REPOURL%"=="" SET DOWNLOAD_URL="%MVNW_REPOURL%/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
    @IF "%MVNW_VERBOSE%"=="true" @ECHO Couldn't find %WRAPPER_JAR%, downloading it ...
    @IF "%MVNW_VERBOSE%"=="true" @ECHO Downloading from: %DOWNLOAD_URL%
    powershell -Command "&{"^
		"$webclient = new-object System.Net.WebClient;"^
		"if (-not ([string]::IsNullOrEmpty('%MVNW_USERNAME%') -and [string]::IsNullOrEmpty('%MVNW_PASSWORD%'))) {"^
		"$webclient.Credentials = new-object System.Net.NetworkCredential('%MVNW_USERNAME%', '%MVNW_PASSWORD%');"^
		"}"^
		"[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; $webclient.DownloadFile('%DOWNLOAD_URL%', '%WRAPPER_JAR%')"^
		"}"
    @IF "%MVNW_VERBOSE%"=="true" @ECHO Finished downloading %WRAPPER_JAR%
)

@SET MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"
@IF NOT EXIST %MAVEN_JAVA_EXE% SET MAVEN_JAVA_EXE=java

@REM @SET WRAPPER_LAUNCHER_ARGS=-Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%"
@SET WRAPPER_LAUNCHER_ARGS=-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%

@REM @SET WRAPPER_OPTS="%MAVEN_OPTS%"
@SET WRAPPER_OPTS=%MAVEN_OPTS%

@IF "%WRAPPER_OPTS%"=="" (
  SET WRAPPER_OPTS=
)

%MAVEN_JAVA_EXE% %WRAPPER_OPTS% %MAVEN_DEBUG_OPTS% %WRAPPER_LAUNCHER_ARGS% -cp %WRAPPER_JAR% %WRAPPER_LAUNCHER% %MAVEN_CONFIG% %*
@SET __MVNW_SAVE_ERRORLEVEL__=%ERRORLEVEL%

@endlocal & CD /D "%__MVNW_SAVE_CD__%"
@EXIT /B %__MVNW_SAVE_ERRORLEVEL__%
