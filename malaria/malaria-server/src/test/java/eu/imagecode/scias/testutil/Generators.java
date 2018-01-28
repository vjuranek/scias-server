package eu.imagecode.scias.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.CellEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.InputDataEntity;
import eu.imagecode.scias.model.jpa.MimeTypeEntity;
import eu.imagecode.scias.model.jpa.ResultSetEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Cell;
import eu.imagecode.scias.model.rest.malaria.DetectedObject;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.model.rest.malaria.InputData;
import eu.imagecode.scias.model.rest.malaria.MimeType;
import eu.imagecode.scias.model.rest.malaria.ResultSet;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.util.SciasFunctions;

public class Generators {

    public static final Date TEST_DATE = new Date();
    public static final Timestamp TEST_TIMESTAMP = new Timestamp(TEST_DATE.getTime());
    public static final String TEST_IMG1_NAME = "Image01.jpg";
    public static final String TEST_IMG2_NAME = "Image02.jpg";
    public static final String TEST_IMG3_NAME = "Image03.jpg";
    public static final String TEST_IMG1_SHA256 = "7172698439171a96716f2190061f214c55a22feadafa460ca5b8b8e2ebf6482e";
    public static final String TEST_IMG2_SHA256 = "7172698439171a96716f2190061f214c55a22feadafa460ca5b8b8e2ebf6482e";
    public static final String TEST_IMG3_SHA256 = "7172698439171a96716f2190061f214c55a22feadafa460ca5b8b8e2ebf6482e";
    public static final MimeType TEST_IMG_MIME_TYPE = MimeType.IMAGE_JPEG;
    public static final int TEST_IMG_WIDTH = 10;
    public static final double TEST_PIXEL_SIZE = 2.0;

    public static int TEST_ANALYSIS_ID1 = 1;
    public static final String TEST_ALGORITHM_VERSION = "1.0";
    public static final String TEST_TRAIN_DATA_VERSION = "1.0";

    public static Analysis generateAnalysis() {
        return generateAnalysis(1);
    }
    
    public static Analysis generateAnalysis(int analysisId) {

        Image uoImg1 = new Image();
        uoImg1.setId(1);
        uoImg1.setName(TEST_IMG2_NAME);
        uoImg1.setSha256(TEST_IMG2_SHA256);
        uoImg1.setMimeType(TEST_IMG_MIME_TYPE);
        uoImg1.setPixelSize(TEST_PIXEL_SIZE);
        uoImg1.setWidth(TEST_IMG_WIDTH);

        Image uoImg2 = new Image();
        uoImg2.setId(2);
        uoImg2.setName(TEST_IMG3_NAME);
        uoImg2.setSha256(TEST_IMG3_SHA256);
        uoImg2.setMimeType(TEST_IMG_MIME_TYPE);
        uoImg2.setPixelSize(TEST_PIXEL_SIZE);
        uoImg2.setWidth(TEST_IMG_WIDTH);
        
        DetectedObject do1 = new DetectedObject();
        do1.setId(1);
        do1.setHeight(10);
        do1.setWidth(20);
        do1.setX(100);
        do1.setY(200);
        do1.setIdClass(1);
        
        DetectedObject do2 = new DetectedObject();
        do2.setId(2);
        do2.setHeight(30);
        do2.setWidth(40);
        do2.setX(300);
        do2.setY(400);
        do2.setIdClass(2);

        Cell cell1 = new Cell();
        cell1.setId(1);
        cell1.setHeight(10);
        cell1.setWidth(20);
        cell1.setX(100);
        cell1.setY(200);
        cell1.getDetectedObject().add(do1);
        cell1.getDetectedObject().add(do2);

        Cell cell2 = new Cell();
        cell2.setId(2);
        cell2.setHeight(30);
        cell2.setWidth(40);
        cell2.setX(300);
        cell2.setY(400);

        ResultSet rs = new ResultSet();
        rs.setId(1);
        rs.getCell().add(cell1);
        rs.getCell().add(cell2);

        Image img = new Image();
        img.setId(1);
        img.setHeight(10);
        img.setName(TEST_IMG1_NAME);
        img.setPixelSize(TEST_PIXEL_SIZE);
        img.setSha256(TEST_IMG1_SHA256);
        img.setWidth(TEST_IMG_WIDTH);
        img.setMimeType(TEST_IMG_MIME_TYPE);

        InputData input = new InputData();
        input.setId(1);
        input.setImage(img);

        Analysis anal = new Analysis();
        anal.setId(analysisId);
        anal.setAlgorithmVersion(TEST_ALGORITHM_VERSION);
        anal.setCreated(TEST_DATE);
        anal.setResultSet(rs);
        anal.setTrainDataVersion(TEST_TRAIN_DATA_VERSION);
        anal.setInputData(input);

        return anal;
    }

    public static Batch generateBatch() {
        Sample sample = new Sample();
        sample.setId(1);
        sample.getAnalysis().add(generateAnalysis());

        Batch batch = new Batch();
        batch.setId(1);
        batch.getSample().add(sample);

        return batch;
    }

    public static AnalysisEntity generateAnalysisEntity() throws Exception {
        CellEntity cell1 = new CellEntity();
        cell1.setId(1);
        cell1.setHeight(10);
        cell1.setWidth(20);
        cell1.setX(100);
        cell1.setY(200);
        
        ResultSetEntity rs = new ResultSetEntity();
        Set<CellEntity> cells = new HashSet<>();
        cells.add(cell1);
        rs.setCells(cells);

        ImageEntity img = new ImageEntity();
        img.setLocalId(1);
        img.setHeight(10);
        img.setName(TEST_IMG1_NAME);
        img.setPixelSize(TEST_PIXEL_SIZE);
        img.setSha256(TEST_IMG1_SHA256);
        img.setWidth(TEST_IMG_WIDTH);
        img.setMimeType(MimeTypeEntity.IMAGE_JPEG);
        InputDataEntity input = new InputDataEntity();
        input.setImage(img);

        AnalysisEntity anal = new AnalysisEntity();
        anal.setAlgorithmVersion(TEST_ALGORITHM_VERSION);
        anal.setCreated(TEST_TIMESTAMP);
        anal.setResultSet(rs);
        anal.setInputData(input);

        return anal;
    }
    
    public static Map<String, byte[]> generateOneImgMap() throws IOException {
        Map<String, byte[]> imgMap = new HashMap<>();
        imgMap.put(TEST_IMG1_NAME, loadImgBytes(TEST_IMG1_NAME));
        return imgMap;
    }
    
    public static Map<String, byte[]> generateImgMap() throws IOException {
        Map<String, byte[]> imgMap = new HashMap<>();
        imgMap.put(TEST_IMG1_NAME, loadImgBytes(TEST_IMG1_NAME));
        imgMap.put(TEST_IMG2_NAME, loadImgBytes(TEST_IMG2_NAME));
        imgMap.put(TEST_IMG3_NAME, loadImgBytes(TEST_IMG3_NAME));
        return imgMap;
    }

    public static void assertFirstImgName(List<Image> imgs) {
        assertEquals(1, imgs.size());
        assertTrue(imgs.get(0).getName().contains(Generators.TEST_IMG1_NAME));
    }
    
    public static void assertImgNames(List<Image> imgs) {
        assertEquals(3, imgs.size());

        List<String> imgNames = new LinkedList<>();
        imgs.forEach(img -> imgNames.add(img.getName()));

        assertTrue(imgNames.contains(Generators.TEST_IMG1_NAME));
        assertTrue(imgNames.contains(Generators.TEST_IMG2_NAME));
        assertTrue(imgNames.contains(Generators.TEST_IMG3_NAME));
    }

    public static void assertFirstImgEntName(List<ImageEntity> imgs) {
        assertEquals(1, imgs.size());
        assertTrue(imgs.get(0).getName().contains(Generators.TEST_IMG1_NAME));
    }
    
    public static void assertImgEntNames(List<ImageEntity> imgs) {
        assertEquals(3, imgs.size());

        List<String> imgNames = new LinkedList<>();
        imgs.forEach(img -> imgNames.add(img.getName()));

        assertTrue(imgNames.contains(Generators.TEST_IMG1_NAME));
        assertTrue(imgNames.contains(Generators.TEST_IMG2_NAME));
        assertTrue(imgNames.contains(Generators.TEST_IMG3_NAME));
    }

    private static byte[] loadImgBytes(String imgName) throws IOException {
        return SciasFunctions.streamToBytes(Generators.class.getClassLoader().getResourceAsStream("img/" + imgName));
    }

}
