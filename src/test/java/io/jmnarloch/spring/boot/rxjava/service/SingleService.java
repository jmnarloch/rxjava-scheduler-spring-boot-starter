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
package io.jmnarloch.spring.boot.rxjava.service;

import io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOn;
import io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOnBean;
import org.mockito.Mockito;
import rx.Observable;
import rx.Single;

import static io.jmnarloch.spring.boot.rxjava.annotation.Scheduler.COMPUTATION;
import static io.jmnarloch.spring.boot.rxjava.annotation.Scheduler.IMMEDIATE;
import static io.jmnarloch.spring.boot.rxjava.annotation.Scheduler.IO;
import static io.jmnarloch.spring.boot.rxjava.annotation.Scheduler.NEW_THREAD;
import static io.jmnarloch.spring.boot.rxjava.annotation.Scheduler.TRAMPOLINE;

/**
 * A simple service used for test purpose.
 *
 * @author Jakub Narloch
 */
public class SingleService {

    @SubscribeOn(IMMEDIATE)
    public Single<String> immediate() {
        return single();
    }

    @SubscribeOn(COMPUTATION)
    public Single<String> computation() {
        return single();
    }

    @SubscribeOn(NEW_THREAD)
    public Single<String> newThread() {
        return single();
    }

    @SubscribeOn(TRAMPOLINE)
    public Single<String> trampoline() {
        return single();
    }

    @SubscribeOn(IO)
    public Single<String> io() {
        return single();
    }

    @SubscribeOn(IMMEDIATE)
    public Object invalidReturnType() {
        return new Object();
    }

    @SubscribeOn(IMMEDIATE)
    public Object nullValue() {
        return null;
    }

    @SubscribeOnBean("executorScheduler")
    public Single<String> customScheduler() {
        return single();
    }

    @SubscribeOnBean("executorScheduler")
    public Single<String> customSchedulerNullValue() {
        return null;
    }

    @SubscribeOnBean("notExistingScheduler")
    public Single<String> notExistingScheduler() {
        return single();
    }

    @SubscribeOnBean("executor")
    public Single<String> invalidSchedulerType() {
        return single();
    }

    @SuppressWarnings("unchecked")
    private <T> Single<T> single() {
        return Mockito.mock(Single.class);
    }
}
