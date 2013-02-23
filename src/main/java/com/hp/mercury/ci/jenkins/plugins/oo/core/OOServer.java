// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.core;

import com.hp.mercury.ci.jenkins.plugins.oo.utils.CollectionUtils;
import com.hp.mercury.ci.jenkins.plugins.oo.utils.Criteria;
import com.hp.mercury.ci.jenkins.plugins.oo.utils.Handler;
import com.hp.mercury.ci.jenkins.plugins.oo.utils.StringUtils;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OOServer {

    private String uniqueLabel;
    private String url;

    private String password;
    private String username;
    private boolean active;

    @Deprecated //keep this for backwards compatability
    public OOServer(String id, String hostUrl, String username, String password) throws Descriptor.FormException {

        this(id, hostUrl, username, password, true);

    }

    @DataBoundConstructor
    public OOServer(String id, String hostUrl, String username, String password, boolean active) throws Descriptor.FormException {

        this.uniqueLabel = id;
        this.url = hostUrl;
        this.username = username;
        this.password = password;
        this.active = active;

    }


    public String getUrl() {
        return url;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public List<OOFlow> getFlows(String basepath) throws IOException {

        return CollectionUtils.map(
                OOAccessibilityLayer.listFlows(this, StringUtils.unslashifyPrefix(basepath).split("/")).getItems(),
                new Handler<OOFlow, OOListResponse.OOListItem>() {

                    public OOFlow apply(OOListResponse.OOListItem node) {
                        return new OOFlow(node.getValue());
                    }

                }
        );

    }

    public OOFlow getFlow(List<OOFlow> flows, final String id) throws IOException {

        List<OOFlow> copy = new ArrayList(flows);

        CollectionUtils.filter(copy, new Criteria<OOFlow>() {
            public boolean isSuccessful(OOFlow tested) {
                return tested.getId().equals(id);
            }
        });

        return (copy.isEmpty() ? null : copy.get(0));
    }

    public String getUniqueLabel() {
        return uniqueLabel;
    }

    public boolean isActive() {
        return active;
    }
}
