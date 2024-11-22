@echo off
REM Description: Run the application for Windows systems

REM Load the environment variables from the .env file
for /f "tokens=1,* delims==" %%i in ('findstr "=" .env') do set %%i=%%j

REM Run the Spring Boot application using Gradle
gradlew bootRun
