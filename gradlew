#!/bin/bash
GRADLE_VERSION=8.2
wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip
unzip gradle-${GRADLE_VERSION}-bin.zip
export PATH=$PATH:$(pwd)/gradle-${GRADLE_VERSION}/bin
gradle assembleDebug
