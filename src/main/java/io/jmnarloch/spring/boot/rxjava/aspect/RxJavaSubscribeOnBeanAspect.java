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

import io.jmnarloch.spring.boot.rxjava.metadata.MetadataExtractor;
import io.jmnarloch.spring.boot.rxjava.subscribable.Subscribables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import rx.Scheduler;

import java.lang.reflect.Method;

import static io.jmnarloch.spring.boot.rxjava.utils.AopUtils.getJointPointMethod;

/**
 *
 */
@Aspect
public class RxJavaSubscribeOnBeanAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Pointcut("@annotation(io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOnBean)")
    public void rxjavaSubscribeOnBeanPointcut() {
    }

    @Around("rxjavaSubscribeOnBeanPointcut()")
    public Object subscribeOnBeanAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

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
                            .subscribeOnBean()
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

    private Scheduler getScheduler(String beanName) {
        return applicationContext.getBean(beanName, Scheduler.class);
    }
}
