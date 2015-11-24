/**
 * 
 */
package com.cosylab;

/**
 * @author kevin
 *
 */
public class Util {
    private Util(){}

    public static String withSuffix(String fileName, String suffix) {
        if(!suffix.startsWith(".")) {
            suffix = "." + suffix;
        }
        if(!fileName.endsWith(suffix)) {
            fileName += suffix;
        }
        return fileName;
    }
}
