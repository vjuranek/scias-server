package eu.imagecode.scias.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import eu.imagecode.scias.util.SciasFunctions;

@Stateless
public class ImageService {

    @Inject
    Logger log;

    private static final int BUFFER_SIZE = 1024; // TODO put into some global configuration?
    private static final String HASH_ALG = "SHA-256";

    public void uploadImage(String expectedSHA256, File uploadedFile, InputStream imageStream)
                    throws IOException, IllegalStateException, NoSuchAlgorithmException {
        String uploadedSHA = SciasFunctions.digestToString(copyStream(uploadedFile, imageStream));

        log.fine(String.format("File '%s': expected hash '%s', actual hash '%s'", uploadedFile.getName(),
                        expectedSHA256, uploadedSHA));
        if (!expectedSHA256.equals(uploadedSHA)) {
            log.warning(String.format("File '%s': expected hash (%s) is not the same as hash of uploade bits (%s)",
                            uploadedFile.getName(), expectedSHA256, uploadedSHA));
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
