package eu.imagecode.scias.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Sample;

/**
 * Service which does all the validation, like check, if it's eligible to upload given batch/sample/analysis, batch
 * request is consistent (number of images matches), etc.
 * 
 * @author vjuranek
 *
 */
public interface ValidationService {

    /**
     * Wraps all checks of incoming {@link Batch} upload request
     * 
     * @param batch
     *            Batch to be uploaded
     * @param imgMap
     *            Map of actual images to be uploaded
     * @param stationId
     *            ID of the station from which the results are comming
     * @throws IllegalArgumentException
     *             when there is found any problem with incoming request
     */
    void checkBatchUploadRequest(Batch batch, Map<String, byte[]> imgMap, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException;

    /**
     * Wraps all checks for {@link Batch} which doesn't exist in DB and will be created.
     * 
     * @param samples
     *            {@link List} of {@link Sample}s contained in the {@link Batch}
     * @param stationId
     *            ID of the station which sent the request
     * @throws IllegalArgumentException
     * @throws NoSuchAlgorithmException
     */
    void checkNewBatchUploadRequest(List<Sample> samples, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException;

    /**
     * Wraps all {@link Analysis} checks
     * 
     * @param analysis
     *            {@link Analysis} to be checked
     * @param imgMap
     *            {@link Map} of the images associated with the upload request
     * @throws IllegalArgumentException
     * @throws NoSuchAlgorithmException
     */
    void checkAnalysis(Analysis analysis, Map<String, byte[]> imgMap, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException;

    /**
     * Check that number of images referenced in the batch is the same as number of actually uploaded images
     * 
     * @param batch
     *            {@link Batch} to be checked
     * @param imgSizeActual
     *            Number of images actually uploaded in batch upload HTTP POST request
     * 
     * @throws IllegalArgumentException
     *             when the number of images referenced in analysis and actual number of uploaded images are different
     */
    void checkNumberOfImages(Batch batch, int imgSizeActual) throws IllegalArgumentException;

    /**
     * Check images to be uploaded. Checks if the image is present and it's sha-256 is same as on client
     * 
     * @param ae
     *            {@link Analysis} containing images
     * @param imgMap
     *            Map with actual images to be uploaded
     * @throws IllegalArgumentException
     *             when image is not present, sha-256 is not filled by client in the request or sha-256 doesn't match
     * @throws NoSuchAlgorithmException
     *             when sha-256 algorithm is not available
     */
    void checkAnalysisImages(Analysis ae, Map<String, byte[]> imgMap)
                    throws IllegalArgumentException, NoSuchAlgorithmException;

    /**
     * Check if the {@link Batch} is already uploaded on the server
     * 
     * @param batchId
     *            {@link Batch} local ID of the batch to be uploaded/checked
     * @param stationId
     *            ID of the station which sent {@link Batch} upload request
     * @throws IllegalArgumentException
     *             when batch is already present in server DB
     */
    void checkBatchUnique(int batchId, int stationId) throws IllegalArgumentException;

    /**
     * Check if each of the {@link Sample}s in thr batch is not uploaded on the server
     * 
     * @param samples
     *            {@link List} of the {@link Sample}s contained in the {@link Batch} to be checked that are unique.
     * @param stationId
     *            ID of the station which sent {@link Batch} upload request
     * @throws IllegalArgumentException
     *             when sample is already present in server DB
     */
    void checkSamplesUnique(List<Sample> samples, int stationId) throws IllegalArgumentException;

    /**
     * Check if the {@link Sample} is already uploaded in server
     * 
     * @param sampleId
     *            Local ID of the {@link Sample} to be checked if already present in DB
     * @param stationId
     *            ID of the station which sent {@link Batch} upload request
     * @throws IllegalArgumentException
     *             when sample is already present on the server DB
     */
    void checkSampleUnique(int sampleId, int stationId) throws IllegalArgumentException;

    /**
     * Check if the analysis is already uploaded in server
     * 
     * @param analysisId
     *            Local ID of the {@link Analysis} to be checked if already present in DB
     * @param stationId
     *            ID of the station which sent {@link Batch} upload request
     * @throws IllegalArgumentException
     *             when analysis is already present in server DB
     */
    void checkAnalysisUnique(int analysisId, int stationId) throws IllegalArgumentException;

}