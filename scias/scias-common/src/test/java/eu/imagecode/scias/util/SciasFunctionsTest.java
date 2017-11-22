package eu.imagecode.scias.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Timestamp;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.junit.Test;

import eu.imagecode.scias.service.ImageServiceTest;

public class SciasFunctionsTest {
    
    public static final String TEST_IMG1 = "img/Image01.jpg";
    public static final String IMG1_SHA256 = "7172698439171a96716f2190061f214c55a22feadafa460ca5b8b8e2ebf6482e";
    
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
    
    @Test
    public void testStreamToBytes() throws Exception {
        InputStream inStream = new FileInputStream(FileSystems.getDefault()
                        .getPath(ImageServiceTest.class.getClassLoader().getResource(TEST_IMG1).getPath()).toFile());
        byte[] inBytes = Files.readAllBytes(FileSystems.getDefault()
                        .getPath(ImageServiceTest.class.getClassLoader().getResource(TEST_IMG1).getPath()));
        assertArrayEquals(inBytes, SciasFunctions.streamToBytes(inStream));
    }
    
    @Test
    public void testByteDigest() throws Exception {
        byte[] inBytes = Files.readAllBytes(FileSystems.getDefault()
                        .getPath(ImageServiceTest.class.getClassLoader().getResource(TEST_IMG1).getPath()));
        String sha256 = SciasFunctions.digestToString(SciasFunctions.byteDigest(inBytes));
        assertEquals(IMG1_SHA256, sha256);
    }
    
    @Test
    public void testDayStringToTimestamp() {
        assertEquals(new Timestamp(1511305200000L), SciasFunctions.dayStringToTimestamp("2017-11-22"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDayStringToTimestampWrongFormat() {
        SciasFunctions.dayStringToTimestamp("2017/11/22");
    }
    
}
