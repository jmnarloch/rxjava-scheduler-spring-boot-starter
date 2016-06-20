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
package io.jmnarloch.spring.boot.rxjava.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * The AOP utility class.
 *
 * @author Jakub Narloch
 */
public final class AopUtils {

    private AopUtils() {
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static Method getJointPointMethod(ProceedingJoinPoint joinPoint) {
        Method method = null;
        if (joinPoint.getSignature() instanceof MethodSignature) {
            method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        }
        return method;
    }
}
