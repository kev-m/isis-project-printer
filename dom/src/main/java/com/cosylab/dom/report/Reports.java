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
import org.apache.isis.applib.services.scratchpad.Scratchpad;
import org.apache.isis.core.runtime.system.context.IsisContext;

@DomainService(repositoryFor = Report.class)
@DomainServiceLayout(menuOrder = "20")
public class Reports {

	// {{ title
	public String title() {
		return "Reports";
	}
	// }}

	// {{ Report Actions
	@MemberOrder(sequence = "4")
	public Report createReport(final @ParameterLayout(named = "Name") String name) {
		final Report obj = container.newTransientInstance(Report.class);
		obj.setName(name);
		container.persistIfNotAlready(obj);
		return obj;
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "10")
	public List<Report> listReports() {
		return container.allInstances(Report.class);
	}

	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "20")
	public List<Report> findByName(@ParameterLayout(named = "Name") final String name) {
		return container.allMatches(new QueryDefault<>(Report.class, "findByName", "name", name));
	}
	
	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
	@MemberOrder(sequence = "20")
	public Report findFirst() {
		return container.firstMatch(new QueryDefault<>(Report.class, "find"));
	}
	// }}

	// {{ ReportSession actions
	/**
	 * Create a {@link ReportSession} that links the current user and the
	 * targeted report, delete all other {@link ReportSession}s for this user.
	 * 
	 * @param report
	 * @return the report
	 */
	public final Report openReport(final Report report) {
		closeReport(report);
		createUserReportSession(report);
		scratchpad.put("currentReport", report);
		return report;
	}
	
	public final Report findOpenReport(){
		final String userName = IsisContext.getAuthenticationSession().getUserName();
		List<ReportSession> sessions = container.allMatches(new QueryDefault<>(ReportSession.class, "findForUser", "user", userName));
		if (!sessions.isEmpty()){
			return sessions.get(0).getReport();
		}
		return null;
	}

	/**
	 * Close any/all open {@link ReportSession} for the current user.
	 * 
	 * @param report
	 * @return the report
	 */
	public final Report closeReport(final Report report) {
		final String userName = IsisContext.getAuthenticationSession().getUserName();
		List<ReportSession> sessions = container.allMatches(new QueryDefault<>(ReportSession.class, "findForUser", "user", userName));
		for (ReportSession reportSession : sessions) {
			container.remove(reportSession);
		}
		return report;
	}
	
	private final ReportSession createUserReportSession(final Report report) {
		final ReportSession obj = container.newTransientInstance(ReportSession.class);
		final String userName = IsisContext.getAuthenticationSession().getUserName();
		obj.setReport(report);
		obj.setUser(userName);
		container.persistIfNotAlready(obj);
		return obj;
	}
	
	// }}
	
	// {{ Internal low level functions

	// {{ injected services
	@javax.inject.Inject
	DomainObjectContainer container;
	
	@javax.inject.Inject
	Scratchpad scratchpad;	
	// }}
	
	// }}
}
