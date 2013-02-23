// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.core;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.Collection;

/*
<listResponse>
    <listReturn>
        <item>
            /Library/How Do I flows/How do I: Create a parallel flow
        </item>
        ...
    </listReturn>
</listResponse>
*/
@XmlRootElement(name = "listResponse")
public class OOListResponse {

    //for jaxb
    public OOListResponse() {}

    Collection<OOListItem> items;

    @XmlElementWrapper(name = "listReturn")
    @XmlElement(name = "item")
    public Collection<OOListItem> getItems() {
        return items;
    }

    public void setItems(Collection<OOListItem> items) {
        this.items = items;
    }

    public static class OOListItem {

        String value;

        public OOListItem() {}

        @XmlValue
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
