/**
 * 
 */
package com.cosylab.isis;

import org.apache.isis.viewer.wicket.model.models.ValueModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;

/**
 * @author kevin
 *
 */
public class ClobAsPanelModel extends PanelAbstract<ValueModel> {
	private static final long serialVersionUID = 191477055996689289L;

	public ClobAsPanelModel(String id, ValueModel model) {
		super(id, model);
		// TODO Auto-generated constructor stub
		System.out.println("here: ");
		// return new Clob(Util.withSuffix(report.getName(), "html"),
		// "text/html", sb);
	}
}
