// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.matcher;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public enum MatchStrategy {

    /**
     * true if OO value is equal to some predefined value
     */
    IS {
        /**
         *
         * @param s the value from the oo response
         * @param valueToCompareWith hardcoded value to compare with
         * @return true if inputs are the same, case agnostic
         */
        @Override
        public boolean matches(String s, String valueToCompareWith) {
            return s.equalsIgnoreCase(valueToCompareWith);
        }
    },

    /**
     * true if OO value contains some predefined string
     */
    CONTAINS {
        /**
         *
         * @param s value from OO
         * @param valueToCompareWith hardcoded value to compare with
         * @return
         */
        @Override
        public boolean matches(String s, String valueToCompareWith) {
            return s.toLowerCase().contains(valueToCompareWith.toLowerCase());
        }
    },

    /**
     * true if OO value starts with some predefined string
     */
    STARTS_WITH {
        /**
         *
         * @param s value from OO
         * @param valueToCompareWith hardcoded value to compare with
         * @return true iff s starts with valueToCompareWith (case agnostic)
         */
        @Override
        public boolean matches(String s, String valueToCompareWith) {
            return s.toLowerCase().startsWith(valueToCompareWith.toLowerCase());
        }
    },

    /**
     * true if value from OO is not the same as predefined value
     */
    DIFFERS_FROM {
        /**
         *
         * @param s value from OO
         * @param valueToCompareWith hardcoded value to compare with
         * @return true iff s is not equal to valueToCompareWith (case agnostic)
         */
        @Override
        public boolean matches(String s, String valueToCompareWith) {
            return !IS.matches(s, valueToCompareWith);
        }
    },

    /**
     * true if value from oo matches a regex
     */
    IS_A_REGEX_MATCH_FOR {
        /**
         *
         * @param s value from OO
         * @param valueToCompareWith hardcoded value to compare with
         * @return true if the string s matches the regex valueToCompareWith
         */
        @Override
        public boolean matches(String s, String valueToCompareWith) {
            return Pattern.matches(valueToCompareWith, s);
        }
    };

    /**
     *
     * @return list of all matchers
     */
    public static List<MatchStrategy> getMatchStrategies() {
        return Arrays.asList(MatchStrategy.values());
    }

    /**
     *
     * @param s value from OO
     * @param valueToCompareWith hardcoded value to compare with
     * @return depends on implementation, abstractly should be true iff s matches valueToCompareWith.
     */
    public abstract boolean matches(String s, String valueToCompareWith);
}
