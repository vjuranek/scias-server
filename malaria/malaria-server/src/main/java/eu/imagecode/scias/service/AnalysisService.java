package eu.imagecode.scias.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.util.ModelMappers;

@Stateless
public class AnalysisService {

    @Inject
    private EntityManager em;

    public List<AnalysisEntity> getAllAnalyses() {
        return em.createNamedQuery("AnalysisEntity.findAll", AnalysisEntity.class).getResultList();
    }
    
    public AnalysisEntity getAnalysisById(int id) {
        return em.createNamedQuery("AnalysisEntity.findById", AnalysisEntity.class).setParameter("analysisId", id).getSingleResult();
    }
    
    public List<AnalysisEntity> getAnalysisByBatchId(int batchId) {
        try {
            return em.createNamedQuery("AnalysisEntity.findByBatchId", AnalysisEntity.class).setParameter("batchId", batchId).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<AnalysisEntity>();
        }
    }
    
    /**
     * Loads analysis with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public AnalysisEntity getAnalysisByLocalId(int analysisId, String stationUuid) {
        return em.createNamedQuery("AnalysisEntity.findByLocalIdAndStation", AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationUUID", stationUuid).getSingleResult();
    }
    
    
    /**
     * Checks, wheather analysis with given local/client ID was already uploaded from given station.
     * 
     */
    public boolean isAnalysisUploaded(int analysisId, String stationUuid) {
        try {
            em.createNamedQuery("AnalysisEntity.findByLocalIdAndStation", AnalysisEntity.class).setParameter("localId", analysisId).setParameter("stationUUID", stationUuid).getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }
    
    /**
     * Uploads one single analysis into database.
     * 
     */
    public AnalysisEntity uploadAnalysis(Analysis analysis, String stationUuid) {
        AnalysisEntity ae = ModelMappers.analysisToEntity(analysis, getStationByUuid(stationUuid));
        em.persist(ae);
        return ae;
    }
    
    /**
     * Puts all images related/contained in given {@link AnalysisEntity} to a {@link List}.  
     * 
     * @param analysis {@link AnalysisEntity} from which images should be extracted. 
     * @return {@link List} of {@link ImageEntity}s related to given analysis. 
     */
    public List<ImageEntity> extractImages(AnalysisEntity analysis) {
        List<ImageEntity> imgs = new LinkedList<ImageEntity>();
        imgs.add(analysis.getInputData().getImage());
        analysis.getResultSet().getUnclassifiedObjects().forEach(uo -> imgs.add(uo.getImage()));
        return imgs;
    }
    
    //TODO move somewhere to not duplicate the code - see BatchService
    private StationEntity getStationByUuid(String stationUuid) {
        return em.createNamedQuery("StationEntity.findByUuid", StationEntity.class).setParameter("stationUuid", stationUuid).getSingleResult();
    }

}
