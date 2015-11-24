/**
 * 
 */
package com.cosylab.isis;

import org.apache.isis.viewer.wicket.viewer.registries.components.ComponentFactoryRegistrarDefault;

import com.google.inject.Singleton;

/**
 * @author kevin
 *
 */
@Singleton
public class MyComponentFactoryRegistrar extends ComponentFactoryRegistrarDefault {
    @Override
    public void addComponentFactories(ComponentFactoryList componentFactories) {
        super.addComponentFactories(componentFactories);
        //componentFactories.add(new StringAsHTMLPanelFactory());
        //componentFactories.add(new ClobAsHTMLPanelFactory());
    }	

}
