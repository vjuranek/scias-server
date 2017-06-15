package eu.imagecode.scias.rest.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Image;
import eu.imagecode.scias.rest.BatchResource;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.service.ImageService;
import eu.imagecode.scias.util.Functions;
import eu.imagecode.scias.util.SciasFunctions;

public class BatchResourceImpl implements BatchResource {

    public static final String MULTIPART_NAME_BATCH = "batch";
    public static final String MULTIPART_NAME_IMAGES = "images";

    // TODO move to some global config
    public static final String UPLOAD_DIR = "/tmp/scias/batch/";
    public static final String HEADER_BATCH_ID = "BatchID";

    @Inject
    private BatchService batchSrv;
    
    @Inject
    private AnalysisService analysisSrv;

    @Inject
    private ImageService imgSrv;

    private String stationId;

    public Response uploadAnalysis(MultipartFormDataInput input) throws Exception {
        // TODO run in one transaction?
        Map<String, List<InputPart>> parts = input.getFormDataMap();
        List<InputPart> batchParts = parts.get(MULTIPART_NAME_BATCH);
        Map<String, InputPart> imgInputMap = SciasFunctions.formInputToImageMap(parts.get(MULTIPART_NAME_IMAGES));

        Batch batch = batchParts.get(0).getBody(Batch.class, null);
        List<Image> imgs = batchSrv.extractImages(batch);

        if (imgs.size() != imgInputMap.keySet().size()) {
            throw new IllegalStateException(
                            String.format("Number of images in analysis doesn't match with number of images in the upload request, expected %d, but got %d",
                                            imgs.size(), imgInputMap.keySet().size()));
        }

        BatchEntity be = batchSrv.uploadBatch(batch, stationId); // TODO run in transaction and eventually abort
                                                                     // (e.g. if number of image doesn't match)
        
        File batchUpDir = new File(UPLOAD_DIR + be.getId());
        if (!batchUpDir.exists()) {
            if (!batchUpDir.mkdirs()) {
                throw new IOException("Unable to create directory " + batchUpDir);
            }
        }

        List<AnalysisEntity> analyses = analysisSrv.getAnalysisByBatchId(be.getId());
        for (AnalysisEntity ae : analyses) {
            File analUpDir = new File(batchUpDir, String.valueOf(ae.getId()));
            if (!analUpDir.exists()) {
                if (!analUpDir.mkdirs()) {
                    throw new IOException("Unable to create directory " + analUpDir);
                }
            } else {
                // TODO don't fail, but log WARN, dir shouldn't exists
            }

            for (ImageEntity img : Functions.imageFromAnalysis(ae)) {
                if (!imgInputMap.containsKey(img.getName())) {
                    throw new IllegalStateException(String
                                    .format("Requested image %s is not contained in upload request", img.getName()));
                }
                imgSrv.uploadImage(img.getSha256().trim(), new File(analUpDir, img.getName()),
                                imgInputMap.get(img.getName()).getBody(InputStream.class, null));
            }
        }

        return Response.ok().header(HEADER_BATCH_ID, be.getId()).build();
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
