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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.InvokedOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.scratchpad.Scratchpad;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Clob;

import com.cosylab.Html;
import com.cosylab.dom.report.Report;
import com.cosylab.dom.report.Reports;
import com.cosylab.isis.RichContent;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Project")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(
// strategy=VersionStrategy.VERSION_NUMBER,
strategy = VersionStrategy.DATE_TIME, column = "version")
@javax.jdo.annotations.Queries({ @javax.jdo.annotations.Query(name = "find", language = "JDOQL", value = "SELECT "
		+ "FROM com.cosylab.dom.modules.simple.Project "),

		@javax.jdo.annotations.Query(name = "findByName", language = "JDOQL", value = "SELECT "
				+ "FROM com.cosylab.dom.simple.Project " + "WHERE name.indexOf(:name) >= 0 "),
		
		@javax.jdo.annotations.Query(name = "findByCustomer", language = "JDOQL", value = "SELECT "
				+ "FROM com.cosylab.dom.simple.Project " + "WHERE customer == :customer")
		})
@javax.jdo.annotations.Unique(name = "Project_name_UNQ", members = { "name" })
@DomainObject
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-flag")
public class Project implements Comparable<Project> {

	public static final int NAME_LENGTH = 40;

	public String title() {
		return getName();
	}

	//{{ Name
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	@MemberOrder(sequence = "10")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Project updateName(
			@Parameter(maxLength = NAME_LENGTH) @ParameterLayout(named = "New name") final String name) {
		setName(name);
		return this;
	}

	public String default0UpdateName() {
		return getName();
	}

	public String validateUpdateName(final String name) {
		return name.contains("!") ? "Exclamation mark is not allowed" : null;
	}

	@Override
	public int compareTo(final Project other) {
		return ObjectContracts.compare(this, other, "name");
	}
	//}}

	//{{ Customer
	@Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	private Customer customer;

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}
	//}}
	
	// {{ Description (property)
	// Was Clob
	@MemberOrder(sequence = "20")
	@Column(allowsNull = "false")
	@PropertyLayout( multiLine = 10, typicalLength = 80)
	@Html
	private RichContent description;

	public RichContent getDescription() {
		return description;
	}

	public void setDescription(final RichContent description) {
		this.description = description;
	}
	public RichContent defaultDescription(){
		return new RichContent("Your text here");
	}
	// }}
	
	//{{ Report
    @Action(invokeOn=InvokeOn.OBJECT_AND_COLLECTION)
	public Project addToReports(){
		Report currentReport = (Report) scratchpad.get("currentReport");
		
		if (currentReport == null){
			currentReport = reports.findOpenReport();
			if (currentReport == null){
				container.informUser("No open report found, please open a report first");
				return this;
			}
			scratchpad.put("currentReport", currentReport);
		}
		currentReport.addProject(this);
		
        return actionInteractionContext.getInvokedOn() == InvokedOn.OBJECT
                ? this  
                : null;
	}
    
	@javax.inject.Inject
    ActionInvocationContext actionInteractionContext;
    
    /*
    public String disableAddToReports(){
    	if (scratchpad == null || scratchpad.get("currentReport") == null){
    		return "No active report"; 
    	}
    	return null;
    }
    */
    
	//}}


	// {{ injected services
	@javax.inject.Inject
	@SuppressWarnings("unused")
	private DomainObjectContainer container;

	@javax.inject.Inject
	Scratchpad scratchpad;
	
	@javax.inject.Inject
	Reports reports;
	//}}
}
