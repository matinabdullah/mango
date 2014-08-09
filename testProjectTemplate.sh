#!/bin/bash

./gradlew --build-file mango-build/build.gradle :mango-project-template:uploadLocalDevelopment :mango-bootstrap:uploadLocalDevelopment
rm -rf project1
cat ./bootstrap.sh | bash -s org.example.Project1
gradle --refresh-dependencies --build-file ./project1/project1-build/build.gradle jettyEclipseRun
