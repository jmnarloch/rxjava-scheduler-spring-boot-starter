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
public class ObservableService {

    @SubscribeOn(IMMEDIATE)
    public Observable<String> immediate() {
        return observable();
    }

    @SubscribeOn(COMPUTATION)
    public Observable<String> computation() {
        return observable();
    }

    @SubscribeOn(NEW_THREAD)
    public Observable<String> newThread() {
        return observable();
    }

    @SubscribeOn(TRAMPOLINE)
    public Observable<String> trampoline() {
        return observable();
    }

    @SubscribeOn(IO)
    public Observable<String> io() {
        return observable();
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
    public Observable<String> customScheduler() {
        return observable();
    }

    @SubscribeOnBean("executorScheduler")
    public Observable<String> customSchedulerNullValue() {
        return null;
    }

    @SubscribeOnBean("notExistingScheduler")
    public Observable<String> notExistingScheduler() {
        return observable();
    }

    @SubscribeOnBean("executor")
    public Observable<String> invalidSchedulerType() {
        return observable();
    }

    @SuppressWarnings("unchecked")
    private <T> Observable<T> observable() {
        return Mockito.mock(Observable.class);
    }
}
