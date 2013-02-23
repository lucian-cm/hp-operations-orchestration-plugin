// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

 /* request format:
<?xml version=\"1.0\" ?>
<run>
    <request>
        <arg name=\"name0\">value0</arg>
        <arg name=\"name1\">value1</arg>
        <arg name=\"name2\">value2</arg>
    </request>
</run>
*/
@XmlRootElement(name = "run")
public class OORunRequest {

    private OOServer server;
    private OOFlow flow;
    private List<OOArg> args;

    //for jaxb
    public OORunRequest() {}

    public OORunRequest(OOServer selectedOOServer, OOFlow flow, List<OOArg> args) {
        this.server = selectedOOServer;
        this.flow = flow;
        this.args = args;
    }

    public OOServer getServer() {
        return server;
    }

    public OOFlow getFlow() {
        return flow;
    }

    @XmlElementWrapper(name = "request")
    @XmlElement(name = "arg")
    public List<OOArg> getArgs() {
        return args;
    }

    public void setArgs(List<OOArg> args) {
        this.args = args;
    }
}
