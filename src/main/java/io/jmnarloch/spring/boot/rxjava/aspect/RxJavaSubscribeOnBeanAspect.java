/**
 * Copyright (c) 2015-2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.spring.boot.rxjava.aspect;

import io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOnBean;
import io.jmnarloch.spring.boot.rxjava.metadata.AnnotationInspector;
import io.jmnarloch.spring.boot.rxjava.metadata.MetadataExtractor;
import io.jmnarloch.spring.boot.rxjava.subscribable.Subscribables;
import io.jmnarloch.spring.boot.rxjava.utils.RxJavaUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import rx.Scheduler;

import static io.jmnarloch.spring.boot.rxjava.utils.AopUtils.getJointPointMethod;
import static io.jmnarloch.spring.boot.rxjava.utils.AopUtils.isNull;

/**
 * The aspect that subscribes the return value of method annotated with {@link SubscribeOnBean}.
 *
 * @author Jakub Narloch
 * @see SubscribeOnBean
 */
@Aspect
public class RxJavaSubscribeOnBeanAspect implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(RxJavaSubscribeOnAspect.class);

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
            final AnnotationInspector<SubscribeOnBean> inspector = MetadataExtractor.extractFrom(
                    getJointPointMethod(joinPoint)
            ).subscribeOnBean();

            if(!isValidMethodDefinition(inspector)) {
                return skip(joinPoint);
            }
            return process(inspector, joinPoint);
        } catch (Throwable exc) {
            throw exc;
        }
    }

    private Object skip(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    private Object process(AnnotationInspector<SubscribeOnBean> inspector, ProceedingJoinPoint joinPoint) throws Throwable {
        final Object result = joinPoint.proceed();
        if (isNull(result)) {
            return result;
        }
        return subscribeOn(inspector, result);
    }

    private Object subscribeOn(AnnotationInspector<SubscribeOnBean> inspector, Object result) {
        final Scheduler scheduler = getScheduler(
                inspector.getAnnotation().value()
        );
        return Subscribables.toSubscribable(result)
                .subscribeOn(scheduler)
                .unwrap();
    }

    private boolean isValidMethodDefinition(AnnotationInspector<SubscribeOnBean> inspector) {
        if (!inspector.isAnnotationPresent()) {
            LOGGER.warn("The @SubscribeOnBean annotation wasn't present");
            return false;
        }

        final Class<?> returnType = inspector.getMethod().getReturnType();
        if (!RxJavaUtils.isRxJavaType(returnType)) {
            LOGGER.warn("The @SubscribeOnBean annotated method return type has to be either Observable or Single");
            return false;
        }

        return true;
    }

    private Scheduler getScheduler(String beanName) {
        return applicationContext.getBean(beanName, Scheduler.class);
    }
}
