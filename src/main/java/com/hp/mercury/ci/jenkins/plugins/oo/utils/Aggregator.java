// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.utils;

import java.util.Collection;

/**
 *
 * @param <R> the aggregation result
 * @param <T> the aggregated collection type
 */
public interface Aggregator<R, T> {

    /**
     *
     * @param elements the container that's about to be iterated.
     *
     * use this function to initialize the aggregation process. for example, you might need to create
     * a "result" collection and you can set it's size according to elements.size() for performance reasons.
     */
    public void init(Collection<T> elements);

    /**
     *
     * @param element the currently iterated element that you should add to the aggregation.
     * if the aggregator implementation is to create a sum of integers, then an implementation for this
     * method would be sum += element;
     */
    public void aggregate(T element);

    /**
     *
     * @return the result of the aggregation
     */
    public R finish();
}
