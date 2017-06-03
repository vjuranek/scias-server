package eu.imagecode.scias.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Locality;
import eu.imagecode.scias.model.rest.malaria.Patient;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.testutil.Generators;

@RunWith(Arquillian.class)
public class BatchServiceIT extends AbstractMalariaServiceIT {
    
    @Inject
    private BatchService batchService;
    
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
    public void testGetBatchByLocalId() {
        BatchEntity batch = batchService.getBatchByLocalId(1, STATION2_UUID);
        assertNotNull(batch);
        
        assertEquals(101, batch.getId());
        assertFalse(batch.isFinished());
        assertEquals(STATION2_UUID, batch.getStation().getUuid());
        assertEquals(PATIENT2_NAME, batch.getPatient().getFirstName());
    }
    
    @Test
    @ApplyScriptBefore({"populate_station_table.sql"})
    public void testBatchUploadAndLoadBack() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(1);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("aa");
        patient.setLastName("aaaa");
        Batch batch = new Batch();
        batch.setId(1);
        batch.setFinished(true);
        batch.getSample().add(sample);
        batch.setPatient(patient);
        
        BatchEntity be = batchService.uploadBatch(batch, STATION1_UUID);
        BatchEntity testBatch = batchService.getBatchById(be.getId());
        assertNotNull(testBatch);
        
        List<AnalysisEntity> anals = testBatch.getSamples().get(0).getAnalyses();
        assertEquals(1, anals.size());
        AnalysisEntity analEnt = anals.get(0);
        assertNotNull(analEnt);
        //TODO load batch!!
        //assertNotNull(analEnt.getBatch());
        //assertEquals(1, analEnt.getBatch().getId().intValue());
        assertEquals(Generators.TEST_ALGORITHM_VERSION, analEnt.getAlgorithmVersion());
        assertEquals(Generators.TEST_DATE, analEnt.getCreated());
        //assertEquals(new Integer(1), analEnt.getSubject()); //TODO fails - not done in ModelMappers
    }

}
