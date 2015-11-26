/**
 * 
 */
package com.cosylab.isis;

import org.apache.isis.applib.adapters.AbstractValueSemanticsProvider;
import org.apache.isis.applib.adapters.EncoderDecoder;
import org.apache.isis.applib.adapters.Parser;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.metamodel.facets.object.value.vsp.ValueSemanticsProviderContext;

/**
 * @author kevin
 *
 */
public class RichContentValueSemanticsProvider extends AbstractValueSemanticsProvider<String>  {
	private final IsisConfiguration configuration;
	private final ValueSemanticsProviderContext context;

    public static final int TYPICAL_LENGTH = 25;
    private static final String DEFAULT_VALUE = null; // no default

    /**
     * Required because implementation of {@link Parser} and
     * {@link EncoderDecoder}.
     */
    public RichContentValueSemanticsProvider() {
        this(null, null);
    }

    public RichContentValueSemanticsProvider(final IsisConfiguration configuration, final ValueSemanticsProviderContext context) {
        super(true, true);
        this.configuration = configuration;
        this.context = context;
    }

    // //////////////////////////////////////////////////////////////////
    // Parser
    // //////////////////////////////////////////////////////////////////
    
    // /////// toString ///////

    @Override
    public String toString() {
        return "RichContentValueSemanticsProvider";
    }
}
