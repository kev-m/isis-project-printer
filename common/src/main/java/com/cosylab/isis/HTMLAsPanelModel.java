/**
 * 
 */
package com.cosylab.isis;

import org.apache.wicket.Component;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.panels.PanelAbstract;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;

/**
 * @author kevin
 *
 */
public class HTMLAsPanelModel extends PanelAbstract<ScalarModel> {
	private static final long serialVersionUID = 1L;
	private static final String INVISIBLE_CLASS = "item-contents-as-html-invisible";
	private static final String ID_HTML = "myHtml";

	public HTMLAsPanelModel(String id, ScalarModel model) {
		super(id, model);
		// TODO Auto-generated constructor stub
		System.out.println("here: " + model.getLongName());
		buildGui();
	}

	private void buildGui() {

		final ScalarModel model = getModel();
		final ObjectAdapter adapter = model.getObject();

		// addOrReplace(map);
		// applyCssVisibility(map, !adapterList.isEmpty());

		// addMarkers(map, adapterList);
	}

	private static void applyCssVisibility(final Component component, final boolean visible) {
		final AttributeModifier modifier = visible
				? new AttributeModifier("class",
						String.valueOf(component.getMarkupAttributes().get("class")).replaceFirst(INVISIBLE_CLASS, ""))
				: new AttributeAppender("class", " " + INVISIBLE_CLASS);
		component.add(modifier);
	}

	@Override
	protected void onModelChanged() {
		buildGui();
	}

}
