package eu.imagecode.scias.util;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.junit.Test;

public class SciasFunctionsTest {
    
    @Test
    public void testDigestToString() {
        byte[] digest = {78,-64,16,-118,-80,39,-81,25,-12,55,-29,104,101,-99,67,60,124,64,-43,-113,27,115,107,-10,95,109,-88,-18,-121,-38,117,14};
        assertEquals("4ec0108ab027af19f437e368659d433c7c40d58f1b736bf65f6da8ee87da750e", SciasFunctions.digestToString(digest));
    }
    
    @Test
    public void testFileNameFromFormHeaders() {
        MultivaluedMap<String, String> headers = new MultivaluedMapImpl<>();
        headers.add("Content-Disposition", "form-data; name=\"testFile\"; filename=\"testFile.jpg\"");
        headers.add("some-header-name", "form-data; name=\"some-name\"; filename=\"someTestFile.jpg\"");
        headers.add("some-header-name2", "form-data; name=\"some name\"; value=\"some value\"");
        headers.add("Content-Disposition", "form-data; name=\"testFile2\"; filename=\"testFile2.jpg\"");
        
        assertEquals("testFile.jpg", SciasFunctions.fileNameFromFormHeaders(headers));
    }
    
}
