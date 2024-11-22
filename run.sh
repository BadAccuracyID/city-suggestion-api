# Description: Run the application for unix based systems
#/bin/bash

export $(cat .env | xargs)
./gradlew bootRun
