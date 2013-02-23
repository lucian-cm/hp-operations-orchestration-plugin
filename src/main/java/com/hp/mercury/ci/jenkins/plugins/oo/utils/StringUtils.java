// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.utils;

/**
 *
 * encompasses common string related tasks
 *
 */
public class StringUtils {

    //utility class
    private StringUtils() { }

    /**
     *
     * removes whitespaces
     *
     * @param input
     * @return the input string, without any whitespaces.
     */
    public static String removeWhitespaces(String input) {
        return input.replaceAll("\\s","");
    }

    /**
     *
     * @param url a url that may or may not end with a "/"
     * @return the same url ending with "/"
     */
    public static String slashify(String url) {
        return url + (url.endsWith("/") ? "" : "/");
    }

    /**
     *
     * @param url a url that may or may not start with a "/"
     * @return the same url without "/" as prefix
     */
    public static String unslashifyPrefix(String url) {
        return url.startsWith("/") ? url.substring(1) : url;
    }
}
