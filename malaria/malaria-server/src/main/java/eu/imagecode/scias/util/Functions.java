package eu.imagecode.scias.util;

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
