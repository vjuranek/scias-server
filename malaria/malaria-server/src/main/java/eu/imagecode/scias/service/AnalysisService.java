package eu.imagecode.scias.service;

import java.sql.Timestamp;
import java.util.List;

import eu.imagecode.scias.model.jpa.AnalysisEntity;

/**
 * Service for manipulating with analyses.
 * 
 * @author vjuranek
 *
 */
public interface AnalysisService {

    /**
     * Loads all analyses from DB
     *
     */
    public List<AnalysisEntity> getAllAnalyses();

    /**
     * Loads analysis with given global/server ID
     *
     */
    public AnalysisEntity getAnalysisById(int id);

    /**
     * Loads all analyses related to given batch ID
     * 
     */
    public List<AnalysisEntity> getAnalysisByBatchId(int batchId);

    /**
     * Loads analysis with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public AnalysisEntity getAnalysisByLocalId(int analysisId, int stationId);

    /**
     * Loads analysis with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public AnalysisEntity getAnalysisByLocalId(int analysisId, String stationUuid);

    /**
     * Loads all analyses in specified time range.
     * 
     */
    public List<AnalysisEntity> getAnalysisInTimeRange(Timestamp from, Timestamp to);

    /**
     * Checks, whether analysis with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isAnalysisUploaded(int analysisId, int stationId);

    /**
     * Checks, whether analysis with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isAnalysisUploaded(int analysisId, String stationUuid);
}
