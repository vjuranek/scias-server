package eu.imagecode.scias.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/batch")
public interface BatchResource {
    
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAnalysis(MultipartFormDataInput input) throws Exception;
    
    //helper methods
    public void setStationId(String stationUuid);

}
