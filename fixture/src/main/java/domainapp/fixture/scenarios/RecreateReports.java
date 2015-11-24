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

package domainapp.fixture.scenarios;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import com.cosylab.dom.report.Report;
import com.cosylab.dom.simple.Customer;
import com.cosylab.dom.simple.Project;
import com.google.common.collect.Lists;

import domainapp.fixture.dom.simple.CustomersCreate;
import domainapp.fixture.dom.simple.CustomersTearDown;
import domainapp.fixture.dom.simple.ProjectsCreate;
import domainapp.fixture.dom.simple.ProjectsTearDown;
import domainapp.fixture.dom.simple.ReportsCreate;
import domainapp.fixture.dom.simple.ReportsTearDown;

public class RecreateReports extends FixtureScript {

	public final List<String> NAMES = Collections
			.unmodifiableList(Arrays.asList("ITER", "ESS", "DESY", "ANKA", "JAEA"));

	public final List<String> NAMES2 = Collections
			.unmodifiableList(Arrays.asList("Report1", "Report2", "Report3", "Report4", "Report5"));

	public final List<String> NAMES3 = Collections
			.unmodifiableList(Arrays.asList("Project1", "Project2", "Project3", "Project4", "Project5"));

	public RecreateReports() {
		withDiscoverability(Discoverability.DISCOVERABLE);
	}

	// region > number (optional input)
	private Integer number;

	/**
	 * The number of objects to create, up to 10; optional, defaults to 3.
	 */
	public Integer getNumber() {
		return number;
	}

	public RecreateReports setNumber(final Integer number) {
		this.number = number;
		return this;
	}
	// endregion

	// region > simpleObjects (output)
	private final List<Report> reports = Lists.newArrayList();
	private final List<Customer> customers = Lists.newArrayList();
	private final List<Project> projects = Lists.newArrayList();

	/**
	 * The simpleobjects created by this fixture (output).
	 */
	public List<Report> getReports() {
		return reports;
	}
	// endregion

	@Override
	protected void execute(final ExecutionContext ec) {

		// defaults
		final int numberReports = defaultParam("number", ec, 2);
		final int numberCustomers = defaultParam("number", ec, 2);
		final int numberProjects = defaultParam("number", ec, 3);

		// validate
		if (numberCustomers < 0 || numberCustomers > NAMES.size()) {
			throw new IllegalArgumentException(String.format("number must be in range [0,%d)", NAMES.size()));
		}
		if (numberReports < 0 || numberReports > NAMES2.size()) {
			throw new IllegalArgumentException(String.format("number must be in range [0,%d)", NAMES2.size()));
		}

		//
		// execute
		//
		// Reports
		ec.executeChild(this, new ReportsTearDown());
		// Projects
		ec.executeChild(this, new ProjectsTearDown());
		// Customers
		ec.executeChild(this, new CustomersTearDown());

		for (int i = 0; i < numberCustomers; i++) {
			// Customers
			final String name = NAMES.get(i);
			final CustomersCreate cs = new CustomersCreate().setName(name);
			ec.executeChild(this, cs.getName(), cs);
			Customer customer = cs.getCustomer();
			customers.add(customer);
			
			for (int j = 0; j < numberProjects; j++){
				// Projects
				final ProjectsCreate pr = new ProjectsCreate().setName(name+" " +NAMES3.get(j));
				pr.setCustomer(customer);				
				ec.executeChild(this, pr.getName(), pr);
				Project project = pr.getProject();
				projects.add(project);
			}
		}
		

		for (int i = 0; i < numberReports; i++) {
			// Reports
			final ReportsCreate fs = new ReportsCreate().setName(NAMES2.get(i));
			ec.executeChild(this, fs.getName(), fs);

			final Report rep = fs.getSimpleObject();
			for (int j = 0; j < numberCustomers; j++){
				rep.addProject(projects.get((i)*numberCustomers+j));
			}
			reports.add(rep);
		}

	}
}
