package eu.imagecode.scias.service;

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
import eu.imagecode.scias.util.Functions;
import eu.imagecode.scias.util.ModelMappers;

/**
 * Service for manipulation with samples.
 * 
 * @author vjuranek
 *
 */
@Stateless
public class SampleService {

    @Inject
    private EntityManager em;

    @Inject
    private AnalysisService analysisSrv;

    @Inject
    private Logger log;

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
        return em.createNamedQuery("SampleEntity.findById", SampleEntity.class).setParameter("sampleId", id)
                        .getSingleResult();
    }

    /**
     * Loads sample with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public SampleEntity getSampleByLocalId(int localId, int stationId) {
        return em.createNamedQuery("SampleEntity.findByLocalIdAndStation", SampleEntity.class)
                        .setParameter("localId", localId).setParameter("stationID", stationId).getSingleResult();
    }

    /**
     * Loads sample with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public SampleEntity getSampleByLocalId(int localId, String stationUuid) {
        return em.createNamedQuery("SampleEntity.findByLocalIdAndStationUuid", SampleEntity.class)
                        .setParameter("localId", localId).setParameter("stationUUID", stationUuid).getSingleResult();
    }

    /**
     * Load all samples which belong to batch with given ID.
     * 
     */
    public List<SampleEntity> getSamplesByBatchId(int batchId) {
        return em.createNamedQuery("SampleEntity.findByBatchId", SampleEntity.class).setParameter("batchId", batchId)
                        .getResultList();
    }

    /**
     * Checks, whether sample with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isSampleUploaded(int sampleId, int stationId) {
        try {
            em.createNamedQuery("SampleEntity.findByLocalIdAndStation", SampleEntity.class)
                            .setParameter("localId", sampleId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

    /**
     * Checks, whether sample with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isSampleUploaded(int sampleId, String stationUuid) {
        try {
            em.createNamedQuery("SampleEntity.findByLocalIdAndStationUuid", SampleEntity.class)
                            .setParameter("localId", sampleId).setParameter("stationUUID", stationUuid)
                            .getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

    /**
     * Uploads sample of samples into the database.
     * 
     */
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
