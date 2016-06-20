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
package io.jmnarloch.spring.boot.rxjava.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * The annotation inspector, capable for extracting the annotation from the given type.
 *
 * @param <A> the annotation type
 * @author Jakub Narloch
 */
public class AnnotationInspector<A extends Annotation> {

    private final Method method;

    private final Class<A> annotationType;

    public AnnotationInspector(Method method, Class<A> annotationType) {
        this.method = method;
        this.annotationType = annotationType;
    }

    public boolean isAnnotationPresent() {
        return method.isAnnotationPresent(annotationType);
    }

    public A getAnnotation() {
        return method.getAnnotation(annotationType);
    }

    public Method getMethod() {
        return method;
    }
}
