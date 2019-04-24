/**
 * The MIT License
 * Copyright © 2019 https://github.com/kburger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.kburger.spring.utils.conditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.MethodMetadata;

/**
 * {@link Condition} implementation to prevent a bean from being registered if there already are
 * other bean instances of the same type.
 * 
 * <p>This condition should be applied on method level, as described in
 * {@link org.springframework.context.annotation.Conditional}.</p>
 */
public class ExistingBeanCondition implements Condition {
    /** Logger instance. */
    private static final Logger logger = LoggerFactory.getLogger(ExistingBeanCondition.class);
    
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (metadata instanceof MethodMetadata == false) {
            throw new IllegalStateException("ExistingBeanCondition is expected to be applied to " +
                    "an @Bean annotated method");
        }
        
        var method = (MethodMetadata)metadata;
        var returnTypeName = method.getReturnTypeName();
        
        final Class<?> type;
        try {
            type = Class.forName(returnTypeName);
        } catch (ClassNotFoundException e) {
            return ConditionFlags.ALLOW_BEAN_REGISTRATION;
        }
        
        var beanFactory = context.getBeanFactory();
        
        if (beanFactory == null) {
            throw new IllegalStateException("BeanFactory is not available; could not determine " +
                    "the existence of " + returnTypeName + " type beans");
        }
        
        try {
            beanFactory.getBean(type);
        } catch (NoUniqueBeanDefinitionException e) {
            logger.debug("Found multiple bean definitions; veto bean registration of {}.{}",
                    method.getDeclaringClassName(), method.getMethodName());
            return ConditionFlags.VETO_BEAN_REGISTRATION;
        } catch (NoSuchBeanDefinitionException e) {
            logger.debug("Found no bean definition; allow bean registration of {}.{}",
                    method.getDeclaringClassName(), method.getMethodName());
            return ConditionFlags.ALLOW_BEAN_REGISTRATION;
        }
        
        logger.debug("Found bean definition; veto bean registration of {}.{}",
                method.getDeclaringClassName(), method.getMethodName());
        return ConditionFlags.VETO_BEAN_REGISTRATION;
    }
}
