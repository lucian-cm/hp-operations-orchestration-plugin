// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.utils;



import java.util.Collection;
import java.util.Map;

/**
 * aggregator calculating the max element of a collection after a transformation has been applied
 * to make the element comparable.
 *
 * usually it would be enough to map the elements to comparables, and then use Collection.max() -
 * but then you'll get the max comparable and not the original element, which sometimes complicates things.
 * also, you'll need to iterate the collection twice, and also store the entire comparable collection -
 * that's a performance issue.
 *
 * so you could instead just implement a comparator and use collection.max, but sometimes it's simpler
 * to use a class' pre-existing comparator or "natural ordering".
 * for example, if your element can easily be transformed to an Integer.
 *
 * @param <C> the result - largest comparable in the aggregated collection
 * @param <T> the type of the collection being aggregated
 */
public class MaxAggregator<C extends Comparable, T> implements Aggregator<Map.Entry<T, C>, T> {

    /**
     * the function that turns the aggregated collection elements to comparables.
     */
    private Handler<C, T> comparifier;

    /**
     * the result's computed comparable
     */
    private C maximumComparable;

    /**
     * the result
     */
    private T maximum;


    /**
     *
     * @param comparifier the function that turns the aggregated collection elements to comparables.
     *                    it is assumed that it does not return null.
     */
    public MaxAggregator(Handler<C, T> comparifier) {
        this.comparifier = comparifier;
    }

    @Override
    public void init(Collection<T> elements) {

        maximum = null;
        maximumComparable = null;
    }

    @Override
    public void aggregate(T element) {

        final C comparable = comparifier.apply(element);

        if (maximumComparable == null || (comparable.compareTo(maximumComparable) > 0)) {
            maximum = element;
            maximumComparable = comparable;
        }
    }

    /**
     *
     * @return null if no aggregated collection was empty, or the largest comparable otherwise.
     */
    @Override
    public Map.Entry<T, C> finish() {

        return (maximum == null) ||
                (maximumComparable == null) ?
                null :
                new MapEntryImpl<T, C>(maximum, maximumComparable);
    }
}
