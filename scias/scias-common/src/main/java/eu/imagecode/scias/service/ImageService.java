package eu.imagecode.scias.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;

import eu.imagecode.scias.util.SciasFunctions;

@Stateless
public class ImageService {

    private static final int BUFFER_SIZE = 1024; // TODO put into some global configuration?
    private static final String HASH_ALG = "SHA-256";

    public void uploadImage(String expectedSHA256, File uploadedFile, InputStream imageStream)
                    throws IOException, IllegalStateException, NoSuchAlgorithmException {
        String uploadedSHA = SciasFunctions.digestToString(copyStream(uploadedFile, imageStream));

        if (!expectedSHA256.equals(uploadedSHA)) {
            throw new IllegalStateException(String.format("SHA256 doesn match! Expected: %s, uploaded: %s",
                            expectedSHA256, uploadedSHA));
        }
    }

    /* package */ byte[] copyStream(File uploadedFile, InputStream imageStream)
                    throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALG);
        try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
            byte[] buff = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = imageStream.read(buff)) != -1) {
                fos.write(buff, 0, len);
                md.update(buff, 0, len);
            }
        }
        return md.digest();
    }

}
