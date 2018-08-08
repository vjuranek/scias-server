package eu.imagecode.scias.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import eu.imagecode.scias.model.jpa.PatientEntity;
import eu.imagecode.scias.service.PatientService;

/**
 * Implementation of {@link PatientService}.
 * 
 * @author vjuranek
 *
 */
@Dependent
@Stateless
public class PatientServiceImpl implements PatientService {

    @Inject
    private EntityManager em;

    @Override
    public List<PatientEntity> getAllPatients() {
        return em.createNamedQuery(PatientEntity.QUERY_FIND_ALL, PatientEntity.class).getResultList();
    }

    @Override
    public PatientEntity getPatientById(int id) {
        return em.createNamedQuery(PatientEntity.QUERY_FIND_BY_ID, PatientEntity.class).setParameter("patientId", id)
                        .getSingleResult();
    }

    @Override
    public PatientEntity getPatientByLocalId(int localId, int stationId) {
        try {
            return em.createNamedQuery(PatientEntity.QUERY_FIND_BY_LOCAL_ID_AND_STATION, PatientEntity.class)
                            .setParameter("localId", localId).setParameter("stationID", stationId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public PatientEntity getPatientByLocalId(int localId, String stationUuid) {
        try {
            return em.createNamedQuery(PatientEntity.QUERY_FIND_BY_LOCAL_ID_STATION_UUID, PatientEntity.class)
                            .setParameter("localId", localId).setParameter("stationUUID", stationUuid)
                            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
