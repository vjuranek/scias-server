package eu.imagecode.scias.rest.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.rest.AnalysisResource;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.util.ModelMappers;

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

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

}
