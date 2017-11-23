package eu.imagecode.scias.rest.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.AnalysisSet;
import eu.imagecode.scias.rest.AnalysisResource;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.util.ModelMappers;
import eu.imagecode.scias.util.SciasFunctions;

/**
 * Implementation of {@link AnalysisResourceImpl}.
 * 
 * @author vjuranek
 *
 */
public class AnalysisResourceImpl implements AnalysisResource {

    @Inject
    private AnalysisService analService;

    private String stationId;

    public List<Analysis> getAll() {
        List<AnalysisEntity> analyses = analService.getAllAnalyses();
        return analyses.stream().map(anal -> ModelMappers.entityToAnalysis(anal)).collect(Collectors.toList());
    }
    
    public AnalysisSet getInTimeRange(@QueryParam("from") String from, @QueryParam("to") String to) {
        List<AnalysisEntity> analysesEnt = analService.getAnalysisInTimeRange(SciasFunctions.dayStringToTimestamp(from), SciasFunctions.dayStringToTimestamp(to));
        List<Analysis> analyses = analysesEnt.stream().map(anal -> ModelMappers.entityToAnalysis(anal)).collect(Collectors.toList());
        AnalysisSet as = new AnalysisSet();
        as.getAnalysis().addAll(analyses);
        return as;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
