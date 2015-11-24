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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple", table = "ReportSession")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(
// strategy=VersionStrategy.VERSION_NUMBER,
strategy = VersionStrategy.DATE_TIME, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "findForUser", language = "JDOQL", value = "SELECT "
				+ "FROM com.cosylab.dom.report.ReportSession " + "WHERE user.indexOf(:user) >= 0 ") })
@javax.jdo.annotations.Unique(name = "ReportSession_name_UNQ", members = { "user" })
@DomainObject
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_ROOT, cssClassFa = "fa-flag")
/**
 * This class is used to create unique user+report instances, so that
 * Project#addToReport() can find one project for this user.
 * 
 * @author kevin
 *
 */
public class ReportSession implements Comparable<ReportSession> {

	public static final int NAME_LENGTH = 40;

	public String title() {
		return getUser();
	}

	// {{ User property
	@javax.jdo.annotations.Column(allowsNull = "false", length = NAME_LENGTH)
	private String user;

	@Programmatic
	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}
	// }}

	// {{ Report (property)
	@javax.jdo.annotations.Column(allowsNull = "false")
	private Report report;

	public Report getReport() {
		return report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}
	// }}



	// {{ low level support functions
	@Override
	public int compareTo(final ReportSession other) {
		return ObjectContracts.compare(this, other, "user");
	}
	// }}

}
