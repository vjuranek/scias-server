package eu.imagecode.scias.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import eu.imagecode.scias.model.rest.malaria.Analysis;

@Path("/analysis")
public interface AnalysisResource {
    
    @Path("/")
    @GET
    @Produces("application/json")
    public List<Analysis> getAll();
    
    //helper methods
    public void setStationId(String stationUuid);

}
