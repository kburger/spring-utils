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

/**
 * Convenience flags to clarify the boolean return values of {@link org.springframework.context.annotation.Condition#matches(org.springframework.context.annotation.ConditionContext, org.springframework.core.type.AnnotatedTypeMetadata) Condition.matches(context, metadata)}.
 */
public interface ConditionFlags {
    /**
     * <blockquote>if the condition matches and the component can be registered</blockquote>
     * @see org.springframework.context.annotation.Condition#matches(org.springframework.context.annotation.ConditionContext, org.springframework.core.type.AnnotatedTypeMetadata)
     */
    boolean ALLOW_BEAN_REGISTRATION = true;
    /**
     * <blockquote>to veto the annotated component's registration</blockquote>
     * @see org.springframework.context.annotation.Condition#matches(org.springframework.context.annotation.ConditionContext, org.springframework.core.type.AnnotatedTypeMetadata)
     */
    boolean VETO_BEAN_REGISTRATION = false;
}
