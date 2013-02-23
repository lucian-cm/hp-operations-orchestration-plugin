// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins;

import com.hp.mercury.ci.jenkins.plugins.oo.utils.StringUtils;
import com.hp.mercury.ci.jenkins.plugins.oo.core.OOArg;
import com.hp.mercury.ci.jenkins.plugins.oo.core.OOListResponse;
import com.hp.mercury.ci.jenkins.plugins.oo.core.OORunRequest;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;


public class TestSerialization {

    @Test
    public void testListResponse() throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(
                OOListResponse.class,
                OOListResponse.OOListItem.class);

        final Unmarshaller unmarshaller = context.createUnmarshaller();

        final InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("listResponse.xml");

        final OOListResponse unmarshal = (OOListResponse)unmarshaller.unmarshal(resourceAsStream);

        Assert.assertEquals("/Library/AGM/AGM - Java 6u25 Config", unmarshal.getItems().iterator().next().getValue());
    }

    @Test
    public void testRunRequest() throws JAXBException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        final OORunRequest ooRunRequest = new OORunRequest();
        ooRunRequest.setArgs(Arrays.asList(new OOArg("url","http://nathan")));
        JAXB.marshal(ooRunRequest, os);

        String serialization = new String(os.toByteArray());

        final String expectedRequest =
                "<run>\n" +
                "    <request>\n" +
                "        <arg name=\"url\">http://nathan</arg>\n" +
                "    </request>\n" +
                "</run>";

        Assert.assertTrue("request string is not as expected", StringUtils.removeWhitespaces(serialization).endsWith(
                StringUtils.removeWhitespaces(expectedRequest)));

    }
}
