package eu.imagecode.scias.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;

import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.util.Functions;
import eu.imagecode.scias.util.SciasFunctions;

@Stateless
public class ValidationService {

    @Inject
    BatchService batchSrv;
    
    @Inject
    SampleService sampleSrv;
    
    @Inject
    AnalysisService analysisSrv;

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
    public void checkBatchUploadRequest(Batch batch, Map<String, byte[]> imgMap, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException {

        checkNumberOfImages(batch, imgMap.size()); // check number of images first as it doesn't require any DB query
        checkBatchUnique(batch.getId(), stationId);
        
        for (Analysis ae : Functions.analysesFromBatch(batch)) {
            checkAnalysis(ae, imgMap, stationId);
        }

    }
    
    public void checkNewBatchUploadRequest(List<Sample> samples, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException {

        checkSamplesUnique(samples, stationId);

    }

    /**
     * Wraps all {@link Analysis} checks
     * 
     * @param analysis
     * @param imgMap
     * @throws IllegalArgumentException
     * @throws NoSuchAlgorithmException
     */
    public void checkAnalysis(Analysis analysis, Map<String, byte[]> imgMap, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException {

        checkAnalysisImages(analysis, imgMap);
        checkAnalysisUnique(analysis.getId(), stationId);

    }

    /**
     * Check that number of images referenced in the batch is the same as number of actually uploaded images
     * 
     * @param batch
     * @param actual
     * 
     * @throws IllegalArgumentException
     *             when the number of images referenced in analysis and actual number of uploaded images are different
     */
    public void checkNumberOfImages(Batch batch, int actual) throws IllegalArgumentException {
        List<Image> imgs = Functions.extractImages(batch);

        if (imgs.size() != actual) {
            throw new IllegalStateException(
                            String.format("Number of images in batch doesn't match with number of images in the upload request, expected %d, but got %d",
                                            imgs.size(), actual));
        }
    }

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
    public void checkAnalysisImages(Analysis ae, Map<String, byte[]> imgMap)
                    throws IllegalArgumentException, NoSuchAlgorithmException {
        // check all images referenced in all analyses exists in
        for (Image img : Functions.imageFromAnalysis(ae)) {

            // check image is present in input map
            if (!imgMap.containsKey(img.getName())) {
                throw new IllegalArgumentException(
                                String.format("Requested image %s is not contained in upload request", img.getName()));
            }

            // check sha-256 of the image is filled
            if (img.getSha256() == null || img.getSha256().isEmpty()) {
                throw new IllegalArgumentException(String
                                .format("Image with name %s has no or empty SHA-256 of the image", img.getName()));
            }

            // check sha-256 of the image agrees with computed sha-256 of the image byte content
            String sha256 = SciasFunctions.digestToString(SciasFunctions.byteDigest(imgMap.get(img.getName())));
            if (!img.getSha256().equals(sha256)) {
                throw new IllegalArgumentException(
                                String.format("SHA-256 of the image doesn't match, extecpted %s, but computed has is %s",
                                                img.getSha256(), sha256));
            }

        }
    }

    /**
     * Check if the {@link Batch} is already uploaded on the server
     * 
     * @param batchId
     * @param stationId
     * @throws IllegalArgumentException
     *             when batch is already present in server DB
     */
    public void checkBatchUnique(int batchId, int stationId) throws IllegalArgumentException {
        boolean uploaded = true;
        try {
            uploaded = batchSrv.isBatchUploaded(batchId, stationId);
        } catch (NonUniqueResultException e) {
            throw new IllegalArgumentException(
                            String.format("Batch with local ID %s from station %s is already uploaded in server DB",
                                            batchId, stationId));
        }
        if (uploaded) {
            throw new IllegalArgumentException(
                            String.format("Batch with local ID %s from station %s is already uploaded in server DB",
                                            batchId, stationId));
        }
    }
    
    /**
     * Check if each of the {@link Sample}s in thr batch is not uploaded on the server
     * 
     * @param samples
     * @param stationId
     * @throws IllegalArgumentException
     *             when sample is already present in server DB
     */
    public void checkSamplesUnique(List<Sample> samples, int stationId) throws IllegalArgumentException {
        for (Sample sample : samples) {
            checkSampleUnique(sample.getId(), stationId);
        }
    }
    
    /**
     * Check if the {@link Sample} is already uploaded in server
     * 
     * @param sampleId
     * @param stationId
     * @throws IllegalArgumentException
     *             when sample is already present on the server DB
     */
    public void checkSampleUnique(int sampleId, int stationId) throws IllegalArgumentException {
        boolean uploaded = true;
        try {
            uploaded = sampleSrv.isSampleUploaded(sampleId, stationId);
        } catch (NonUniqueResultException e) {
            throw new IllegalArgumentException(
                            String.format("Sample with local ID %s from station %s is already uploaded in server DB",
                                            sampleId, stationId));
        }
        if (uploaded) {
            throw new IllegalArgumentException(
                            String.format("Sample with local ID %s from station %s is already uploaded in server DB",
                                            sampleId, stationId));
        }
    }
    
    /**
     * Check if the analysis is already uploaded in server
     * 
     * @param analysisId
     * @param stationId
     * @throws IllegalArgumentException
     *             when analysis is already present in server DB
     */
    public void checkAnalysisUnique(int analysisId, int stationId) throws IllegalArgumentException {
        
        if (analysisSrv.isAnalysisUploaded(analysisId, stationId)) {
            throw new IllegalArgumentException(
                            String.format("Analysis with local ID %s from station %s is already uploaded in server DB",
                                            analysisId, stationId));
        }
        
    }

}
