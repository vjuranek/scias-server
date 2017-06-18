package eu.imagecode.scias.service;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.PatientEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.util.Functions;
import eu.imagecode.scias.util.ModelMappers;

@Stateless
public class BatchService {

    @Inject
    private EntityManager em;

    @Inject
    private SampleService sampleSrv;

    @Inject
    private PatientService patientSrv;

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
        return em.createNamedQuery("BatchEntity.findById", BatchEntity.class).setParameter("batchId", id)
                        .getSingleResult();
    }

    /**
     * Loads batch with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public BatchEntity getBatchByLocalId(int localId, String stationUuid) {
        try {
            return em.createNamedQuery("BatchEntity.findByLocalIdAndStation", BatchEntity.class)
                            .setParameter("localId", localId).setParameter("stationUUID", stationUuid)
                            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Uploads batch of samples into the database.
     * 
     */
    public BatchEntity uploadBatch(Batch batch, Map<String, byte[]> imgMap, String stationUuid) {
        StationEntity stationEnt = getStationByUuid(stationUuid);
        BatchEntity batchEnt = getBatchByLocalId(batch.getId(), stationEnt.getUuid());
        if (batchEnt != null) {
            // add all analyses in the sample
            for (Sample sample : batch.getSample()) {
                sampleSrv.uploadSample(sample, batchEnt, stationEnt, imgMap);
            }
        } else {
            // batch doesn't exist yet - create it, included underlying structures like samples
            batchEnt = ModelMappers.batchToEntity(batch, stationEnt);
            // populate images with img content
            batchEnt.getSamples().forEach(sampleEnt -> sampleEnt.getAnalyses()
                            .forEach(analysisEnt -> Functions.populateImages(analysisEnt, imgMap)));
            PatientEntity patient = patientSrv.getPatientByLocalId(batch.getPatient().getId(), stationUuid);
            if (patient != null) {
                batchEnt.setPatient(patient);
            }
            em.persist(batchEnt);
        }
        return batchEnt;
    }

    private StationEntity getStationByUuid(String stationUuid) {
        return em.createNamedQuery("StationEntity.findByUuid", StationEntity.class)
                        .setParameter("stationUuid", stationUuid).getSingleResult();
    }

}
