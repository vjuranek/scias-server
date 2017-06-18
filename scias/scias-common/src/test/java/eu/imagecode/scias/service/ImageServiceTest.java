package eu.imagecode.scias.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.security.MessageDigest;

import org.junit.Test;

import eu.imagecode.scias.util.SciasFunctions;

public class ImageServiceTest {

    public static final String TEST_IMG1 = "img/Image01.jpg";
    public static final String IMG1_SHA256 = "7172698439171a96716f2190061f214c55a22feadafa460ca5b8b8e2ebf6482e";

    @Test
    public void testUploadImage() throws Exception {
        InputStream in = ImageServiceTest.class.getClassLoader().getResourceAsStream(TEST_IMG1);
        File out = File.createTempFile("tmp-test-", ".jpg");

        ImageService imgService = new ImageService();
        imgService.uploadImage(IMG1_SHA256, out, in);

        byte[] inBytes = Files.readAllBytes(FileSystems.getDefault()
                        .getPath(ImageServiceTest.class.getClassLoader().getResource(TEST_IMG1).getPath()));
        byte[] outBytes = Files.readAllBytes(out.toPath());

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(outBytes);
        String outSha256 = SciasFunctions.digestToString(md.digest());

        assertEquals(IMG1_SHA256, outSha256);
        assertArrayEquals(inBytes, outBytes);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testWrongImageSha256() throws Exception {
        InputStream in = ImageServiceTest.class.getClassLoader().getResourceAsStream(TEST_IMG1);
        String sha256 = "blablabla";
        File out = File.createTempFile("tmp-test-", ".jpg");

        ImageService imgService = new ImageService();
        imgService.uploadImage(sha256, out, in);  
    }

}
