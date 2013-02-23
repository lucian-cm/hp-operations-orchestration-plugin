// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.utils;



import java.util.Random;

/**
 * Utility class for things relating to uniqueness
 *
 */
public class UniquenessUtils {

    /**
     * cached character container for alpha-numeric chars.
     */
    private static char[] ALPHA_NUMERIC_CHARS = null;

    /**
     * getter that initializes {@link #ALPHA_NUMERIC_CHARS} if it's not initialized
     *
     * @return an initialized {@link #ALPHA_NUMERIC_CHARS} which contains a-z,A-Z,0-9
     */
    private static char[] getAlphaNumericChars() {

        //initialize the cached field if it's not set - for lazy loading...
        if (ALPHA_NUMERIC_CHARS == null) {

            //determine the size of the combined alphaNumeric range
            int alphaNumericCharactersCount = 0;

            for (CharacterRange cr : CharacterRange.values()) {

                alphaNumericCharactersCount += cr.getLength();
            }

            ALPHA_NUMERIC_CHARS = new char[alphaNumericCharactersCount];

            //fill in the values from the nested ranges
            int index = 0;

            for (CharacterRange cr : CharacterRange.values()) {

                for (Character c : cr) {

                    ALPHA_NUMERIC_CHARS[index++] = c;
                }
            }
        }

        return ALPHA_NUMERIC_CHARS;
    }


    private static Random random = null;

    private static Random getRandom() {

        if (random == null) {
            random = new Random();
        }

        return random;
    }

    /**
     *
     * @param size of random alphanumeric string to generate
     * @return a random alpha numeric string of size "size" (param)
     */
    public static String randomAlphanumeric(int size) {

        char[] buf = new char[size];
        final Random random = getRandom();
        final char[] alphaNumericChars = getAlphaNumericChars();

        for (int i = 0 ; i < size ; i++) {
            buf[i] = randomAlphanumericChar(alphaNumericChars, random);
        }

        return new String(buf);
    }

    /**
     *
     * @return random alpha numeric character
     */
    public static char randomAlphanumericChar() {

        return randomAlphanumericChar(getAlphaNumericChars(), getRandom());
    }

    private static char randomAlphanumericChar(char[] alphaNumericChars, Random random) {

        return alphaNumericChars[random.nextInt(alphaNumericChars.length)];
    }
}
