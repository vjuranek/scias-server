package eu.imagecode.scias.util;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;

public class Functions {

    /**
     * Extracts all {@link Analysis}es contained in @{link Batch}
     * 
     * @param batch
     *            to be uploaded
     * @return {@link List} of {@link Analysis} contained in the {link @Batch}
     */
    public static List<Analysis> analysesFromBatch(Batch batch) {
        List<Analysis> analyses = new ArrayList<>();
        batch.getSample().forEach(sample -> sample.getAnalysis().forEach(analysis -> analyses.add(analysis)));
        return analyses;
    }

    /**
     * Extracts all {@link ImageEntity} related to {@link AnalysisEntity}, i.e. input data image and unclassified data
     * images.
     * 
     * @param analysis
     *            {@link AnalysisEntity} which images will be extracted from.
     * @return {@link List} of all {@link ImageEntity} related to given {@link AnalysisEntity}.
     */
    public static List<ImageEntity> imageFromAnalysisEntity(AnalysisEntity analysis) {
        List<ImageEntity> images = new ArrayList<>();
        images.add(analysis.getInputData().getImage());
        analysis.getResultSet().getUnclassifiedObjects().forEach(uo -> images.add(uo.getImage()));
        return images;
    }

    /**
     * Same as {@link #imageFromAnalysisEntity(AnalysisEntity)}, but extracts images from {@link Analysis}
     * 
     * @param analysis
     *            {@link Analysis} which images will be extracted from.
     * @return {@link List} of all {@link Image} related to given {@link Analysis}.
     */
    public static List<Image> imageFromAnalysis(Analysis analysis) {
        List<Image> images = new ArrayList<>();
        images.add(analysis.getInputData().getImage());
        analysis.getResultSet().getUnclassifiedObject().forEach(uo -> images.add(uo.getImage()));
        return images;
    }

    /**
     * Puts all images related/contained in given {@link BatchEntity} to a {@link List}.
     * 
     * @param analysis
     *            {@link BatchEntity} from which images should be extracted.
     * @return {@link List} of {@link ImageEntity}s related to given batch.
     */
    public static List<Image> extractImages(Batch batch) {
        List<Analysis> analyses = new ArrayList<>();
        batch.getSample().forEach(sample -> analyses.addAll(sample.getAnalysis()));
        List<Image> imgs = new LinkedList<Image>();
        analyses.forEach(analysis -> {
            imgs.add(analysis.getInputData().getImage());
            analysis.getResultSet().getUnclassifiedObject().forEach(uo -> imgs.add(uo.getImage()));
        });
        return imgs;
    }

    /**
     * Checks incoming batch upload request, if the data are consistent (e.g. number of images references in batch is
     * same as number of imges to be uploaded).
     * 
     * @param batch
     *            Batch to be uploaded
     * @param imgMap
     *            Map of actual images to be uploaded
     * @throws IllegalArgumentException
     *             when there is found any problem with incomming request
     */
    public static void checkBatchUploadRequest(Batch batch, Map<String, byte[]> imgMap)
                    throws IllegalArgumentException, NoSuchAlgorithmException {

        List<Image> imgs = Functions.extractImages(batch);

        // check that number of images referenced in the batch is the same as number of actually uploaded images
        if (imgs.size() != imgMap.keySet().size()) {
            throw new IllegalStateException(
                            String.format("Number of images in batch doesn't match with number of images in the upload request, expected %d, but got %d",
                                            imgs.size(), imgMap.keySet().size()));
        }

        // check all images referenced in all analyses exists in
        for (Analysis ae : analysesFromBatch(batch)) {
            for (Image img : Functions.imageFromAnalysis(ae)) {

                // check image is present in input map
                if (!imgMap.containsKey(img.getName())) {
                    throw new IllegalArgumentException(String
                                    .format("Requested image %s is not contained in upload request", img.getName()));
                }

                // check sha-256 of the image is filled
                if (img.getSha256() == null || img.getSha256().isEmpty()) {
                    throw new IllegalArgumentException(String
                                    .format("Image with name %s has no or empty SHA-256 of the image", img.getName()));
                }

                // check sha-256 of the image agrees with computed sha-256 of the image byte content
                String sha256 = SciasFunctions.digestToString(SciasFunctions.byteDigest(imgMap.get(img.getName())));
                if (!img.getSha256().equals(sha256)) {
                    throw new IllegalArgumentException(
                                    String.format("SHA-256 of the image doesn't match, extecpted %s, but computed has is %s",
                                                    img.getSha256(), sha256));
                }

            }
        }

    }

    /**
     * Populate {@link ImageEntity}s in {@link AnalysisEntity} with images content (raw image bytes)
     * 
     * @param analysis
     *            {@link AnalysisEntity} which images should be populated
     * @param imgMap
     *            raw content of the images
     */
    public static void populateImages(AnalysisEntity analysis, Map<String, byte[]> imgMap) {
        for (ImageEntity img : imageFromAnalysisEntity(analysis)) {
            img.setBytes(imgMap.get(img.getName()));
        }
    }

}
