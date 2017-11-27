package eu.imagecode.scias.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class Functions {

    public static final String EOF_REG_EXP = "\\A";
    
    public static final String MULTIPART_NAME_BATCH = "batch";
    public static final String MULTIPART_NAME_IMAGES = "images";
    private static final String IMG_DIR = "img";

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

    /**
     * Prepares Batch {@link HttpEntity} for POST request
     *  
     * @param batchPath  path to XML batch file
     * @param imgs images to be attached to the batch
     * @return {@link HttpEntity} for given batch
     * @throws Exception
     */
    public static HttpEntity prepareBatchHttpEntity(String batchPath, String... imgs) throws Exception {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody(MULTIPART_NAME_BATCH, Functions.loadResourceAsString(batchPath),
                        ContentType.APPLICATION_XML);
        for (String img : imgs) {
            builder.addBinaryBody(MULTIPART_NAME_IMAGES, Functions.getResourceStream(IMG_DIR + "/" + img),
                            ContentType.APPLICATION_OCTET_STREAM, img);
        }
        return builder.build();
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
