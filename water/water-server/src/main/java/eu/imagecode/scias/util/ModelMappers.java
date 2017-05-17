package eu.imagecode.scias.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.InputDataEntity;
import eu.imagecode.scias.model.jpa.MimeTypeEntity;
import eu.imagecode.scias.model.jpa.ResultEntity;
import eu.imagecode.scias.model.jpa.ResultSetEntity;
import eu.imagecode.scias.model.jpa.SciasUserEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.jpa.UnclassifiedObjectEntity;
import eu.imagecode.scias.model.rest.Analysis;
import eu.imagecode.scias.model.rest.Batch;
import eu.imagecode.scias.model.rest.Image;
import eu.imagecode.scias.model.rest.InputData;
import eu.imagecode.scias.model.rest.MimeType;
import eu.imagecode.scias.model.rest.Result;
import eu.imagecode.scias.model.rest.ResultSet;
import eu.imagecode.scias.model.rest.UnclassifiedObject;
import eu.imagecode.scias.model.rest.User;

public class ModelMappers {

    public static BatchEntity batchToEntity(Batch batch, StationEntity station) {
        BatchEntity ent = new BatchEntity();
        ent.setLocalId(batch.getId());
        ent.setStation(station);
        ent.setFinished(batch.isFinished());
        ent.setAnalyses(analysesListToEntities(batch.getAnalysis(), station));
        // TODO subject
        // analEnt.setSubject(anal.getSubject());
        return ent;
    }

    public static Batch entityToBatch(BatchEntity ent) {
        Batch batch = new Batch();
        batch.setId(ent.getId());
        batch.setFinished(ent.isFinished());
        List<Analysis> analyses = batch.getAnalysis();
        entsToAnalysesList(ent.getAnalyses()).forEach(analysis -> analyses.add(analysis));
        // TODO subject
        // batch.setSubject();
        return batch;
    }
    
    public static List<AnalysisEntity> analysesListToEntities(List<Analysis> analyses, StationEntity station) {
        return analyses.stream().map(analysis -> analysisToEntity(analysis, station)).collect(Collectors.toList());
    }

    public static List<Analysis> entsToAnalysesList(List<AnalysisEntity> ents) {
        return ents.stream().map(ent -> entityToAnalysis(ent)).collect(Collectors.toList());
    }
    
    public static AnalysisEntity analysisToEntity(Analysis anal, StationEntity station) {
        AnalysisEntity analEnt = new AnalysisEntity();
        analEnt.setLocalId(anal.getId());
        analEnt.setAlgorithmVersion(anal.getAlgorithmVersion());
        analEnt.setCreated(new Timestamp(anal.getCreated().getTime()));
        analEnt.setInputData(inputDataToEntity(anal.getInputData(), station)); // TODO switch to lazy loading
        analEnt.setResultSet(resultSetToEntity(anal.getResultSet(), station)); // TODO switch to lazy loading
        return analEnt;
    }

    public static Analysis entityToAnalysis(AnalysisEntity ent) {
        Analysis analysis = new Analysis();
        analysis.setId(ent.getId());
        analysis.setAlgorithmVersion(ent.getAlgorithmVersion());
        analysis.setCreated(ent.getCreated());
        analysis.setResultSet(entToResultSet(ent.getResultSet()));
        return analysis;
    }

    public static ResultSetEntity resultSetToEntity(ResultSet rs, StationEntity station) {
        ResultSetEntity rsEntList = new ResultSetEntity();
        rsEntList.setLocalId(rs.getId());
        rsEntList.setStation(station);
        rsEntList.setResults(resultListToEntities(rs.getResult(), station));
        rsEntList.setUnclassifiedObjects(unclassifiedObjectListToEntities(rs.getUnclassifiedObject(), station));
        return rsEntList;
    }

    public static ResultSet entToResultSet(ResultSetEntity rse) {
        ResultSet rs = new ResultSet();
        List<Result> resList = rs.getResult();
        List<UnclassifiedObject> uoList = rs.getUnclassifiedObject();
        rse.getResults().forEach(r -> resList.add(entToResult(r)));
        rse.getUnclassifiedObjects().forEach(u -> uoList.add(entToUnclassifiedObject(u)));
        return rs;
    }

    public static ResultEntity resultToEntity(Result res, StationEntity station) {
        ResultEntity resEnt = new ResultEntity();
        resEnt.setLocalId(res.getId());
        resEnt.setStation(station);
        resEnt.setAmount(res.getAmount());
        resEnt.setClassId(res.getIdClass());
        return resEnt;
    }

    public static Result entToResult(ResultEntity ent) {
        Result result = new Result();
        result.setAmount(ent.getAmount());
        result.setIdClass(ent.getClassId());
        return result;
    }

    public static Set<ResultEntity> resultListToEntities(List<Result> results, StationEntity station) {
        return results.stream().map(res -> resultToEntity(res, station)).collect(Collectors.toSet());
    }

    public static List<Result> entsToResultList(List<ResultEntity> ents) {
        return ents.stream().map(ent -> entToResult(ent)).collect(Collectors.toList());
    }

    public static UnclassifiedObjectEntity unclassifiedObjecToEntity(UnclassifiedObject uo, StationEntity station) {
        UnclassifiedObjectEntity uoEnt = new UnclassifiedObjectEntity();
        uoEnt.setLocalId(uo.getId());
        uoEnt.setStation(station);
        uoEnt.setImage(imageToEntity(uo.getImage()));
        uoEnt.setResolved(uo.isResolved());
        if (uo.getResolvedTime() != null) {
            uoEnt.setResolvedTime(new Timestamp(uo.getResolvedTime().getTime()));
        }
        if (uo.getResolvedBy() != null) {
            uoEnt.setResolvedBy(userToEntity(uo.getResolvedBy()));
        }
        return uoEnt;
    }

    public static UnclassifiedObject entToUnclassifiedObject(UnclassifiedObjectEntity ent) {
        UnclassifiedObject uco = new UnclassifiedObject();
        uco.setImage(entityToImage(ent.getImage()));
        uco.setResolved(ent.getResolved());
        if (ent.getResolvedTime() != null) {
            uco.setResolvedTime(ent.getResolvedTime());
        }
        if (ent.getResolvedBy() != null) {
            uco.setResolvedBy(entityToUser(ent.getResolvedBy()));
        }
        return uco;
    }

    public static Set<UnclassifiedObjectEntity> unclassifiedObjectListToEntities(List<UnclassifiedObject> uos, StationEntity station) {
        return uos.stream().map(uo -> unclassifiedObjecToEntity(uo, station)).collect(Collectors.toSet());
    }

    public static List<UnclassifiedObject> entstoUnclassifiedObjectList(List<UnclassifiedObjectEntity> ents) {
        return ents.stream().map(ent -> entToUnclassifiedObject(ent)).collect(Collectors.toList());
    }

    public static InputDataEntity inputDataToEntity(InputData inputData, StationEntity station) {
        InputDataEntity inputDataEnt = new InputDataEntity();
        inputDataEnt.setLocalId(inputData.getId());
        inputDataEnt.setStation(station);
        inputDataEnt.setImage(imageToEntity(inputData.getImage()));
        return inputDataEnt;
    }

    public static InputData entToInputData(InputDataEntity ent) {
        InputData inputData = new InputData();
        inputData.setImage(entityToImage(ent.getImage()));
        return inputData;
    }

    public static List<InputDataEntity> inputDataListToEntity(List<InputData> inputData, StationEntity station) {
        return inputData.stream().map(id -> inputDataToEntity(id, station)).collect(Collectors.toList());
    }

    public static List<InputData> entToInputDataList(List<InputDataEntity> ents) {
        return ents.stream().map(ent -> entToInputData(ent)).collect(Collectors.toList());
    }

    public static ImageEntity imageToEntity(Image img) {
        ImageEntity imgEnt = new ImageEntity();
        imgEnt.setLocalId(img.getId());
        imgEnt.setName(img.getName());
        imgEnt.setSha256(img.getSha256());
        imgEnt.setPixelSize(img.getPixelSize());
        imgEnt.setHeight(img.getHeight());
        imgEnt.setWidth(img.getWidth());
        imgEnt.setMimeType(mimeTypeToEntity(img.getMimeType()));
        return imgEnt;
    }

    public static Image entityToImage(ImageEntity ent) {
        Image image = new Image();
        image.setId(ent.getLocalId());
        image.setName(ent.getName());
        image.setSha256(ent.getSha256());
        image.setPixelSize(ent.getPixelSize());
        image.setHeight(ent.getHeight());
        image.setWidth(ent.getWidth());
        image.setMimeType(entityToMimeType(ent.getMimeType()));
        return image;
    }

    public static List<ImageEntity> imageListToEnt(List<Image> imgs) {
        return imgs.stream().map(img -> imageToEntity(img)).collect(Collectors.toList());
    }

    public static List<Image> entToImageList(List<ImageEntity> ents) {
        return ents.stream().map(ent -> entityToImage(ent)).collect(Collectors.toList());
    }

    public static SciasUserEntity userToEntity(User user) {
        SciasUserEntity userEnt = new SciasUserEntity();
        userEnt.setUsername(user.getName() + " " + user.getSurname());
        userEnt.setSciasRoles(null);
        return userEnt;
    }

    public static User entityToUser(SciasUserEntity ent) {
        User user = new User();
        user.setId(ent.getId());
        user.setName(ent.getUsername().split(" ")[0]);
        user.setSurname(ent.getUsername().split(" ")[1]);
        return user;
    }

    public static MimeTypeEntity mimeTypeToEntity(MimeType mimeType) {
        if (mimeType == null) {
            throw new IllegalStateException("Mime type cannot be null");
        }
        
        switch (mimeType) {
        case IMAGE_JPEG:
            return MimeTypeEntity.IMAGE_JPEG;
        case IMAGE_PNG:
            return MimeTypeEntity.IMAGE_PNG;
        case IMAGE_TIFF:
            return MimeTypeEntity.IMAGE_TIFF;
        default:
            throw new IllegalStateException("Unsupported mime type " + mimeType);
        }
    }

    public static MimeType entityToMimeType(MimeTypeEntity ent) {
        if (ent == null) {
            throw new IllegalStateException("Mime type cannot be null");
        }
        
        switch (ent) {
        case IMAGE_JPEG:
            return MimeType.IMAGE_JPEG;
        case IMAGE_PNG:
            return MimeType.IMAGE_PNG;
        case IMAGE_TIFF:
            return MimeType.IMAGE_TIFF;
        default:
            throw new IllegalStateException("Unsupported mime type " + ent);
        }
    }

}
