package eu.imagecode.scias.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Functions {

    public static final String EOF_REG_EXP = "\\A";

    /**
     * Gets {@link InputStream} of the class path resource.
     * 
     * @param resourceName Name of the class path resource.
     * @return @{link {@link InputStream} of the requested resource.
     */
    public static InputStream getResourceStream(String resourceName) {
        return Functions.class.getClassLoader().getResourceAsStream(resourceName);
    }
    
    /**
     * Loads file content as {@link String}. File is assumed to be on the class path, i.e. a resource file.
     * 
     * @param resourceName Name of the class path resource.
     * @return Content of the resource as {@link String}.
     * @throws IOException
     */
    public static String loadResourceAsString(String resourceName) throws IOException {
        try(InputStream is = getResourceStream(resourceName)) {
            return readStreamContent(is);
        }
    }
    
    /**
     * Loads file content as {@link String}.
     * 
     * @param filePath Full path to file to be read.
     * @return Content of the file as {@link String}.
     * @throws IOException
     */
    public static String loadFile(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(new File(filePath))) {
            return readStreamContent(is);
        } 
    }

    
    private static String readStreamContent(InputStream stream) {
        String content = null;
        try(Scanner s = new Scanner(stream)) {
            s.useDelimiter(EOF_REG_EXP);
            if(s.hasNext()) {
                content = s.next();
            }
        }
        return content;
    }

}
