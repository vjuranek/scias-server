package eu.imagecode.scias.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * 
 * @author vjuranek
 *
 */
public class SciasFunctions {

    private static final int BUFFER_SIZE = 1024; // TODO put into some global configuration?
    private static final String HASH_ALG = "SHA-256";

    /**
     * Converts digest byte array into string digest
     * 
     * @param digest
     * @return
     */
    public static String digestToString(byte[] digest) {
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static <T> String imgListToString(List<T> imgList) {
        StringBuilder sb = new StringBuilder();
        imgList.forEach(it -> {
            sb.append(it.toString());
            sb.append(System.lineSeparator());
        });
        return sb.toString();
    }

    /**
     * Extracts file name from HTTP multipart/form-data request headers for file upload request.
     * 
     * @param headers
     *            HTTP header of on part of multipart/form-data HTTP request.
     * @return File name of file which is uploaded in given part of multipart/form-data HTTP request.
     */
    public static String fileNameFromFormHeaders(MultivaluedMap<String, String> headers) {
        for (String part : headers.getFirst("Content-Disposition").split(";")) {
            if (part.trim().startsWith("filename")) {
                return part.split("=")[1].trim().replaceAll("\"", "");
            }
        }
        throw new IllegalStateException("Content-Disposition header doesn't contain 'filename' part");
    }

    /**
     * Converts raw form input to {@link Map}, where keys are image names and value are appropriate {@link InputPart}s
     * for given image name.
     * 
     * @param inputFiles
     *            image part of {@link MultipartFormDataInput}
     * @return {@link Map} which connets image names with appropriate {@link InputPart}s of
     *         {@link MultipartFormDataInput}
     */
    public static Map<String, InputPart> formInputToImageMap(List<InputPart> inputFiles) {
        Map<String, InputPart> imgMap = inputFiles.stream().collect(Collectors
                        .toMap(file -> SciasFunctions.fileNameFromFormHeaders(file.getHeaders()), file -> file));
        return imgMap;
    }

    /**
     * Converts raw form input to {@link Map}, where keys are image names and value are byte arrays of appropriate
     * {@link InputPart}s for given image name.
     * 
     * @param inputFiles
     *            image part of {@link MultipartFormDataInput}
     * @return {@link Map} which connets image names with appropriate byte array of {@link MultipartFormDataInput}
     * @throws IOException
     */
    public static Map<String, byte[]> formInputToByteMap(List<InputPart> inputParts) throws IOException {
        Map<String, byte[]> imgMap = new HashMap<>();
        for (InputPart inPart : inputParts) {
            imgMap.put(SciasFunctions.fileNameFromFormHeaders(inPart.getHeaders()),
                            streamToBytes(inPart.getBody(InputStream.class, null)));
        }
        return imgMap;
    }

    /**
     * Converts {@link InputStream} to byte array
     * 
     * @param inStream
     *            {@link InputStream} to be converted to byte array
     * @return content of {@link InputStream} as byte array
     * @throws IOException
     */
    public static byte[] streamToBytes(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[BUFFER_SIZE];
        int len = 0;
        while ((len = inStream.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        return baos.toByteArray();
    }

    /**
     * Computes SHA256 digest of provided bytes
     * 
     * @param bytes
     *            which SHA-256 should be computed
     * @return SHA-256 of provided bytes
     * @throws NoSuchAlgorithmException
     */
    public static byte[] byteDigest(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(HASH_ALG);
        md.update(bytes);
        return md.digest();

    }
    
    /**
     * Converts date represented as string into {@link Timestamp}.
     * 
     * @param day date in format YYYY-MM-DD
     * @return {@link Timestamp} corresponding to provided string representration of the date 
     */
    public static Timestamp dayStringToTimestamp(String day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try  {
            d = format.parse(day);
        } catch(ParseException e) {
            throw new IllegalArgumentException(String.format("Wrong date format '%s', expected format is 'YYYY-MM-DD'", day));
        }
        return new Timestamp(d.getTime());
        
    }

}
