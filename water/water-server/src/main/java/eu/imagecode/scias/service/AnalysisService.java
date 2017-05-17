package eu.imagecode.scias.service;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.ImageEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.Analysis;
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
