package eu.imagecode.scias.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;

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
    public static void checkBatchUploadRequest(Batch batch, Map<String, InputPart> imgMap)
                    throws IllegalArgumentException {

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
                if (!imgMap.containsKey(img.getName())) {
                    throw new IllegalArgumentException(String
                                    .format("Requested image %s is not contained in upload request", img.getName()));
                }
            }
        }

    }

}
