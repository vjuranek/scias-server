package eu.imagecode.scias.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.imagecode.scias.model.jpa.PatientEntity;
import eu.imagecode.scias.service.PatientService;

@RunWith(Arquillian.class)
public class PatientServiceIT extends AbstractMalariaServiceIT {

    @Inject
    private PatientService patientSrv;

    @Test
    @ApplyScriptBefore({ "populate_db.sql" })
    public void testGetAllPatients() {
        List<PatientEntity> patients = patientSrv.getAllPatients();
        assertEquals(2, patients.size());

        PatientEntity p1 = patients.get(0);
        assertEquals(100, p1.getId());
        assertEquals(PATIENT1_NAME, p1.getFirstName());
        assertEquals(STATION1_UUID, p1.getStation().getUuid());

        PatientEntity p2 = patients.get(1);
        assertEquals(101, p2.getId());
        assertEquals(PATIENT2_NAME, p2.getFirstName());
        assertEquals(STATION2_UUID, p2.getStation().getUuid());
    }

    @Test
    @ApplyScriptBefore({ "populate_db.sql" })
    public void testGetPatientById() {
        PatientEntity p1 = patientSrv.getPatientById(100);
        assertNotNull(p1);

        assertEquals(100, p1.getId());
        assertEquals(PATIENT1_NAME, p1.getFirstName());
        assertEquals(STATION1_UUID, p1.getStation().getUuid());
    }

    @Test
    @ApplyScriptBefore({ "populate_db.sql" })
    public void testGetPatientByLocalId() {
        PatientEntity p2 = patientSrv.getPatientByLocalId(100, STATION2_UUID);
        assertNotNull(p2);

        assertEquals(101, p2.getId());
        assertEquals(PATIENT2_NAME, p2.getFirstName());
        assertEquals(STATION2_UUID, p2.getStation().getUuid());
    }

}
