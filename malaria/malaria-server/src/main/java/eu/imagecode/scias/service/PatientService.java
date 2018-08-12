package eu.imagecode.scias.service;

import java.util.List;

import javax.ejb.Stateless;

import eu.imagecode.scias.model.jpa.PatientEntity;

/**
 * Service for manipulating with patients. Currently only load patients or queries DB for some facts about patient.
 * 
 * @author vjuranek
 *
 */
public interface PatientService {

    /**
     * Loads all patients from DB.
     * 
     */
    List<PatientEntity> getAllPatients();

    /**
     * Loads patient with specified global/server ID.
     * 
     */
    PatientEntity getPatientById(int id);

    /**
     * Loads patient with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    PatientEntity getPatientByLocalId(int localId, int stationId);

    /**
     * Loads patient with specified local/client ID for given station (local ID has to be unique for one station).
     * 
     */
    PatientEntity getPatientByLocalId(int localId, String stationUuid);

}