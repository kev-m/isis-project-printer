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
package com.cosylab.dom.simple;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = Customer.class)
@DomainServiceLayout(menuOrder = "10")
public class CosyObjects {

	// {{ title
	public String title() {
		return "Customers";
	}
	// }}

	// {{ Customer Actions
	@MemberOrder(sequence = "4")
	public Customer createCustomer(final @ParameterLayout(named = "Name") String name) {
		final Customer obj = container.newTransientInstance(Customer.class);
		obj.setName(name);
		container.persistIfNotAlready(obj);
		return obj;
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "10")
	public List<Customer> listCustomers() {
		return container.allInstances(Customer.class);
	}

	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "20")
	public List<Customer> findByName(@ParameterLayout(named = "Name") final String name) {
		return container.allMatches(new QueryDefault<>(Customer.class, "findByName", "name", name));
	}
	// }}
	
	
	// {{ Project actions
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "100")
	public List<Project> listProjects() {
		return container.allInstances(Project.class);
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "100")
	public List<Project> listProjectsFor(Customer customer) {
		return container.allMatches(new QueryDefault<>(Project.class, "findByCustomer", "customer", customer));
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "110")
	public List<Project> findProjects(@ParameterLayout(named = "Name") final String name) {
		return container.allMatches(new QueryDefault<>(Project.class, "findByName", "name", name));
	}

	@MemberOrder(sequence = "120")
	public Project createProject(final Customer customer, final @ParameterLayout(named = "Name") String name) {
		final Project obj = container.newTransientInstance(Project.class);
		obj.setName(name);
		obj.setCustomer(customer);
		container.persistIfNotAlready(obj);
		return obj;
	}
	// }}
	

	// {{ injected services

	@javax.inject.Inject
	DomainObjectContainer container;

	// }}
}
