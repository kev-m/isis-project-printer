/**
 * 
 */
package com.cosylab.isis;

import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.SpecificationLoader;
import org.apache.isis.core.runtime.persistence.adapter.PojoAdapter;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.mementos.PropertyMemento;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import com.cosylab.Html;

/**
 * @author kevin
 *
 */
public class StringAsHTMLPanelFactory extends ComponentFactoryAbstract {
	private static final long serialVersionUID = 2668941049926738913L;
	private static final String ID_HTML = "mine";	

	public StringAsHTMLPanelFactory() {
		super(ComponentType.SCALAR_NAME_AND_VALUE, ID_HTML);
	}

	@Override
	protected ApplicationAdvice appliesTo(IModel<?> model) {
		if (model instanceof ScalarModel) {
			ScalarModel mo = (ScalarModel) model;
			ObjectSpecification os = mo.getTypeOfSpecification();
	        ObjectSpecification locatableSpec = getSpecificationLoader().loadSpecification(Html.class);
	        if (os.isOfType(locatableSpec)){
				System.out.println(locatableSpec);
	        }
			
			Object obj = model.getObject();
			PropertyMemento pm = ((ScalarModel) model).getPropertyMemento();
			if (pm != null && pm.getIdentifier().equals("description")){
				//return ApplicationAdvice.APPLIES_EXCLUSIVELY;
			}
			if (obj != null) {
				if (obj instanceof PojoAdapter) {
					PojoAdapter po = (PojoAdapter) obj;
					ObjectSpecification os2 =  po.getSpecification();
					System.out.println(os2.getSingularName());
					System.out.println(po.getSpecification().getCorrespondingClass().getName());
					if (po.getSpecification().getCorrespondingClass().isAnnotationPresent(Html.class)) {
						//return ApplicationAdvice.APPLIES;
					}
				}
			}
		}
		return ApplicationAdvice.DOES_NOT_APPLY;
	}
	

	@Override
	public Component createComponent(String id, IModel<?> model) {
		final ScalarModel bookmarkedPagesModel = (ScalarModel) model;
		return new HTMLAsPanelModel(id, bookmarkedPagesModel);
	}
	

    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSpecificationLoader();
    }	
}
