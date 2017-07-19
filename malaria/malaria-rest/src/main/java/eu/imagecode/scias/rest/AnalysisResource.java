package eu.imagecode.scias.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import eu.imagecode.scias.model.rest.malaria.Analysis;

/**
 * REST entrypoint which handles all opeations with {@link Analysis}. It's added just for convenience (e.g. easy listing
 * of all analyses and can be removed in the future.
 * 
 * @author vjuranek
 *
 */
@Path("/analysis")
public interface AnalysisResource {

    /**
     * List all the analysis in DB.
     * 
     * @return {@link List} of the {@link Analysis} in DB.
     */
    @Path("/")
    @GET
    @Produces("application/json")
    public List<Analysis> getAll();

    // helper methods
    
    /**
     * Used for passing station UUID from request URL to the {@link AnalysisResource}.
     * 
     * @param stationUuid
     *            UUID of the station which sent the request.
     */
    public void setStationId(String stationUuid);

}
