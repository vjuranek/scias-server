package eu.imagecode.scias.service;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.InputDataEntity;
import eu.imagecode.scias.model.jpa.ResultSetEntity;
import eu.imagecode.scias.model.jpa.UnclassifiedObjectEntity;

public class AnalysisServiceTest {

    @Test
    public void testExtractImages() {
        ImageEntity uo1 = new ImageEntity();
        uo1.setName("uoimg1");
        uo1.setSha256("uo12345");
        UnclassifiedObjectEntity ucl1 = new UnclassifiedObjectEntity();
        ucl1.setImage(uo1);
        ImageEntity uo2 = new ImageEntity();
        uo2.setName("uoimg2");
        uo2.setSha256("uo54321");
        UnclassifiedObjectEntity ucl2 = new UnclassifiedObjectEntity();
        ucl2.setImage(uo2);
        Set<UnclassifiedObjectEntity> uoSet = new HashSet<>();
        uoSet.add(ucl1);
        uoSet.add(ucl2);
        ResultSetEntity res = new ResultSetEntity();
        res.setUnclassifiedObjects(uoSet);
        
        ImageEntity img1 = new ImageEntity();
        img1.setName("img1");
        img1.setSha256("12345");
        InputDataEntity input1 = new InputDataEntity();
        input1.setImage(img1);
        AnalysisEntity analysis = new AnalysisEntity();
        
        analysis.setInputData(input1);
        analysis.setResultSet(res);
        
        AnalysisService analService = new AnalysisService();
        List<ImageEntity> imgs = analService.extractImages(analysis);
        
        assertEquals(3, imgs.size());
    }
    
}
