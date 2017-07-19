package eu.imagecode.scias.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;

/**
 * Main REST entrypoint to the application, which however just delegates work to the {@link BatchResource} and
 * {@link AnalysisResource}. Most of the actual work is done in {@link BatchResource}. {@link AnalysisResource} is added
 * just for convenience (e.g. easy listing of all analyses), but can be removed in the future.
 * 
 * @author vjuranek
 *
 */
@Path("/station")
public interface StationResource {

    /**
     * Creates {@link BatchResource} which will process requests related to manipulation with {@link Batch}es.
     * 
     * @param stationId
     *            UUID of the station which sent the request
     * @return {@link BatchResource} which will handle processing of the {@link Batch} requst.
     */
    @Path("{id}/batch")
    public BatchResource getBatch(@PathParam("id") String stationId);

    /**
     * Creates {@link AnalysisResource} which will process requests related to manipulation with {@link Analysis}.
     * 
     * @param stationId
     *            UUID of the station which sent the request
     * @return {@link AnalysisResource} which will handle processing of the {@link Analysis} requst.
     */
    @Path("{id}/analysis")
    public AnalysisResource getAnalysis(@PathParam("id") String stationId);

}
