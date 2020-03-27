#!/usr/bin/env sh

./gradlew clean build bintrayUpload -PbintrayUser=JoeLjt -PbintrayKey=49e3a5d9980daef96124147cdcc1422eab56de54 -PdryRun=false
