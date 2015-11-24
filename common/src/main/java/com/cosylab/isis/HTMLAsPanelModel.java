/**
 * 
 */
package com.cosylab.isis;

import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;

/**
 * @author kevin
 *
 */
public class HTMLAsPanelModel extends PanelAbstract<ScalarModel> {
	private static final long serialVersionUID = 191477055996689289L;
	
	public HTMLAsPanelModel(String id, ScalarModel model) {
		super(id, model);
		// TODO Auto-generated constructor stub
		System.out.println("here: " + model.getLongName());
	}
}
