/**
 * 
 */
package com.cosylab.reports;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.value.Clob;

import com.cosylab.ReportPrinter;
import com.cosylab.Util;
import com.cosylab.dom.report.Report;
import com.cosylab.dom.simple.Project;

/**
 * @author kevin
 *
 */
@DomainService
@DomainServiceLayout(menuOrder = "30", named="Print")
public class Latex implements ReportPrinter {

	public Clob printAsLatex(Report report){
		StringBuilder sb = new StringBuilder();
		sb.append("Header\n");
		sb.append("<h1>"+report.getName()+"</h1>\n");
		report.getProjects();
		for (Project project : report.getProjects()) {
			sb.append("<h2>"+project.getName()+"</h2>");
			sb.append("<p>"+project.getDescription()+"</p>");
		}
		return new Clob(Util.withSuffix(report.getName(), "tex"), "text/latex", sb);
		//return sb.toString();
	}
}
