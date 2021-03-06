package eu.imagecode.scias.testutil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.InputDataEntity;
import eu.imagecode.scias.model.jpa.MimeTypeEntity;
import eu.imagecode.scias.model.jpa.ResultSetEntity;
import eu.imagecode.scias.model.jpa.UnclassifiedObjectEntity;
import eu.imagecode.scias.model.rest.Analysis;
import eu.imagecode.scias.model.rest.Image;
import eu.imagecode.scias.model.rest.InputData;
import eu.imagecode.scias.model.rest.MimeType;
import eu.imagecode.scias.model.rest.ResultSet;

public class Generators {
    
    public static final Date TEST_DATE = new Date();
    public static final Timestamp TEST_TIMESTAMP = new Timestamp(TEST_DATE.getTime());
    public static final String TEST_IMG_NAME = "test img";
    public static final String TEST_UO_IMG_NAME1 = "unclass img1";
    public static final String TEST_UO_IMG_NAME2 = "unclass img2";
    public static final int TEST_IMG_WIDTH = 10;
    public static final double TEST_PIXEL_SIZE = 2.0; 
    public static final String TEST_ALGORITHM_VERSION = "1.0";
    public static final String TEST_TRAIN_DATA_VERSION = "1.0";
    
    public static Analysis generateAnalysis() {
        ResultSet rs = new ResultSet(); //TODO full result set
        rs.setId(1);
        Image img = new Image();
        img.setId(1);
        img.setHeight(10);
        img.setName(TEST_IMG_NAME);
        img.setPixelSize(TEST_PIXEL_SIZE);
        img.setSha256("aaaaaaaa");
        img.setWidth(TEST_IMG_WIDTH);
        img.setMimeType(MimeType.IMAGE_JPEG);
        InputData input = new InputData();
        input.setId(1);
        input.setImage(img);

        Analysis anal = new Analysis();
        anal.setId(1);
        anal.setAlgorithmVersion(TEST_ALGORITHM_VERSION);
        anal.setCreated(TEST_DATE);
        anal.setResultSet(rs);
        anal.setTrainDataVersion(TEST_TRAIN_DATA_VERSION);
        anal.setInputData(input);
        
        return anal;
    }
    
    public static AnalysisEntity generateAnalysisEntity() {
        ImageEntity uoImg1 = new ImageEntity();
        uoImg1.setLocalId(1);
        uoImg1.setHeight(10);
        uoImg1.setName(TEST_UO_IMG_NAME1);
        uoImg1.setPixelSize(TEST_PIXEL_SIZE);
        uoImg1.setSha256("aaaaaaaa");
        uoImg1.setWidth(TEST_IMG_WIDTH);
        uoImg1.setMimeType(MimeTypeEntity.IMAGE_JPEG);
        UnclassifiedObjectEntity uo1 = new UnclassifiedObjectEntity();
        uo1.setImage(uoImg1);
        
        ImageEntity uoImg2 = new ImageEntity();
        uoImg2.setLocalId(2);
        uoImg2.setHeight(10);
        uoImg2.setName(TEST_UO_IMG_NAME2);
        uoImg2.setPixelSize(TEST_PIXEL_SIZE);
        uoImg2.setSha256("aaaaaaaa");
        uoImg2.setWidth(TEST_IMG_WIDTH);
        uoImg2.setMimeType(MimeTypeEntity.IMAGE_JPEG);
        UnclassifiedObjectEntity uo2 = new UnclassifiedObjectEntity();
        uo2.setImage(uoImg2);
        
        ResultSetEntity rs = new ResultSetEntity();
        Set<UnclassifiedObjectEntity> uos = new HashSet<>();
        uos.add(uo1);
        uos.add(uo2);
        rs.setUnclassifiedObjects(uos);
        
        ImageEntity img = new ImageEntity();
        img.setLocalId(1);
        img.setHeight(10);
        img.setName(TEST_IMG_NAME);
        img.setPixelSize(TEST_PIXEL_SIZE);
        img.setSha256("aaaaaaaa");
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

}
