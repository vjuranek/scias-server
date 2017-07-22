package eu.imagecode.scias.rest.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import eu.imagecode.scias.conf.MalariaConf;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.rest.BatchResource;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.util.SciasFunctions;

/**
 * Implementation of {@link BatchResource. Does most of the actual work as it handles {@link Batch} uploads.
 * 
 * @author vjuranek
 *
 */
public class BatchResourceImpl implements BatchResource {

    @Inject
    @MalariaConf("req.part.batch.name")
    public String MULTIPART_NAME_BATCH;
    
    @Inject
    @MalariaConf("req.part.img.name")
    public String MULTIPART_NAME_IMAGES;
    
    @Inject
    @MalariaConf("resp.header.batch.name")
    public String HEADER_BATCH_ID;
    
    @Inject
    @MalariaConf("resp.header.error.name")
    public String HEADER_ERROR_MSG;

    @Inject
    private BatchService batchSrv;

    @Inject
    private Logger log;

    private String stationId;

    public Response uploadAnalysis(MultipartFormDataInput input) throws Exception {
        Map<String, List<InputPart>> parts = input.getFormDataMap();

        // load batch from the request
        List<InputPart> batchParts = parts.get(MULTIPART_NAME_BATCH);
        if (batchParts.size() == 0) {
            log.info(String.format(
                            "Invalid request - doesn't contain any batch part, returnig HTTP 400. (Contains %d parts).",
                            input.getParts().size()));
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MSG,
                            String.format("Multipart form don't contain any %s section!", MULTIPART_NAME_BATCH))
                            .build();
        }
        Batch batch = batchParts.get(0).getBody(Batch.class, null);

        // load images from the request
        Map<String, byte[]> imgMap = SciasFunctions.formInputToByteMap(parts.get(MULTIPART_NAME_IMAGES));

        // now do the upload into DB itself
        try {
            BatchEntity be = batchSrv.uploadBatch(batch, imgMap, stationId);
            return Response.ok().header(HEADER_BATCH_ID, be.getId()).build();
        } catch (IllegalArgumentException | EJBException e) {
            return Response.status(Status.BAD_REQUEST).header(HEADER_ERROR_MSG, e.getMessage()).build();
        }
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
