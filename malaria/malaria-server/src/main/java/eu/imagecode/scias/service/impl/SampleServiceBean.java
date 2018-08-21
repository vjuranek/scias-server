package eu.imagecode.scias.service.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.SampleEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.SampleService;
import eu.imagecode.scias.util.Functions;
import eu.imagecode.scias.util.ModelMappers;

/**
 * Implementation of {@link SampleService}.
 * 
 * @author vjuranek
 *
 */
@Stateless
public class SampleServiceBean implements SampleService {

    @Inject
    private EntityManager em;

    @Inject
    private AnalysisService analysisSrv;

    @Inject
    private Logger log;

    @Override
    public List<SampleEntity> getAllSamples() {
        return em.createNamedQuery(SampleEntity.QUERY_FIND_ALL, SampleEntity.class).getResultList();
    }

    @Override
    public SampleEntity getSampleById(int id) {
        return em.createNamedQuery(SampleEntity.QUERY_FIND_BY_ID, SampleEntity.class).setParameter("sampleId", id)
                        .getSingleResult();
    }

    @Override
    public SampleEntity getSampleByLocalId(int localId, int stationId) {
        return em.createNamedQuery(SampleEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, SampleEntity.class)
                        .setParameter("localId", localId).setParameter("stationID", stationId).getSingleResult();
    }

    @Override
    public SampleEntity getSampleByLocalId(int localId, String stationUuid) {
        return em.createNamedQuery(SampleEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, SampleEntity.class)
                        .setParameter("localId", localId).setParameter("stationUUID", stationUuid).getSingleResult();
    }

    @Override
    public List<SampleEntity> getSamplesByBatchId(int batchId) {
        return em.createNamedQuery(SampleEntity.QUERY_FIND_BY_BATCH_ID, SampleEntity.class)
                        .setParameter("batchId", batchId).getResultList();
    }

    @Override
    public boolean isSampleUploaded(int sampleId, int stationId) {
        try {
            em.createNamedQuery(SampleEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, SampleEntity.class)
                            .setParameter("localId", sampleId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

    @Override
    public boolean isSampleUploaded(int sampleId, String stationUuid) {
        try {
            em.createNamedQuery(SampleEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, SampleEntity.class)
                            .setParameter("localId", sampleId).setParameter("stationUUID", stationUuid)
                            .getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

    @Override
    public SampleEntity uploadSample(Sample sample, BatchEntity batch, StationEntity stationEnt,
                    Map<String, byte[]> imgMap) {
        SampleEntity sampleEnt = null;
        try {
            sampleEnt = getSampleByLocalId(sample.getId(), stationEnt.getId());
            for (Analysis analysis : sample.getAnalysis()) {
                if (!analysisSrv.isAnalysisUploaded(analysis.getId(), stationEnt.getId())) {
                    AnalysisEntity analysisEnt = ModelMappers.analysisToEntity(analysis, stationEnt);
                    Functions.populateImages(analysisEnt, imgMap); // populate images with img content
                    analysisEnt.setSample(sampleEnt);
                    sampleEnt.getAnalyses().add(analysisEnt);
                    log.fine(String.format(
                                    "[Sample %d]: Analysis with local ID %d from station %s uploaded into database",
                                    sampleEnt.getId(), analysis.getId(), stationEnt.getUuid()));
                } else {
                    log.fine(String.format(
                                    "[Sample %d]: Analysis with local ID %d from station %s already contained in DB, skipping upload.",
                                    sampleEnt.getId(), analysis.getId(), stationEnt.getUuid()));
                }
            }
        } catch (NoResultException e) {
            log.fine(String.format("Sample with local ID %d from station %s doesn't exist in DB, creating...",
                            sample.getId(), stationEnt.getUuid()));
            // sample doesn't exist yet - create it, included underlying structures like analysis
            sampleEnt = ModelMappers.sampleToEntity(sample, stationEnt);
            // populate images with img content
            sampleEnt.getAnalyses().forEach(analysisEnt -> Functions.populateImages(analysisEnt, imgMap));
            sampleEnt.setBatch(batch);
            em.persist(sampleEnt);
        }
        return sampleEnt;
    }

}
