package eu.imagecode.scias.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.AnalysisSet;

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
    
    /**
     * List all analyses in given time range
     * 
     * @param from lower bound of the interval
     * @param to upper bound of the interval
     * @return {@link List} of the {@link Analysis} in specified time range.
     */
    @Path("/time")
    @GET
    @Produces("application/xml")
    public AnalysisSet getInTimeRange(@QueryParam("from") String from, @QueryParam("to") String to);

    // helper methods
    
    /**
     * Used for passing station UUID from request URL to the {@link AnalysisResource}.
     * 
     * @param stationUuid
     *            UUID of the station which sent the request.
     */
    public void setStationId(String stationUuid);

}
