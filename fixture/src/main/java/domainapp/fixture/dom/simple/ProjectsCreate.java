/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package domainapp.fixture.dom.simple;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import com.cosylab.dom.simple.CosyObjects;
import com.cosylab.dom.simple.Customer;
import com.cosylab.dom.simple.Project;

public class ProjectsCreate extends FixtureScript {

    //region > name (input)
    private String name;
    /**
     * Name of the object (required)
     */
    public String getName() {
        return name;
    }

    public ProjectsCreate setName(final String name) {
        this.name = name;
        return this;
    }
    //endregion
    
    // {{ Customer (property)
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}
	// }}



    //region > simpleObject (output)
    private Project project;

    /**
     * The created simple object (output).
     * @return
     */
    public Project getProject() {
        return project;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        String name = checkParam("name", ec, String.class);

        this.project = wrap(simpleObjects).createProject(customer, name);
        // also make available to UI
        ec.addResult(this, project);
    }

    @javax.inject.Inject
    private CosyObjects simpleObjects;

    
}
