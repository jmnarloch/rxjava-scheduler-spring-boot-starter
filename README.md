# Spring Boot Netflix RxJava Declarative Schedulers

> A Spring Boot starter for RxJava schedulers integration

[![Build Status](https://travis-ci.org/jmnarloch/rxjava-scheduler-spring-cloud-starter.svg?branch=master)](https://travis-ci.org/jmnarloch/rxjava-scheduler-spring-cloud-starter)
[![Coverage Status](https://coveralls.io/repos/jmnarloch/rxjava-scheduler-spring-cloud-starter/badge.svg?branch=master&service=github)](https://coveralls.io/github/jmnarloch/rxjava-scheduler-spring-cloud-starter?branch=master)

## Setup

Add the Spring Boot starter to your project:

```xml
<dependency>
  <groupId>io.jmnarloch</groupId>
  <artifactId>rxjava-scheduler-spring-cloud-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Usage

## Properties

The only supported property is `rxjava.schedulers.enabled` which allows to disable this extension.

```
rxjava.schedulers.enabled=true # true by default
```

## License

Apache 2.0
