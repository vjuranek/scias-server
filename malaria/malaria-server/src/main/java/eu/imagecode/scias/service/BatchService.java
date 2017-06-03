package eu.imagecode.scias.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.SampleEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.util.ModelMappers;

@Stateless
public class BatchService {
    
    @Inject
    private EntityManager em;
    
    /**
     * Loads all batches from DB.
     * 
     */
    public List<BatchEntity> getAllBatches() {
        return em.createNamedQuery("BatchEntity.findAll", BatchEntity.class).getResultList();
    }
    
    /**
     * Loads batch with specified global/server ID.
     * 
     */
    public BatchEntity getBatchById(int id) {
        return em.createNamedQuery("BatchEntity.findById", BatchEntity.class).setParameter("batchId", id).getSingleResult();
    }
    
    /**
     * Loads batch with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public BatchEntity getBatchByLocalId(int localId, String stationUuid) {
        return em.createNamedQuery("BatchEntity.findByLocalIdAndStation", BatchEntity.class).setParameter("localId", localId).setParameter("stationUUID", stationUuid).getSingleResult();
    }
    
    /**
     * Uploads batch of samples into the database.
     * 
     */
    public BatchEntity uploadBatch(Batch batch, String stationUuid) {
        BatchEntity batchEnt = null;
        try {
            batchEnt = getBatchByLocalId(batch.getId(), stationUuid);
        } catch (NoResultException e) {
            batchEnt = ModelMappers.batchToEntity(batch, getStationByUuid(stationUuid));
            em.persist(batchEnt);
            em.flush();
        }
        int sampleId = batch.getSample().get(0).getId();
        
        //TODO !!!! just a test
        SampleEntity sampleEnt = batchEnt.getSamples().get(0);
        sampleEnt.getAnalyses().add(ModelMappers.analysisToEntity(batch.getSample().get(0).getAnalysis().get(0), getStationByUuid(stationUuid)));
        
        em.persist(batchEnt);
        return batchEnt;
    }
    
    /**
     * Puts all images related/contained in given {@link BatchEntity} to a {@link List}.  
     * 
     * @param analysis {@link BatchEntity} from which images should be extracted. 
     * @return {@link List} of {@link ImageEntity}s related to given batch. 
     */
    public List<Image> extractImages(Batch batch, String stationUuid) {
        List<Analysis> analyses = new ArrayList<>();
        batch.getSample().forEach(sample -> analyses.addAll(sample.getAnalysis()));
        List<Image> imgs = new LinkedList<Image>();
        analyses.forEach(analysis -> { 
            imgs.add(analysis.getInputData().getImage());
            analysis.getResultSet().getUnclassifiedObject().forEach(uo -> imgs.add(uo.getImage()));
        });
        return imgs;
    }
    
    private StationEntity getStationByUuid(String stationUuid) {
        return em.createNamedQuery("StationEntity.findByUuid", StationEntity.class).setParameter("stationUuid", stationUuid).getSingleResult();
    }

}
