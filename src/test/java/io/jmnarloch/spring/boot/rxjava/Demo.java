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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.jmnarloch.spring.boot.rxjava.annotation.Scheduler;
import io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOn;
import io.jmnarloch.spring.boot.rxjava.annotation.SubscribeOnBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * Demonstrates the usage of this component.
 *
 * @author Jakub Narloch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Demo.Application.class)
public class Demo {

    @Autowired
    private InvoiceService service;

    @Test
    public void shouldSubscribeOn() {

        // when
        Observable<Invoice> observable = service.getInvoices();

        // then
        List<Invoice> invoices = observable.toList().toBlocking().first();
        assertEquals(invoices.size(), 2);
    }

    @Test
    public void shouldSubscribeOnBean() {

        // when
        Observable<Invoice> observable = service.getUnprocessedInvoices();

        // then
        List<Invoice> invoices = observable.toList().toBlocking().first();
        assertEquals(invoices.size(), 2);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableAspectJAutoProxy
    public static class Application {

        @Bean
        public InvoiceService observableService() {
            return new InvoiceService();
        }

        @Bean
        public rx.Scheduler customScheduler() {
            return Schedulers.from(
                    Executors.newSingleThreadExecutor()
            );
        }
    }

    public static class InvoiceService {

        @SubscribeOn(Scheduler.IO)
        public Observable<Invoice> getInvoices() {
            return Observable.just(
                    new Invoice("Acme", new Date()),
                    new Invoice("Oceanic", new Date())
            );
        }

        @SubscribeOnBean("customScheduler")
        public Observable<Invoice> getUnprocessedInvoices() {
            return Observable.just(
                    new Invoice("Acme", new Date()),
                    new Invoice("Oceanic", new Date())
            );
        }
    }

    public static class Invoice {

        private final String title;

        private final Date issueDate;

        @JsonCreator
        public Invoice(@JsonProperty("title") String title, @JsonProperty("issueDate") Date issueDate) {
            this.title = title;
            this.issueDate = issueDate;
        }

        public String getTitle() {
            return title;
        }

        public Date getIssueDate() {
            return issueDate;
        }
    }
}
