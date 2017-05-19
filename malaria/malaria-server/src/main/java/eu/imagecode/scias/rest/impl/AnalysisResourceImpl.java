package eu.imagecode.scias.rest.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.rest.AnalysisResource;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.ImageService;
import eu.imagecode.scias.util.ModelMappers;
import eu.imagecode.scias.util.SciasFunctions;

public class AnalysisResourceImpl implements AnalysisResource {

    public static final String MULTIPART_NAME_ANALYSIS = "analysis";
    public static final String MULTIPART_NAME_IMAGES = "images";

    // TODO move to some global config
    public static final String UPLOAD_DIR = "/tmp/scias/";
    public static final String HEADER_ANALYSIS_ID = "AnalysisID";

    @Inject
    private AnalysisService analService;

    @Inject
    private ImageService imgService;

    private String stationId;

    public List<Analysis> getAll() {
        List<AnalysisEntity> analyses = analService.getAllAnalyses();
        return analyses.stream().map(anal -> ModelMappers.entityToAnalysis(anal)).collect(Collectors.toList());
    }

    @Override
    public Response uploadAnalysis(MultipartFormDataInput input) throws Exception {
        // TODO run in one transaction?
        Map<String, List<InputPart>> parts = input.getFormDataMap();
        List<InputPart> analParts = parts.get(MULTIPART_NAME_ANALYSIS);
        Map<String, InputPart> imgInputMap = SciasFunctions.formInputToImageMap(parts.get(MULTIPART_NAME_IMAGES));

        Analysis analysis = analParts.get(0).getBody(Analysis.class, null);
        AnalysisEntity ae = analService.uploadAnalysis(analysis, stationId); // TODO run in transaction and eventually
                                                                             // abort (e.g. if number of image doesn't
                                                                             // match)
        List<ImageEntity> imgs = analService.extractImages(ae);

        if (imgs.size() != imgInputMap.keySet().size()) {
            throw new IllegalStateException(
                            "Number of images in analysis doesn't match with number of images in the upload request");
        }

        File uploadDir = new File(UPLOAD_DIR + ae.getId());
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                throw new IOException("Unable to create directory " + uploadDir);
            }
        } else {
            //TODO log warning (but don't fails), dir shouldn't exists!
        }

        for (ImageEntity img : imgs) {
            if (!imgInputMap.containsKey(img.getName())) {
                throw new IllegalStateException(
                                String.format("Requested image %s is not contained in upload request", img.getName()));
            }
            imgService.uploadImage(img.getSha256(), new File(uploadDir, img.getName()),
                            imgInputMap.get(img.getName()).getBody(InputStream.class, null));
        }

        return Response.ok().header(HEADER_ANALYSIS_ID, ae.getId()).build();
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
