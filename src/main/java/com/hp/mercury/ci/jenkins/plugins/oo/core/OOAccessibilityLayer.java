// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.core;

import com.hp.mercury.ci.jenkins.plugins.oo.utils.StringUtils;
import com.hp.mercury.ci.jenkins.plugins.oo.http.JaxbEntity;
import com.hp.mercury.ci.jenkins.plugins.OOBuildStep;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

public class OOAccessibilityLayer {

    private static final String FLOW_ROOT_FOLDER = "Library";
    private static final String REST_SERVICES_URL_PATH = "services/rest/";
    private static final String LIST_OPERATION_URL_PATH = "list/";
    private static final String RUN_OPERATION_URL_PATH = "run/";

    public static OOServer getOOServer(String uniqueLabel) {

        return OOBuildStep.getDescriptorStatically().getOoServers(true).get(uniqueLabel);
    }

    public static Collection<String> getAvailableServers() {

        return OOBuildStep.getDescriptorStatically().getOoServers(true).keySet();
    }

    public static OOListResponse listFlows(OOServer s, String... folders) throws IOException {

        String foldersPath = "";

        for (String folder : folders) {
            foldersPath += folder + "/";
        }

        String url = StringUtils.slashify(s.getUrl()) +
                REST_SERVICES_URL_PATH +
                LIST_OPERATION_URL_PATH +
                foldersPath;

        final HttpResponse response = OOBuildStep.getHttpClient().execute(
                new HttpGet(OOBuildStep.URI(url)));

        final int statusCode = response.getStatusLine().getStatusCode();

        final HttpEntity entity = response.getEntity();

        try {

            if (statusCode == HttpStatus.SC_OK) {

                return JAXB.unmarshal(entity.getContent(), OOListResponse.class);
            }

            else {

                throw new RuntimeException("unable to get list of flows from " + url + ", response code: " +
                        statusCode + "(" + HttpStatus.getStatusText(statusCode) + ")");
            }
        }

        finally {
            EntityUtils.consume(entity);
        }

    }

    public static OORunResponse run(OORunRequest request) throws IOException, URISyntaxException {

        String urlString = StringUtils.slashify(request.getServer().getUrl()) +
                REST_SERVICES_URL_PATH +
                RUN_OPERATION_URL_PATH +
                StringUtils.unslashifyPrefix(request.getFlow().getId());

        final URI uri = OOBuildStep.URI(urlString);

        final HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(new JaxbEntity(request));
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "text/xml");
        final HttpResponse response = OOBuildStep.getHttpClient().execute(httpPost);

        final int statusCode = response.getStatusLine().getStatusCode();

        final HttpEntity entity = response.getEntity();

        try {
            if (statusCode == HttpStatus.SC_OK) {

                final OORunResponse unmarshal = JAXB.unmarshal(entity.getContent(), OORunResponse.class);

                return unmarshal;
            } else {

                throw new RuntimeException("unable to get run result from " + uri + ", response code: " +
                        statusCode + "(" + HttpStatus.getStatusText(statusCode) + ")");
            }
        } finally {
            EntityUtils.consume(entity);
        }

    }
}