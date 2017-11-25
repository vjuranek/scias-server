package eu.imagecode.scias.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import eu.imagecode.scias.util.Functions;

public class AnalysisResourceIT {

    private static final String ANALYSIS_TIME_RESOURCE_URL = "http://localhost:8080/malaria-server/rest/analysis/time";
    private static final String BATCH_RESOURCE_URL = "http://localhost:8080/malaria-server/rest/station/f8ffba00-9134-4828-b34d-c03b4b2ee736/batch";
    public static final String MULTIPART_NAME_BATCH = "batch";
    public static final String MULTIPART_NAME_IMAGES = "images";
    public static final String HEADER_BATCH_ID = "BatchID";
    public static final String HEADER_ERROR_MSG = "ErrorMsg";
    private static final String IMG_DIR = "img";

    public static final String ROOT_ELEM = "analysis-set";
    public static final String ANALYSIS_ELEM = "analysis";
    public static final String ID_ELEM = "id";

    protected static CloseableHttpClient client = HttpClients.createDefault();
    
    @BeforeClass
    public static void setup() throws Exception {
        HttpPost batchPost = new HttpPost(BATCH_RESOURCE_URL);
        batchPost.setEntity(prepareBatchHttpEntity("xml/test_batch1.xml", "Image01.jpg", "Image02.jpg"));

        try (CloseableHttpResponse response = client.execute(batchPost)) {
            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
            Header idHeader = response.getFirstHeader(HEADER_BATCH_ID);
            assertNotNull(idHeader);
            int batchId = Integer.valueOf(idHeader.getValue());
            assertTrue(batchId > 0);
        }
    }

    @Test
    public void testTimeRangeQuery() throws Exception { 
        HttpGet queryReq = new HttpGet(ANALYSIS_TIME_RESOURCE_URL + "?from=2017-03-16&to=2017-03-18");

        try (CloseableHttpResponse response = client.execute(queryReq)) {
            assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
            InputStream is = response.getEntity().getContent();
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document xml = dBuilder.parse(is);
            xml.getDocumentElement().normalize();
            
            assertEquals(ROOT_ELEM, xml.getDocumentElement().getNodeName());
            NodeList ids = xml.getElementsByTagName(ID_ELEM);
            assertEquals(2, ids.getLength());
            assertEquals("1", ids.item(0).getTextContent());
            assertEquals("2", ids.item(1).getTextContent());
        }
    }

    private static HttpEntity prepareBatchHttpEntity(String batchPath, String... imgs) throws Exception {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addTextBody(MULTIPART_NAME_BATCH, Functions.loadResourceAsString(batchPath),
                        ContentType.APPLICATION_XML);
        for (String img : imgs) {
            builder.addBinaryBody(MULTIPART_NAME_IMAGES, Functions.getResourceStream(IMG_DIR + "/" + img),
                            ContentType.APPLICATION_OCTET_STREAM, img);
        }
        return builder.build();
    }

}
