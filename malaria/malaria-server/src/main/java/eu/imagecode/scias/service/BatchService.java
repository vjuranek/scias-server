package eu.imagecode.scias.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.rest.malaria.Batch;

/**
 * Service for manipulating with batches.
 * 
 * @author vjuranek
 *
 */
public interface BatchService {

    /**
     * Loads all batches from DB.
     * 
     */
    List<BatchEntity> getAllBatches();

    /**
     * Loads batch with specified global/server ID.
     * 
     */
    BatchEntity getBatchById(int id);

    /**
     * Loads batch with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    BatchEntity getBatchByLocalId(int localId, int stationId);

    /**
     * Loads batch with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    BatchEntity getBatchByLocalId(int localId, String stationUuid);

    /**
     * Checks, whether batch with given local/client ID was already uploaded from given station.
     * 
     */
    boolean isBatchUploaded(int batchId, int stationId);

    /**
     * Checks, whether batch with given local/client ID was already uploaded from given station.
     * 
     */
    boolean isBatchUploaded(int batchId, String stationUuid);

    /**
     * Uploads batch of samples into the database.
     * 
     */
    BatchEntity uploadBatch(Batch batch, Map<String, byte[]> imgMap, String stationUuid)
                    throws NoSuchAlgorithmException;

}