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

public class AnalysisResourceIT {

    private static final String ANALYSIS_RESOURCE_URL = "http://localhost:8080/scias-water/rest/station/aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb/analysis";
    public static final String HEADER_ANALYSIS_ID = "AnalysisID";
    private static final String IMG_DIR = "img";

    @Test
    public void testUploadAndGet() throws Exception {
        int analId = postAnalysis("xml/test_analysis1.xml", "Image01.jpg", "Image02.jpg");
        assertTrue(analId > 0);
    }

    /**
     * Uploads XML file with analysis to remote server via HTTP form multipart post request. Request contains also all 
     * images which analysis refers to. Asserts that upload was successful and returns back ID of the uploaded analysis.
     * 
     * @param analPath Path to the XML file with analysis. Has to be class path resource.
     * @param imgs Names of the images which analysis refers to. Have to be class path resources.
     * @return ID of the analysis.
     * @throws Exception
     */
    private int postAnalysis(String analPath, String... imgs) throws Exception {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody("analysis", Functions.loadResourceAsString(analPath), ContentType.APPLICATION_XML);
        builder.addBinaryBody("images", Functions.getResourceStream(IMG_DIR + "/" + imgs[0]),
                        ContentType.APPLICATION_OCTET_STREAM, imgs[0]);
        builder.addBinaryBody("images", Functions.getResourceStream(IMG_DIR + "/" + imgs[1]),
                        ContentType.APPLICATION_OCTET_STREAM, imgs[1]);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost analPost = new HttpPost(ANALYSIS_RESOURCE_URL);
        HttpEntity multipart = builder.build();
        analPost.setEntity(multipart);
        
        try(CloseableHttpResponse response = client.execute(analPost)) {
            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
            Header idHeader = response.getFirstHeader(HEADER_ANALYSIS_ID);
            assertNotNull(idHeader);
            return Integer.valueOf(idHeader.getValue());
        }
    }
}
