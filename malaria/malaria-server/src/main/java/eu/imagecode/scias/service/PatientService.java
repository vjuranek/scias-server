package eu.imagecode.scias.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import eu.imagecode.scias.model.jpa.PatientEntity;

/**
 * Service for manipulating with patients. Currently only load patients or queries DB for some facts about patient.
 * 
 * @author vjuranek
 *
 */
@Stateless
public class PatientService {
    
    @Inject
    private EntityManager em;
    
    /**
     * Loads all patients from DB.
     * 
     */
    public List<PatientEntity> getAllPatients() {
        return em.createNamedQuery(PatientEntity.QUERY_FIND_ALL, PatientEntity.class).getResultList();
    }
    
    /**
     * Loads patient with specified global/server ID.
     * 
     */
    public PatientEntity getPatientById(int id) {
        return em.createNamedQuery(PatientEntity.QUERY_FIND_BY_ID, PatientEntity.class).setParameter("patientId", id).getSingleResult();
    }
    
    /**
     * Loads patient with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public PatientEntity getPatientByLocalId(int localId, int stationId) {
        try {
            return em.createNamedQuery(PatientEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, PatientEntity.class).setParameter("localId", localId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }        
    }
    
    /**
     * Loads patient with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    public PatientEntity getPatientByLocalId(int localId, String stationUuid) {
        try {
            return em.createNamedQuery(PatientEntity.QUERY_FIND_BY_LOCAL_ID_STATION_UUID, PatientEntity.class).setParameter("localId", localId).setParameter("stationUUID", stationUuid).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }        
    }
}
