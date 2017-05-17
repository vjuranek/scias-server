package eu.imagecode.scias.util;

import java.util.ArrayList;
import java.util.List;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;

public class Functions {
    
    /**
     * Extracts all {@link ImageEntity} related to {@link AnalysisEntity}, i.e. input data image and unclassified data images.
     * 
     * @param analysis {@link AnalysisEntity} which images will be extracted from. 
     * @return {@link List} of all {@link ImageEntity} related to given {@link AnalysisEntity}.
     */
    public static List<ImageEntity> imageFromAnalysis(AnalysisEntity analysis) {
        List<ImageEntity> images = new ArrayList<>();
        images.add(analysis.getInputData().getImage());
        analysis.getResultSet().getUnclassifiedObjects().forEach(uo -> images.add(uo.getImage()));
        return images;
    }

}
