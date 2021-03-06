package eu.imagecode.scias.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.service.AnalysisService;

/**
 * Implementation of {@link AnalysisService}
 * 
 * @author vjuranek
 *
 */
@Dependent
@Stateless
public class AnalysisServiceBean implements AnalysisService {

    @Inject
    private EntityManager em;

    @Override
    public List<AnalysisEntity> getAllAnalyses() {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_ALL, AnalysisEntity.class).getResultList();
    }

    @Override
    public AnalysisEntity getAnalysisById(int id) {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_ID, AnalysisEntity.class).setParameter("analysisId", id)
                        .getSingleResult();
    }

    @Override
    public List<AnalysisEntity> getAnalysisByBatchId(int batchId) {
        try {
            return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_BATCH_ID, AnalysisEntity.class)
                            .setParameter("batchId", batchId).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<AnalysisEntity>();
        }
    }

    @Override
    public AnalysisEntity getAnalysisByLocalId(int analysisId, int stationId) {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, AnalysisEntity.class)
                        .setParameter("localId", analysisId).setParameter("stationID", stationId).getSingleResult();
    }

    @Override
    public AnalysisEntity getAnalysisByLocalId(int analysisId, String stationUuid) {
        return em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, AnalysisEntity.class)
                        .setParameter("localId", analysisId).setParameter("stationUUID", stationUuid).getSingleResult();
    }

    @Override
    public List<AnalysisEntity> getAnalysisInTimeRange(Timestamp from, Timestamp to) {
        try {
            return em.createNamedQuery(AnalysisEntity.QUERY_FIND_IN_TIME_RANGE, AnalysisEntity.class)
                            .setParameter("from", from).setParameter("to", to).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<AnalysisEntity>();
        }
    }

    @Override
    public boolean isAnalysisUploaded(int analysisId, int stationId) {
        try {
            em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, AnalysisEntity.class)
                            .setParameter("localId", analysisId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

    @Override
    public boolean isAnalysisUploaded(int analysisId, String stationUuid) {
        try {
            em.createNamedQuery(AnalysisEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, AnalysisEntity.class)
                            .setParameter("localId", analysisId).setParameter("stationUUID", stationUuid)
                            .getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

}
