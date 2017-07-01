package eu.imagecode.scias.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import eu.imagecode.scias.util.Functions;

public class BatchResourceIT {
    
    private static final String BATCH_RESOURCE_URL = "http://localhost:8080/malaria-server/rest/station/f8ffba00-9134-4828-b34d-c03b4b2ee736/batch";
    private static final String BATCH_NON_EXISTING_STATION_RESOURCE_URL = "http://localhost:8080/malaria-server/rest/station/doesntexists/batch";
    public static final String MULTIPART_NAME_BATCH = "batch";
    public static final String MULTIPART_NAME_IMAGES = "images";
    public static final String HEADER_BATCH_ID = "BatchID";
    public static final String HEADER_ERROR_MSG = "ErrorMsg";
    private static final String IMG_DIR = "img";

    /**
     * Uploads XML file with batch to remote server via HTTP form multipart post request. Request contains also all 
     * images which analysis refers to. Asserts that upload was successful and returns back ID of the uploaded analysis.
     * 
     */
    @Test
    public void testUploadAndGet() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost batchPost = new HttpPost(BATCH_RESOURCE_URL);
        batchPost.setEntity(prepareBatchHttpEntity("xml/test_batch1.xml", "Image01.jpg", "Image02.jpg"));
        
        try(CloseableHttpResponse response = client.execute(batchPost)) {
            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
            Header idHeader = response.getFirstHeader(HEADER_BATCH_ID);
            assertNotNull(idHeader);
            int batchId = Integer.valueOf(idHeader.getValue());
            assertTrue(batchId > 0);
        }
    }
    
    @Test
    public void testUploadFromNonExistingStation() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost batchPost = new HttpPost(BATCH_NON_EXISTING_STATION_RESOURCE_URL);
        batchPost.setEntity(prepareBatchHttpEntity("xml/test_batch1.xml", "Image01.jpg", "Image02.jpg"));
        
        try(CloseableHttpResponse response = client.execute(batchPost)) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
            Header errorMsg = response.getFirstHeader(HEADER_ERROR_MSG);
            assertNotNull(errorMsg);
            assertTrue(errorMsg.getValue().equals("java.lang.IllegalArgumentException: Station with UUID 'doesntexists' doesn't exist in database!"));
        }
    }
    
    @Test
    public void testWrongNumberImgs() throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost batchPost = new HttpPost(BATCH_RESOURCE_URL);
        batchPost.setEntity(prepareBatchHttpEntity("xml/test_batch1.xml", "Image01.jpg"));
        
        try(CloseableHttpResponse response = client.execute(batchPost)) {
            assertEquals(HttpStatus.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
            Header errorMsg = response.getFirstHeader(HEADER_ERROR_MSG);
            assertNotNull(errorMsg);
            assertTrue(errorMsg.getValue().startsWith("Number of images in batch doesn't match"));
        }
    }
    
    private HttpEntity prepareBatchHttpEntity(String batchPath, String... imgs) throws Exception {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody(MULTIPART_NAME_BATCH, Functions.loadResourceAsString(batchPath), ContentType.APPLICATION_XML);
        for (String img : imgs) {
            builder.addBinaryBody(MULTIPART_NAME_IMAGES, Functions.getResourceStream(IMG_DIR + "/" + img),
                        ContentType.APPLICATION_OCTET_STREAM, img);
        }
        return builder.build();
    }

}
