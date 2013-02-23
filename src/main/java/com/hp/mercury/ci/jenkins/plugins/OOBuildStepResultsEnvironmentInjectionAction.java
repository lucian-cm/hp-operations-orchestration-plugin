// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins;

import hudson.EnvVars;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.EnvironmentContributingAction;

/**
 *
 * this class must remain in package com.hp.mercury.ci.jenkins.plugins for
 * backwards compatability
 */
public class OOBuildStepResultsEnvironmentInjectionAction implements EnvironmentContributingAction {

    private EnvVars injectedVars;

    public OOBuildStepResultsEnvironmentInjectionAction(EnvVars envVars) {
        this.injectedVars = envVars;
    }

    @Override
    public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {

        if (env != null && injectedVars != null) {
            env.putAll(injectedVars);
        }
    }

    /*
    These are nulls because this shouldn't be visible...
    */
    @Override
    public String getIconFileName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDisplayName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUrlName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
