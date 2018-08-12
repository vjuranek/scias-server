package eu.imagecode.scias.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.PatientEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.service.PatientService;
import eu.imagecode.scias.service.SampleService;
import eu.imagecode.scias.service.ValidationService;
import eu.imagecode.scias.util.Functions;
import eu.imagecode.scias.util.ModelMappers;

/**
 * Implementation of {@link BatchService}
 * 
 * @author vjuranek
 *
 */
@Stateless
public class BatchServiceImpl implements BatchService {

    @Inject
    private EntityManager em;

    @Inject
    private SampleService sampleSrv;

    @Inject
    private PatientService patientSrv;

    @Inject
    private ValidationService validationSrv;

    @Inject
    private Logger log;

    @Override
    public List<BatchEntity> getAllBatches() {
        return em.createNamedQuery(BatchEntity.QUERY_FIND_ALL, BatchEntity.class).getResultList();
    }

    @Override
    public BatchEntity getBatchById(int id) {
        return em.createNamedQuery(BatchEntity.QUERY_FIND_BY_ID, BatchEntity.class).setParameter("batchId", id)
                        .getSingleResult();
    }

    @Override
    public BatchEntity getBatchByLocalId(int localId, int stationId) {
        try {
            return em.createNamedQuery(BatchEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, BatchEntity.class)
                            .setParameter("localId", localId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public BatchEntity getBatchByLocalId(int localId, String stationUuid) {
        try {
            return em.createNamedQuery(BatchEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, BatchEntity.class)
                            .setParameter("localId", localId).setParameter("stationUUID", stationUuid)
                            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean isBatchUploaded(int batchId, int stationId) {
        try {
            em.createNamedQuery(BatchEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, BatchEntity.class)
                            .setParameter("localId", batchId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

    @Override
    public boolean isBatchUploaded(int batchId, String stationUuid) {
        try {
            em.createNamedQuery(BatchEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION_UUID, BatchEntity.class)
                            .setParameter("localId", batchId).setParameter("stationUUID", stationUuid)
                            .getSingleResult();
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            return true;
        }
        return true;
    }

    @Override
    public BatchEntity uploadBatch(Batch batch, Map<String, byte[]> imgMap, String stationUuid)
                    throws NoSuchAlgorithmException {
        StationEntity stationEnt = getStationByUuid(stationUuid);

        // do some check before actual upload
        validationSrv.checkBatchUploadRequest(batch, imgMap, stationEnt.getId());

        BatchEntity batchEnt = getBatchByLocalId(batch.getId(), stationEnt.getId());
        if (batchEnt != null) {
            // add all analyses in the sample
            for (Sample sample : batch.getSample()) {
                sampleSrv.uploadSample(sample, batchEnt, stationEnt, imgMap);
            }
        } else {
            // validate batch first
            validationSrv.checkNewBatchUploadRequest(batch.getSample(), stationEnt.getId());

            // batch doesn't exist yet - create it, included underlying structures like samples
            log.fine(String.format("Batch with local ID %d from station %s doesn't exists in DB, creating ...",
                            batch.getId(), stationUuid));
            batchEnt = ModelMappers.batchToEntity(batch, stationEnt);
            // populate images with img content
            batchEnt.getSamples().forEach(sampleEnt -> sampleEnt.getAnalyses()
                            .forEach(analysisEnt -> Functions.populateImages(analysisEnt, imgMap)));
            PatientEntity patient = patientSrv.getPatientByLocalId(batch.getPatient().getId(), stationEnt.getId());
            if (patient != null) {
                batchEnt.setPatient(patient);
            } // TODO can patient be null/not set? If not, throw exception
            em.persist(batchEnt);
        }
        return batchEnt;
    }

    private StationEntity getStationByUuid(String stationUuid) throws IllegalArgumentException {
        try {
            return em.createNamedQuery(StationEntity.QUERY_FIND_BY_UUID, StationEntity.class)
                            .setParameter("stationUuid", stationUuid).getSingleResult();
        } catch (NoResultException e) {
            log.info(String.format("Station with UUID '%s' doesn't exist in database!", stationUuid));
            throw new IllegalArgumentException(
                            String.format("Station with UUID '%s' doesn't exist in database!", stationUuid));
        }
    }

}
