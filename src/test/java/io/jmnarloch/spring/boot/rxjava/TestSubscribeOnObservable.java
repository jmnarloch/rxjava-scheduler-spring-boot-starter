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
package io.jmnarloch.spring.boot.rxjava;

import io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOn;
import io.jmnarloch.spring.boot.rxjava.service.ObservableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link SubscribeOn} declaration for {@link Observable} type.
 *
 * @author Jakub Narloch
 */
@PrepareForTest(Schedulers.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestSubscribeOnObservable.Application.class)
public class TestSubscribeOnObservable {

    @Autowired
    private ObservableService service;

    @Test
    public void shouldSubscribeOnImmediate() {

        // when
        Observable result = service.immediate();

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldSubscribeOnTrampoline() {

        // when
        Observable result = service.trampoline();

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldSubscribeOnComputation() {

        // when
        Observable result = service.computation();

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldSubscribeOnNewThread() {

        // when
        Observable result = service.newThread();

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldSubscribeOnIo() {

        // when
        Observable result = service.io();

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldNotSubscribeToNonReactiveType() {

        // when
        Object result = service.invalidReturnType();

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldNotSubscribeToNull() {

        // when
        Object result = service.nullValue();

        // then
        assertNull(result);
    }

    @Test
    public void shouldSubscribeOnBean() {

        // when
        Observable result = service.customScheduler();

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldNotSubscribeOnBeanWhenReturnValueIsNull() {

        // when
        Observable result = service.customSchedulerNullValue();

        // then
        assertNull(result);
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void shouldFailToSubscribeOnMissingBean() {

        // when
        Observable result = service.notExistingScheduler();

        // then
        assertNotNull(result);
    }

    @Test(expected = BeanNotOfRequiredTypeException.class)
    public void shouldFailToSubscribeOnBeanIncorrectType() {

        // when
        Observable result = service.invalidSchedulerType();

        // then
        assertNotNull(result);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableAspectJAutoProxy
    public static class Application {

        @Bean
        public ObservableService observableService() {
            return new ObservableService();
        }

        @Bean
        public rx.Scheduler executorScheduler() {
            return Schedulers.from(
                    Executors.newSingleThreadExecutor()
            );
        }

        @Bean
        public Executor executor() {
            return Executors.newSingleThreadExecutor();
        }
    }
}
