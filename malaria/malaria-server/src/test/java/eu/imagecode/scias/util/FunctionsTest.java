package eu.imagecode.scias.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.testutil.Generators;

public class FunctionsTest {
    
    @Test
    public void testAnalysesFromBatch() {
        Batch batch = Generators.generateBatch();
        List<Analysis> analyses = Functions.analysesFromBatch(batch);
        assertEquals(1, analyses.size());
        
        Analysis analysis = analyses.get(0);
        assertEquals(Generators.TEST_ANALYSIS_ID1, analysis.getId().intValue());
        assertEquals(Generators.TEST_ALGORITHM_VERSION, analysis.getAlgorithmVersion());
    }

    @Test
    public void testGetImageFromAnalysisEntity() throws Exception {
        List<ImageEntity> imgs = Functions.imageFromAnalysisEntity(Generators.generateAnalysisEntity());
        Generators.assertImgEntNames(imgs);
    }
    
    @Test
    public void testGetImageFromAnalysis() {
        List<Image> imgs = Functions.imageFromAnalysis(Generators.generateAnalysis());
        Generators.assertImgNames(imgs);
    }

    
    @Test
    public void testExtractImages() {
        Batch batch = Generators.generateBatch();
        List<Image> imgs = Functions.extractImages(batch);
        Generators.assertImgNames(imgs);
    }
}
