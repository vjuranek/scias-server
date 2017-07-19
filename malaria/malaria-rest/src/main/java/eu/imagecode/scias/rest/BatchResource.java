package eu.imagecode.scias.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import eu.imagecode.scias.model.rest.malaria.Batch;

/**
 * REST entrypoint which handles all opeations with {@link Batch}es. This is assumed to be the entrypoint which does
 * most of the work.
 * 
 * @author vjuranek
 *
 */
@Path("/batch")
public interface BatchResource {

    /**
     * Handles upload of the {@link Batch}
     * 
     * @param input
     *            {@link MultipartFormDataInput} form which containes {@link Batch} as well as images referenced in the
     *            {@link Batch}.
     * @return {@link Response} with appropriate status.
     * @throws Exception
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAnalysis(MultipartFormDataInput input) throws Exception;

    // helper methods

    /**
     * Used for passing station UUID from request URL to the {@link BatchResource}.
     * 
     * @param stationUuid
     *            UUID of the station which sent the request.
     */
    public void setStationId(String stationUuid);

}
