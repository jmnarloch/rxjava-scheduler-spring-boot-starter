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

Adds a declarative approach for defining schedulers on the methods RxJava return types: Observable or Single.

* SubscribeOn - subscribes to the RxJava predefined scheduler.
* SubscribeOnBean - subscribes to Scheduler defined as bean within application context.

## Example

```
    @Configuration
    @EnableAspectJAutoProxy
    public static class Application {

        @Bean
        public rx.Scheduler customScheduler() {
            return Schedulers.from(
                    Executors.newSingleThreadExecutor()
            );
        }
    }
```

Afterwards you can define that your services uses the custom scheduler or any of the RxJava predefined by annotating
your methods.

```
    @Service
    public class InvoiceService {

        @SubscribeOn(Scheduler.IO)
        public Observable<Invoice> getInvoices() {
            return Observable.just(
                ...
            );
        }

        @SubscribeOnBean("executorScheduler")
        public Observable<Invoice> getUnprocessedInvoices() {
            return Observable.just(
                ...
            );
        }
    }
```

Note: You need to enable Spring AOP proxies through `@EnableAspectJAutoProxy` in order to use this extension.

## Properties

The only supported property is `rxjava.schedulers.enabled` which allows to disable this extension.

```
rxjava.schedulers.enabled=true # true by default
```

## License

Apache 2.0
