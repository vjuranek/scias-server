package eu.imagecode.scias.rest.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.rest.BatchResource;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.service.ValidationService;
import eu.imagecode.scias.util.SciasFunctions;

public class BatchResourceImpl implements BatchResource {

    // TODO move to some global config
    public static final String MULTIPART_NAME_BATCH = "batch";
    public static final String MULTIPART_NAME_IMAGES = "images";
    public static final String HEADER_BATCH_ID = "BatchID";

    @Inject
    private BatchService batchSrv;
    
    @Inject
    private ValidationService validationSrv;

    private String stationId;

    public Response uploadAnalysis(MultipartFormDataInput input) throws Exception {
        Map<String, List<InputPart>> parts = input.getFormDataMap();

        // load batch from the request
        List<InputPart> batchParts = parts.get(MULTIPART_NAME_BATCH);
        if (batchParts.size() == 0) {
            throw new IllegalArgumentException(
                            String.format("Multipart form don't contain any %s section!", MULTIPART_NAME_BATCH));
        }
        Batch batch = batchParts.get(0).getBody(Batch.class, null);

        // load images from the request
        Map<String, byte[]> imgMap = SciasFunctions.formInputToByteMap(parts.get(MULTIPART_NAME_IMAGES));

        // do some check before actual upload
        validationSrv.checkBatchUploadRequest(batch, imgMap, stationId);

        // now do the upload into DB itself
        BatchEntity be = batchSrv.uploadBatch(batch, imgMap, stationId);

        return Response.ok().header(HEADER_BATCH_ID, be.getId()).build();
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
