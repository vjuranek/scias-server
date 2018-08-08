package eu.imagecode.scias.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;

import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.service.SampleService;
import eu.imagecode.scias.service.ValidationService;
import eu.imagecode.scias.util.Functions;
import eu.imagecode.scias.util.SciasFunctions;

/**
 * Implementation of {@link ValidationService}.
 * 
 * @author vjuranek
 *
 */
@Dependent
@Stateless
public class ValidationServiceImpl implements ValidationService {

    @Inject
    BatchService batchSrv;

    @Inject
    SampleService sampleSrv;

    @Inject
    AnalysisService analysisSrv;

    @Override
    public void checkBatchUploadRequest(Batch batch, Map<String, byte[]> imgMap, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException {

        checkNumberOfImages(batch, imgMap.size()); // check number of images first as it doesn't require any DB query
        checkBatchUnique(batch.getId(), stationId);

        for (Analysis ae : Functions.analysesFromBatch(batch)) {
            checkAnalysis(ae, imgMap, stationId);
        }

    }

    @Override
    public void checkNewBatchUploadRequest(List<Sample> samples, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException {
        checkSamplesUnique(samples, stationId);
    }

    @Override
    public void checkAnalysis(Analysis analysis, Map<String, byte[]> imgMap, int stationId)
                    throws IllegalArgumentException, NoSuchAlgorithmException {

        checkAnalysisImages(analysis, imgMap);
        checkAnalysisUnique(analysis.getId(), stationId);

    }

    @Override
    public void checkNumberOfImages(Batch batch, int imgSizeActual) throws IllegalArgumentException {
        List<Image> imgs = Functions.extractImages(batch);

        if (imgs.size() != imgSizeActual) {
            throw new IllegalStateException(
                            String.format("Number of images in batch doesn't match with number of images in the upload request, expected %d, but got %d",
                                            imgs.size(), imgSizeActual));
        }
    }

    @Override
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

    @Override
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

    @Override
    public void checkSamplesUnique(List<Sample> samples, int stationId) throws IllegalArgumentException {
        for (Sample sample : samples) {
            checkSampleUnique(sample.getId(), stationId);
        }
    }

    @Override
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

    @Override
    public void checkAnalysisUnique(int analysisId, int stationId) throws IllegalArgumentException {

        if (analysisSrv.isAnalysisUploaded(analysisId, stationId)) {
            throw new IllegalArgumentException(
                            String.format("Analysis with local ID %s from station %s is already uploaded in server DB",
                                            analysisId, stationId));
        }

    }

}
