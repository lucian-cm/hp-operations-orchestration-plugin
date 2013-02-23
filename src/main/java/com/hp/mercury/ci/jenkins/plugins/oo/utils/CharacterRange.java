// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.utils;



import java.util.Iterator;

/**
 *
 * enum with several common character ranges.
 * useful for input validation, uid generation, text transformations etc...
 *
 */
public enum CharacterRange implements Iterable<Character> {

    /**
     * represents the 0-9 numeric range
      */
    Numeric('0','9'),

    /**
     * represents the lower-case alphabetical range a-z
     */
    LowercaseAlphabetic('a','z'),

    /**
     * represents the upper-case alphabetical range A-Z
     */
    UppercaseAlphabetic('A','Z');

    /**
     * first character in range
     */
    private char firstLetter;

    /**
     * last character in range
     */
    private char lastLetter;

    /**
     * @param firstLetter first character in range
     * @param lastLetter last character in range
     */
    CharacterRange(char firstLetter, char lastLetter) {
        this.firstLetter = firstLetter;
        this.lastLetter = lastLetter;
    }

    /**
     *
     * @return {@link #firstLetter}
     */
    public char getFirstLetter() {
        return firstLetter;
    }

    /**
     *
     * @return {@link #lastLetter}
     */
    public char getLastLetter() {
        return lastLetter;
    }

    /**
     *
     * @return the size of this range
     */
    public int getLength() {

        //the +1 is because the range is inclusive:
        // to see this: if firstLetter and lastLetter are the same, the size should be 1 and not 0.
        return Math.abs(lastLetter - firstLetter) + 1;
    }

    /**
     *
     * @return an iterator that iterates this range's elements
     */
    @Override
    public Iterator<Character> iterator() {

        return new Iterator<Character>() {

            /**
             * state of iteration - next letter that will be returned
             */
            private Character currentLetter = CharacterRange.this.getFirstLetter();

            /**
             *
             * @return true iff the iteration has not reached the last letter
             */
            @Override
            public boolean hasNext() {

                return (currentLetter <= CharacterRange.this.getLastLetter());
            }

            /**
             *
             * @return the next character in the range
             */
            @Override
            public Character next() {

                //could be done in a single line, but clearer this way.
                char ret = currentLetter;
                currentLetter++;
                return ret;
            }

            /**
             * this method is not implemented.
             * @throws NotImplementedException
             */
            @Override
            public void remove() {
                throw new NotImplementedException();
            }
        };
    }
}