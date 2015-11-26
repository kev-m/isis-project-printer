/**
 * 
 */
package com.cosylab.isis;

import org.apache.isis.applib.value.Clob;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.SpecificationLoader;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.viewer.wicket.model.models.ValueModel;
import org.apache.isis.viewer.wicket.ui.ComponentFactoryAbstract;
import org.apache.isis.viewer.wicket.ui.ComponentType;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import com.cosylab.ReportPrinter;

/**
 * @author kevin
 *
 */
public class ClobAsHTMLPanelFactory extends ComponentFactoryAbstract {
	private static final long serialVersionUID = 2668941049926738913L;
	private static final String ID_HTML = "myHtml";	

	public ClobAsHTMLPanelFactory() {
		super(ComponentType.VALUE, ID_HTML);
	}

	@Override
	protected ApplicationAdvice appliesTo(IModel<?> model) {
		if (model instanceof ValueModel) {
			ValueModel mo = (ValueModel) model;
			final ObjectSpecification specification = mo.getActionModelHint().getTargetAdapter().getSpecification();
			final Class<?> correspondingClass = specification.getCorrespondingClass();
			if (correspondingClass.isAssignableFrom(ReportPrinter.class)){
				return ApplicationAdvice.APPLIES_EXCLUSIVELY;
			}
	        if (correspondingClass.getName().equals("com.cosylab.dom.report.Report")){
				System.out.println("Here");
				return ApplicationAdvice.APPLIES_EXCLUSIVELY;
	        }

	        /* Remove all this
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
			*/
		}
		return ApplicationAdvice.DOES_NOT_APPLY;
	}
	

	@Override
	public Component createComponent(String id, IModel<?> model) {
		final ValueModel valueModel = (ValueModel) model;
		return new ClobAsHtmlModel(id, valueModel);
	}
	

    protected SpecificationLoader getSpecificationLoader() {
        return IsisContext.getSpecificationLoader();
    }	
}
