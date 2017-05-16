package eu.imagecode.scias.util;

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
        Map<String, InputPart> imgMap = inputFiles.stream().collect(
                        Collectors.toMap(file -> SciasFunctions.fileNameFromFormHeaders(file.getHeaders()), file -> file));
        return imgMap;
    }

}
