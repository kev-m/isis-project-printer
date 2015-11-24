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
package com.cosylab.dom.report;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.util.ObjectContracts;

import com.cosylab.dom.simple.Project;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "Report")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(
// strategy=VersionStrategy.VERSION_NUMBER,
strategy = VersionStrategy.DATE_TIME, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "find", language = "JDOQL", value = "SELECT "
				+ "FROM com.cosylab.dom.report.Report "),
		
		@javax.jdo.annotations.Query(name = "findByName", language = "JDOQL", value = "SELECT "
				+ "FROM com.cosylab.dom.report.Report " + "WHERE name.indexOf(:name) >= 0 ") })
@javax.jdo.annotations.Unique(name = "Report_name_UNQ", members = { "name" })
@DomainObject
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-flag")
public class Report implements Comparable<Report> {

	public static final int NAME_LENGTH = 40;

	public String title() {
		return getName();
	}

	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	@Property(editing = Editing.DISABLED)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Report updateName(
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
	public int compareTo(final Report other) {
		return ObjectContracts.compare(this, other, "name");
	}

	@javax.inject.Inject
	@SuppressWarnings("unused")
	private DomainObjectContainer container;

	// {{ Projects (Collection)
	private Set<Project> projects = new LinkedHashSet<Project>();

	@MemberOrder(sequence = "1")
	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(final Set<Project> projects) {
		this.projects = projects;
	}

	public Report addProject(final Project project) {
		if (!this.projects.contains(project)){
			this.projects.add(project);
		}
		return this;
	}
	
	//{{ Remove projects from a report
	public Report removeProject(final Project project) {
		if (this.projects.contains(project)){
			this.projects.remove(project);
		}
		return this;
	}
	
	public Collection<Project> choices0RemoveProject(){
		return projects;
	}
	//}} 
	
}
