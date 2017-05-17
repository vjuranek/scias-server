package eu.imagecode.scias.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import eu.imagecode.scias.model.rest.Analysis;

@Path("/analysis")
public interface AnalysisResource {
    
    @Path("/")
    @GET
    @Produces("application/json")
    public List<Analysis> getAll();
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAnalysis(MultipartFormDataInput input) throws Exception;
    
    //helper methods
    public void setStationId(String stationUuid);

}
