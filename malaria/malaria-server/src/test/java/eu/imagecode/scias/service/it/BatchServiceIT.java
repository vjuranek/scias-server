package eu.imagecode.scias.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.jpa.PatientEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Locality;
import eu.imagecode.scias.model.rest.malaria.Patient;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.service.PatientService;
import eu.imagecode.scias.testutil.Generators;

@RunWith(Arquillian.class)
public class BatchServiceIT extends AbstractMalariaServiceIT {
    
    @Inject
    private BatchService batchService;
    
    @Inject
    private PatientService patientSrv;
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetAllBatches() {
        List<BatchEntity> batches = batchService.getAllBatches();
        assertEquals(2, batches.size());
        
        BatchEntity b1 = batches.get(0);
        assertEquals(100, b1.getId());
        assertTrue(b1.isFinished());
        assertEquals(STATION1_UUID, b1.getStation().getUuid());
        assertEquals(PATIENT1_NAME, b1.getPatient().getFirstName());
        
        BatchEntity b2 = batches.get(1);
        assertEquals(101, b2.getId());
        assertFalse(b2.isFinished());
        assertEquals(STATION2_UUID, b2.getStation().getUuid());
        assertEquals(PATIENT2_NAME, b2.getPatient().getFirstName());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetBatchById() {
        BatchEntity batch = batchService.getBatchById(100);
        assertNotNull(batch);
        
        assertEquals(100, batch.getId());
        assertTrue(batch.isFinished());
        assertEquals(STATION1_UUID, batch.getStation().getUuid());
        assertEquals(PATIENT1_NAME, batch.getPatient().getFirstName());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetBatchByLocalIdStationId() {
        BatchEntity batch = batchService.getBatchByLocalId(100, STATION2_ID);
        assertNotNull(batch);
        
        assertEquals(101, batch.getId());
        assertFalse(batch.isFinished());
        assertEquals(STATION2_UUID, batch.getStation().getUuid());
        assertEquals(PATIENT2_NAME, batch.getPatient().getFirstName());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetBatchByLocalIdStationUUID() {
        BatchEntity batch = batchService.getBatchByLocalId(100, STATION2_UUID);
        assertNotNull(batch);
        
        assertEquals(101, batch.getId());
        assertFalse(batch.isFinished());
        assertEquals(STATION2_UUID, batch.getStation().getUuid());
        assertEquals(PATIENT2_NAME, batch.getPatient().getFirstName());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testNewBatchUpload() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(111);
        sample.setCreated(Date.valueOf(LocalDate.now()));
        sample.setFinished(false);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("aa");
        patient.setLastName("aaaa");
        Batch batch = new Batch();
        batch.setId(111);
        batch.setFinished(true);
        batch.setCreated(Date.valueOf(LocalDate.now()));
        batch.getSample().add(sample);
        batch.setPatient(patient);
        
        Map<String, byte[]> imgMap = Generators.generateOneImgMap();
        
        BatchEntity be = batchService.uploadBatch(batch, imgMap, STATION1_UUID);
        
        //test that new batch record is created, DB init script creates 2
        List<BatchEntity> batches = batchService.getAllBatches();
        assertEquals(3, batches.size());
        
        BatchEntity testBatch = batchService.getBatchById(be.getId());
        assertNotNull(testBatch);
        
        List<AnalysisEntity> anals = testBatch.getSamples().get(0).getAnalyses();
        assertEquals(1, anals.size());
        AnalysisEntity analEnt = anals.get(0);
        assertNotNull(analEnt);
        assertEquals(Generators.TEST_ALGORITHM_VERSION, analEnt.getAlgorithmVersion());
        assertEquals(Generators.TEST_DATE, analEnt.getCreated());
    }
    
    @Test(expected = EJBTransactionRolledbackException.class)
    @ApplyScriptBefore({"populate_db.sql"})
    public void testExistingBatchUpload() throws Exception {
        Analysis anal = Generators.generateAnalysis(5);
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(1);
        sample.setCreated(Date.valueOf(LocalDate.now()));
        sample.setFinished(false);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("aa");
        patient.setLastName("aaaa");
        Batch batch = new Batch();
        batch.setId(100);
        batch.setFinished(true);
        batch.setCreated(Date.valueOf(LocalDate.now()));
        batch.getSample().add(sample);
        batch.setPatient(patient);
        
        Map<String, byte[]> imgMap = Generators.generateOneImgMap();
        
        batchService.uploadBatch(batch, imgMap, STATION1_UUID);
    }

    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testPatientNotRecreated() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(1);
        sample.setCreated(Date.valueOf(LocalDate.now()));
        sample.setFinished(false);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        Patient patient = new Patient();
        patient.setId(111);
        patient.setFirstName("aa");
        patient.setLastName("aaaa");
        Batch batch = new Batch();
        batch.setId(111);
        batch.setFinished(true);
        batch.setCreated(Date.valueOf(LocalDate.now()));
        batch.getSample().add(sample);
        batch.setPatient(patient);
        
        Map<String, byte[]> imgMap = Generators.generateOneImgMap();
        
        BatchEntity be = batchService.uploadBatch(batch, imgMap, STATION1_UUID);
        
        List<PatientEntity> patients = patientSrv.getAllPatients();
        assertEquals(3, patients.size());
        
        Analysis anal2 = Generators.generateAnalysis(2);
        Sample sample2 = new Sample();
        sample2.setId(2);
        sample2.setCreated(Date.valueOf(LocalDate.now()));
        sample2.setFinished(false);
        sample2.setLocality(loc);
        sample2.getAnalysis().add(anal2);
        Batch batch2 = new Batch();
        batch2.setId(112);
        batch2.setFinished(true);
        batch2.setCreated(Date.valueOf(LocalDate.now()));
        batch2.getSample().add(sample2);
        batch2.setPatient(patient);
        
        BatchEntity be2 = batchService.uploadBatch(batch2, imgMap, STATION1_UUID);
        
        List<PatientEntity> patients2 = patientSrv.getAllPatients();
        assertEquals(3, patients2.size());
        
    }
    
    @Test(expected = EJBTransactionRolledbackException.class)
    @ApplyScriptBefore({"populate_db.sql"})
    public void testUploadFromNonExistingStation() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(1);
        sample.setCreated(Date.valueOf(LocalDate.now()));
        sample.setFinished(false);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        Patient patient = new Patient();
        patient.setId(111);
        patient.setFirstName("aa");
        patient.setLastName("aaaa");
        Batch batch = new Batch();
        batch.setId(111);
        batch.setFinished(true);
        batch.getSample().add(sample);
        batch.setPatient(patient);
        
        Map<String, byte[]> imgMap = Generators.generateOneImgMap();
        
        BatchEntity be = batchService.uploadBatch(batch, imgMap, "doesn't exists");
    }
}
