package eu.imagecode.scias.service;

import java.util.List;
import java.util.Map;

import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.SampleEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Sample;

/**
 * Service for manipulation with samples.
 * 
 * @author vjuranek
 *
 */
public interface SampleService {

    /**
     * Loads all samples from DB.
     * 
     */
    List<SampleEntity> getAllSamples();

    /**
     * Loads sample with specified global/server ID.
     * 
     */
    SampleEntity getSampleById(int id);

    /**
     * Loads sample with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    SampleEntity getSampleByLocalId(int localId, int stationId);

    /**
     * Loads sample with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    SampleEntity getSampleByLocalId(int localId, String stationUuid);

    /**
     * Load all samples which belong to batch with given ID.
     * 
     */
    List<SampleEntity> getSamplesByBatchId(int batchId);

    /**
     * Checks, whether sample with given local/client ID was already uploaded from given station.
     * 
     */
    boolean isSampleUploaded(int sampleId, int stationId);

    /**
     * Checks, whether sample with given local/client ID was already uploaded from given station.
     * 
     */
    boolean isSampleUploaded(int sampleId, String stationUuid);

    /**
     * Uploads sample of samples into the database.
     * 
     */
    SampleEntity uploadSample(Sample sample, BatchEntity batch, StationEntity stationEnt, Map<String, byte[]> imgMap);

}