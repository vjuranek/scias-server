package eu.imagecode.scias.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.SampleEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.util.ModelMappers;

@Stateless
public class SampleService {
    
    @Inject
    private EntityManager em;
    
    /**
     * Loads all samples from DB.
     * 
     */
    public List<SampleEntity> getAllSamples() {
        return em.createNamedQuery("SampleEntity.findAll", SampleEntity.class).getResultList();
    }
    
    /**
     * Loads sample with specified global/server ID.
     * 
     */
    public SampleEntity getSampleById(int id) {
        return em.createNamedQuery("SampleEntity.findById", SampleEntity.class).setParameter("sampleId", id).getSingleResult();
    }
    
    /**
     * Loads sample with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public SampleEntity getSampleByLocalId(int localId, String stationUuid) {
        return em.createNamedQuery("SampleEntity.findByLocalIdAndStation", SampleEntity.class).setParameter("localId", localId).setParameter("stationUUID", stationUuid).getSingleResult();
    }
    
    /**
     * Load all samples which belong to batch with given ID.
     * 
     */
    public List<SampleEntity> getSamplesByBatchId(int batchId) {
        return em.createNamedQuery("SampleEntity.findByBatchId", SampleEntity.class).setParameter("batchId", batchId).getResultList();
    }
    
    /**
     * Uploads sample of samples into the database.
     * 
     */
    public SampleEntity uploadSample(Sample sample, BatchEntity batch, StationEntity stationEnt) {
        SampleEntity sampleEnt = null;
        try {
            sampleEnt = getSampleByLocalId(sample.getId(), stationEnt.getUuid());
            //add all analyses in the sample
            for (Analysis analysis : sample.getAnalysis()) {
                AnalysisEntity analysisEnt = ModelMappers.analysisToEntity(analysis, stationEnt);
                analysisEnt.setSample(sampleEnt);
                sampleEnt.getAnalyses().add(analysisEnt);
            }
        } catch (NoResultException e) {
            //sample doesn't exist yet - create it, included underlying structures like analysis
            sampleEnt = ModelMappers.sampleToEntity(sample, stationEnt);
            sampleEnt.setBatch(batch);
            em.persist(sampleEnt);
        }
        return sampleEnt;
    }

}
