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
package io.jmnarloch.spring.boot.rxjava.annotation;

import rx.Single;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation that allows to define the scheduler upon which the {@link rx.Observable} or {@link Single} will be
 * subscribed on.
 *
 * <pre>
 * <code>
 * public static class InvoiceService {
 *
 *  &#64;SubscribeOn(Scheduler.IO)
 *  public Observable&lt;Invoice&gt; getInvoices() {
 *      return Observable.just(
 *          new Invoice("Acme", new Date()),
 *          new Invoice("Oceanic", new Date())
 *      );
 *  }
 * }
 * </code>
 * </pre>
 *
 * @author Jakub Narloch
 * @see Scheduler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubscribeOn {

    /**
     * The scheduler on which the return value will be subscribed on.
     */
    Scheduler value();
}
