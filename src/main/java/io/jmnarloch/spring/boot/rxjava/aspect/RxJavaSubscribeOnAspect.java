/**
 * Copyright (c) 2015-2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.spring.boot.rxjava.aspect;

import io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOn;
import io.jmnarloch.spring.boot.rxjava.metadata.MetadataExtractor;
import io.jmnarloch.spring.boot.rxjava.subscribable.Subscribables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.lang.reflect.Method;

import static io.jmnarloch.spring.boot.rxjava.utils.AopUtils.getJointPointMethod;

/**
 *
 */
@Aspect
public class RxJavaSubscribeOnAspect {

    @Pointcut("@annotation(io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOn)")
    public void rxjavaSubscribeOnPointcut() {
    }

    @Around("rxjavaSubscribeOnPointcut()")
    public Object subscribeOnAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // #1 retrieve method definition

            // #2 validate the method prerequisites:
            // annotations
            // return type

            // #3 establish the scheduler types from annotation

            // #4 execute the method

            // #5 schedule on the result

            final Method method = getJointPointMethod(joinPoint);

            final Scheduler scheduler = getScheduler(
                    MetadataExtractor.extractFrom(method)
                            .subscribeOn()
                            .getAnnotation()
                            .value()
            );

            return Subscribables.toSubscribable(joinPoint.proceed())
                    .subscribeOn(scheduler)
                    .unwrap();
        } catch (Throwable exc) {
            throw exc;
        }
    }

    private Scheduler getScheduler(SubscribeOn.Scheduler value) {
        switch (value) {
            case IMMEDIATE:
                return Schedulers.immediate();
            case TRAMPOLINE:
                return Schedulers.trampoline();
            case NEWTHREAD:
                return Schedulers.newThread();
            case COMPUTATION:
                return Schedulers.computation();
            case IO:
                return Schedulers.io();
            default:
                throw new IllegalStateException();
        }
    }
}
