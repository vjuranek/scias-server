package eu.imagecode.scias.rest.impl;

import javax.inject.Inject;

import eu.imagecode.scias.rest.AnalysisResource;
import eu.imagecode.scias.rest.BatchResource;
import eu.imagecode.scias.rest.StationResource;

public class StationResourceImpl implements StationResource {

    @Inject
    BatchResource batch;
    
    @Inject
    AnalysisResource analysis;
    
    @Override
    public BatchResource getBatch(String stationId) {
        batch.setStationId(stationId);
        return batch;
    }
    
    @Override
    public AnalysisResource getAnalysis(String stationId) {
        analysis.setStationId(stationId);
        return analysis;
    }
}
