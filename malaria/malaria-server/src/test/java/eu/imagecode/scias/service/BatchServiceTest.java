package eu.imagecode.scias.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.model.rest.malaria.InputData;
import eu.imagecode.scias.model.rest.malaria.ResultSet;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.model.rest.malaria.UnclassifiedObject;

public class BatchServiceTest {

    @Test
    public void testExtractImages() {
        Image uo1 = new Image();
        uo1.setName("uoimg1");
        uo1.setSha256("uo12345");
        UnclassifiedObject ucl1 = new UnclassifiedObject();
        ucl1.setImage(uo1);
        Image uo2 = new Image();
        uo2.setName("uoimg2");
        uo2.setSha256("uo54321");
        UnclassifiedObject ucl2 = new UnclassifiedObject();
        ucl2.setImage(uo2);
        ResultSet res = new ResultSet();
        res.getUnclassifiedObject().add(ucl1);
        res.getUnclassifiedObject().add(ucl2);
        
        Image img1 = new Image();
        img1.setName("img1");
        img1.setSha256("12345");
        InputData input1 = new InputData();
        input1.setImage(img1);
        Analysis analysis = new Analysis();
        
        analysis.setInputData(input1);
        analysis.setResultSet(res);
        
        Sample sample = new Sample();
        sample.getAnalysis().add(analysis);
        
        Batch batch = new Batch();
        batch.getSample().add(sample);
        
        BatchService batchSrv = new BatchService();
        List<Image> imgs = batchSrv.extractImages(batch);
        
        assertEquals(3, imgs.size());
    }
}
