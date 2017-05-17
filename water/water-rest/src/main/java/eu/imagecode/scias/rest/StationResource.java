package eu.imagecode.scias.rest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * REST endpoint for analysis.
 * 
 * @author vjuranek
 *
 */
@Path("/station")
public interface StationResource {

    @Path("{id}/batch")
    public BatchResource getBatch(@PathParam("id") String stationId);
    
    @Path("{id}/analysis")
    public AnalysisResource getAnalysis(@PathParam("id") String stationId);
	
}
