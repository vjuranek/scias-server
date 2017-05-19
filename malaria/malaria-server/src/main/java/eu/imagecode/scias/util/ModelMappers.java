package eu.imagecode.scias.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.InputDataEntity;
import eu.imagecode.scias.model.jpa.LocalityEntity;
import eu.imagecode.scias.model.jpa.MimeTypeEntity;
import eu.imagecode.scias.model.jpa.PatientEntity;
import eu.imagecode.scias.model.jpa.ResultEntity;
import eu.imagecode.scias.model.jpa.ResultSetEntity;
import eu.imagecode.scias.model.jpa.SampleEntity;
import eu.imagecode.scias.model.jpa.SciasUserEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.jpa.UnclassifiedObjectEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.model.rest.malaria.InputData;
import eu.imagecode.scias.model.rest.malaria.Locality;
import eu.imagecode.scias.model.rest.malaria.MimeType;
import eu.imagecode.scias.model.rest.malaria.Patient;
import eu.imagecode.scias.model.rest.malaria.Result;
import eu.imagecode.scias.model.rest.malaria.ResultSet;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.model.rest.malaria.UnclassifiedObject;
import eu.imagecode.scias.model.rest.malaria.User;

public class ModelMappers {

    public static BatchEntity batchToEntity(Batch batch, StationEntity station) {
        BatchEntity ent = new BatchEntity();
        ent.setLocalId(batch.getId());
        ent.setFinished(batch.isFinished());
        ent.setPatient(patientToEntity(batch.getPatient(), station));
        ent.setSamples(samplesListToEntities(batch.getSample(), station));
        ent.setStation(station);
        return ent;
    }

    public static Batch entityToBatch(BatchEntity ent) {
        Batch batch = new Batch();
        batch.setId(ent.getLocalId());
        batch.setFinished(ent.isFinished());
        batch.setPatient(entityToPatient(ent.getPatient()));
        List<Sample> samples = batch.getSample();
        entsToSamplesList(ent.getSamples()).forEach(sample -> samples.add(sample));;
        return batch;
    }
    
    public static List<SampleEntity> samplesListToEntities(List<Sample> samples, StationEntity station) {
        return samples.stream().map(sample -> sampleToEntity(sample, station)).collect(Collectors.toList());
    }

    public static List<Sample> entsToSamplesList(List<SampleEntity> ents) {
        return ents.stream().map(ent -> entityToSample(ent)).collect(Collectors.toList());
    }
    
    public static SampleEntity sampleToEntity(Sample sample, StationEntity station) {
        SampleEntity ent = new SampleEntity();
        ent.setLocalId(sample.getId());
        ent.setFinished(sample.isFinished());
        ent.setLocality(localityToEntity(sample.getLocality()));
        ent.setAnalyses(analysesListToEntities(sample.getAnalysis(), station));
        return ent;
    }

    public static Sample entityToSample(SampleEntity ent) {
        Sample sample = new Sample();
        sample.setId(ent.getLocalId());
        sample.setFinished(ent.isFinished());
        sample.setLocality(entityToLocality(ent.getLocality()));
        List<Analysis> analyses = sample.getAnalysis();
        entsToAnalysesList(ent.getAnalyses()).forEach(analysis -> analyses.add(analysis));
        return sample;
    }
    
    public static PatientEntity patientToEntity(Patient patient, StationEntity station) {
        PatientEntity ent = new PatientEntity();
        ent.setLocalId(patient.getId());
        ent.setDayOfBirth(patient.getDateOfBirth());
        ent.setFirstName(patient.getFirstName());
        ent.setLastName(patient.getLastName());
        ent.setStation(station);
        return ent;
    }
    
    public static Patient entityToPatient(PatientEntity ent) {
        Patient patient = new Patient();
        patient.setId(ent.getLocalId());
        patient.setDateOfBirth(ent.getDayOfBirth());
        patient.setFirstName(ent.getFirstName());
        patient.setLastName(ent.getLastName());
        patient.setMiddleName(ent.getMiddleName());
        return patient;
    }
    
    public static LocalityEntity localityToEntity(Locality locality) {
        LocalityEntity ent = new LocalityEntity();
        ent.setLocalId(locality.getId());
        ent.setAltitude(locality.getAltitude());
        ent.setLatitude(locality.getLatitude());
        ent.setLongtitude(locality.getLongtitude());
        return ent;
    }
    
    public static Locality entityToLocality(LocalityEntity ent) {
        Locality locality = new Locality();
        locality.setId(ent.getLocalId());
        locality.setAltitude(ent.getAltitude());
        locality.setLatitude(ent.getLatitude());
        locality.setLongtitude(ent.getLongtitude());
        return locality;
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
        analEnt.setStation(station);
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
        rsEntList.setResults(resultListToEntities(rs.getResult(), station));
        rsEntList.setUnclassifiedObjects(unclassifiedObjectListToEntities(rs.getUnclassifiedObject(), station));
        rsEntList.setStation(station);
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

    public static ResultEntity resultToEntity(Result res) {
        ResultEntity resEnt = new ResultEntity();
        resEnt.setLocalId(res.getId());
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
        return results.stream().map(res -> resultToEntity(res)).collect(Collectors.toSet());
    }

    public static List<Result> entsToResultList(List<ResultEntity> ents) {
        return ents.stream().map(ent -> entToResult(ent)).collect(Collectors.toList());
    }

    public static UnclassifiedObjectEntity unclassifiedObjecToEntity(UnclassifiedObject uo, StationEntity station) {
        UnclassifiedObjectEntity uoEnt = new UnclassifiedObjectEntity();
        uoEnt.setLocalId(uo.getId());
        uoEnt.setImage(imageToEntity(uo.getImage()));
        uoEnt.setStation(station);
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
        inputDataEnt.setImage(imageToEntity(inputData.getImage()));
        inputDataEnt.setStation(station);
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
        userEnt.setUserRoles(null);
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
