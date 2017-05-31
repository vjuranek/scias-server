package eu.imagecode.scias.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.util.ModelMappers;

@Stateless
public class BatchService {
    
    @Inject
    private EntityManager em;
    
    public List<BatchEntity> getAllBatches() {
        return em.createNamedQuery("BatchEntity.findAll", BatchEntity.class).getResultList();
    }
    
    public BatchEntity getBatchById(int id) {
        return em.createNamedQuery("BatchEntity.findById", BatchEntity.class).setParameter("batchId", id).getSingleResult();
    }
    
    public BatchEntity getBatchByLocalId(int localId, String stationUuid) {
        return em.createNamedQuery("BatchEntity.findByLocalIdAndStation", BatchEntity.class).setParameter("localId", localId).setParameter("stationUUID", stationUuid).getSingleResult();
    }
    
    /**
     * Uploads batch of samples into the database.
     * 
     */
    public BatchEntity uploadBatch(Batch batch, String stationUuid) {
        BatchEntity batchEnt = ModelMappers.batchToEntity(batch, getStationByUuid(stationUuid));
        em.persist(batchEnt);
        return batchEnt;
    }
    
    /**
     * Puts all images related/contained in given {@link BatchEntity} to a {@link List}.  
     * 
     * @param analysis {@link BatchEntity} from which images should be extracted. 
     * @return {@link List} of {@link ImageEntity}s related to given batch. 
     */
    public List<ImageEntity> extractImages(BatchEntity batch, String stationUuid) {
        List<AnalysisEntity> analyses = new ArrayList<>();
        batch.getSamples().forEach(sample -> analyses.addAll(sample.getAnalyses()));
        List<ImageEntity> imgs = new LinkedList<ImageEntity>();
        analyses.forEach(analysis -> { 
            imgs.add(analysis.getInputData().getImage());
            analysis.getResultSet().getUnclassifiedObjects().forEach(uo -> imgs.add(uo.getImage()));
        });
        return imgs;
    }
    
    private StationEntity getStationByUuid(String stationUuid) {
        return em.createNamedQuery("StationEntity.findByUuid", StationEntity.class).setParameter("stationUuid", stationUuid).getSingleResult();
    }

}
