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
package com.github.kburger.spring.utils.conditions

import static com.github.kburger.spring.utils.conditions.ConditionFlags.*
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.NoUniqueBeanDefinitionException
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotationMetadata
import org.springframework.core.type.MethodMetadata
import spock.lang.Shared
import spock.lang.Specification

class ExistingBeanConditionSpec extends Specification {
    /** Subject under test. */
    @Shared condition = new ExistingBeanCondition()
    
    // covenience closure for mocking the context and metadata fields and matching against the condition
    def match = { Map args = [:] ->
        if (!args.returnType) {
            args.returnType = "java.lang.Object"
        }
        if (!args.getBeanClosure) {
            args.getBeanClosure = { -> null }
        }
        
        def context = [
            getBeanFactory: { -> [
                getBean: args.getBeanClosure
                ] as ConfigurableListableBeanFactory }
            ] as ConditionContext
        
        MethodMetadata metadata = Mock {
            getReturnTypeName() >> args.returnType
        }
        
        return condition.matches(context, metadata)
    }
    
    def "non method application of @ExistingBeanCondition"() {
        when:
        condition.matches(Mock(ConditionContext), Mock(AnnotationMetadata))
        
        then:
        def ex = thrown(IllegalStateException)
        ex.message == "ExistingBeanCondition is expected to be applied to an @Bean annotated method"
    }
    
    def "annotation of a non-loadable class type"() {
        when:
        def result = match returnType: "com.foo.Bar"
        
        then:
        result == ALLOW_BEAN_REGISTRATION
    }
    
    def "non-available bean factory"() {
        when:
        condition.matches([getBeanFactory: { -> null }] as ConditionContext,
            [getReturnTypeName: { -> "java.lang.Object"}] as MethodMetadata)
        
        then:
        def ex = thrown(IllegalStateException)
        ex.message == "BeanFactory is not available; could not determine the existence of java.lang.Object type beans"
    }
    
    def "multiple beans available"() {
        when:
        def result = match getBeanClosure: { throw new NoUniqueBeanDefinitionException(Object, "one", "two") }
        
        then:
        result == VETO_BEAN_REGISTRATION
    }
    
    def "no beans available"() {
        when:
        def result = match getBeanClosure: { throw new NoSuchBeanDefinitionException(Object) }
        
        then:
        result == ALLOW_BEAN_REGISTRATION
    }
    
    def "single bean available"() {
        when:
        def result = match getBeanClosure: { new Object() }
        
        then:
        result == VETO_BEAN_REGISTRATION
    }
}
