/**
 * 
 */
package com.cosylab.isis;

import java.io.IOException;
import java.io.Writer;

import javax.jdo.annotations.Column;

import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Value;

import com.cosylab.ReportPrinter;
import com.google.common.io.CharStreams;
import com.google.common.io.OutputSupplier;

/**
 * @author kevin
 *
 */
@Value(semanticsProviderClass=RichContentValueSemanticsProvider.class)
public class RichContent implements ReportPrinter {
	@Column(allowsNull = "false")
	@PropertyLayout( multiLine = 10, typicalLength = 500)
    private final String chars;
    
    public RichContent(String chars){
        this.chars = chars;
    }

    public CharSequence getChars() {
        return chars;
    }
    
    public void writeCharsTo(final Writer wr) throws IOException {
        CharStreams.write(chars, new OutputSupplier<Writer>() {
            @Override
            public Writer getOutput() throws IOException {
                return wr;
            }
        });
    }

    @Override
    public String toString() {
        return getChars().length() + " chars";
    }
	
}
