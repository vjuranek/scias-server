package eu.imagecode.scias.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.service.AnalysisService;

/**
 * Service for manipulating with analyses.
 * 
 * @author vjuranek
 *
 */
@Dependent
public class AnalysisServiceImpl implements AnalysisService {

    @Inject
    private EntityManager em;

    /**
     * Loads all analyses from DB 
     *
     */
    public List<AnalysisEntity> getAllAnalyses() {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_ALL, AnalysisEntity.class).getResultList();
    }

    /**
     * Loads analysis with given global/server ID 
     *
     */
    public AnalysisEntity getAnalysisById(int id) {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_ID, AnalysisEntity.class).setParameter("analysisId", id).getSingleResult();
    }
    
    /**
     * Loads all analyses related to given batch ID
     * 
     */
    public List<AnalysisEntity> getAnalysisByBatchId(int batchId) {
        try {
            return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_BATCH_ID, AnalysisEntity.class).setParameter("batchId", batchId).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<AnalysisEntity>();
        }
    }
    
    /**
     * Loads analysis with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public AnalysisEntity getAnalysisByLocalId(int analysisId, int stationId) {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationID", stationId).getSingleResult();
    }
    
    /**
     * Loads analysis with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public AnalysisEntity getAnalysisByLocalId(int analysisId, String stationUuid) {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationUUID", stationUuid).getSingleResult();
    }
    
    /**
     * Loads all analyses in specified time range.
     * 
     */
    public List<AnalysisEntity> getAnalysisInTimeRange(Timestamp from, Timestamp to) {
        try {
            return em.createNamedQuery(AnalysisEntity.QUERY_FIND_IN_TIME_RANGE, AnalysisEntity.class).setParameter("from", from).setParameter("to", to).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<AnalysisEntity>();
        }
    }
    
    
    /**
     * Checks, whether analysis with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isAnalysisUploaded(int analysisId, int stationId) {
        try {
            em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationID", stationId).getSingleResult();
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
            em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationUUID", stationUuid).getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

}
