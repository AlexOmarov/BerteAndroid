# Berte (android)

PoC Application with Android frameworks.

[![Java version](https://img.shields.io/static/v1?label=Java&message=17&color=blue)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_BerteAndroid)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_BerteAndroid&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_BerteAndroid)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_BerteAndroid&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_BerteAndroid)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_BerteAndroid&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_BerteAndroid)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=AlexOmarov_BerteAndroid&metric=coverage)](https://sonarcloud.io/summary/new_code?id=AlexOmarov_BerteAndroid)

## Table of Contents

- [Introduction](#introduction)
- [Documentation](#documentation)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)

## Introduction


## Documentation

## Features

## Requirements

## Quick Start

### Run code quality assurance tasks

If you want to get total coverage and sonar analysis with local changes, then you should run following tasks:
```
.\gradlew test createReleaseUnitTestCoverageReport coverage
.\gradlew sonarqube -D"sonar.host.url"="https://sonarcloud.io" -D"sonar.login"="b9694e03ee5fbf20e87d643ef0efccc104332567"
```
Then, jacoco test report with coverage will be generated on local machine in build folder
and sonar analysis will take place on server and will be visible on sonarcloud instance.
Also, it is recommended to install [SonarLint](https://plugins.jetbrains.com/plugin/7973-sonarlint) Intellij plugin for integration of code
quality analysis more native-like
Also, there is a possibility to configure jacoco coverage as a replace for common Idea coverage
analyzer (it's optional)
