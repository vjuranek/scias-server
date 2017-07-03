package eu.imagecode.scias.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import eu.imagecode.scias.model.jpa.AnalysisEntity;

@Stateless
public class AnalysisService {

    @Inject
    private EntityManager em;

    /**
     * Loads all analyses from DB 
     *
     */
    public List<AnalysisEntity> getAllAnalyses() {
        return em.createNamedQuery("AnalysisEntity.findAll", AnalysisEntity.class).getResultList();
    }

    /**
     * Loads analysis with given global/server ID 
     *
     */
    public AnalysisEntity getAnalysisById(int id) {
        return em.createNamedQuery("AnalysisEntity.findById", AnalysisEntity.class).setParameter("analysisId", id).getSingleResult();
    }
    
    /**
     * Loads all analyses related to given batch ID
     * 
     */
    public List<AnalysisEntity> getAnalysisByBatchId(int batchId) {
        try {
            return em.createNamedQuery("AnalysisEntity.findByBatchId", AnalysisEntity.class).setParameter("batchId", batchId).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<AnalysisEntity>();
        }
    }
    
    /**
     * Loads analysis with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public AnalysisEntity getAnalysisByLocalId(int analysisId, int stationId) {
        return em.createNamedQuery("AnalysisEntity.findByLocalIdAndStation", AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationID", stationId).getSingleResult();
    }
    
    /**
     * Loads analysis with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public AnalysisEntity getAnalysisByLocalId(int analysisId, String stationUuid) {
        return em.createNamedQuery("AnalysisEntity.findByLocalIdAndStationUuid", AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationUUID", stationUuid).getSingleResult();
    }
    
    
    /**
     * Checks, whether analysis with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isAnalysisUploaded(int analysisId, int stationId) {
        try {
            em.createNamedQuery("AnalysisEntity.findByLocalIdAndStation", AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }
    
    
    /**
     * Checks, whether analysis with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isAnalysisUploaded(int analysisId, String stationUuid) {
        try {
            em.createNamedQuery("AnalysisEntity.findByLocalIdAndStationUuid", AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationUUID", stationUuid).getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

}
