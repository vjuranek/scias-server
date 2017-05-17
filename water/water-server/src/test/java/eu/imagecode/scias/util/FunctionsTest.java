package eu.imagecode.scias.util;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.testutil.Generators;

public class FunctionsTest {

    @Test
    public void testGetImageFromAnalysis() {
        List<ImageEntity> imgs = Functions.imageFromAnalysis(Generators.generateAnalysisEntity());
        List<String> imgNames = new LinkedList<>();
        imgs.forEach(img -> imgNames.add(img.getName()));
        
        assertTrue(imgNames.contains(Generators.TEST_IMG_NAME));
        assertTrue(imgNames.contains(Generators.TEST_UO_IMG_NAME1));
        assertTrue(imgNames.contains(Generators.TEST_UO_IMG_NAME2));
    }

}
