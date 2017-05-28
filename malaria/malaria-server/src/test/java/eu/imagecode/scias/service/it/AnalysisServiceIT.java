package eu.imagecode.scias.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.jboss.arquillian.persistence.CreateSchema;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.jpa.BatchEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Batch;
import eu.imagecode.scias.model.rest.malaria.Locality;
import eu.imagecode.scias.model.rest.malaria.Patient;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.BatchService;
import eu.imagecode.scias.testutil.Generators;
import eu.imagecode.scias.util.DeploymentBuilder;

@CreateSchema({"prepare_db.sql"})
@RunWith(Arquillian.class)
public class AnalysisServiceIT {
    
    private static final String STATION_UUID = "aaaaaaaa-1111-2222-3333-bbbbbbbbbbbb";

    @Deployment
    public static WebArchive createDeployment() {
        return DeploymentBuilder.createRestServerWar();
    }

    @Inject
    private BatchService batchService;
    
    @Inject
    private AnalysisService analService;
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testAnalysisUploadAndLoadBack() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        AnalysisEntity ae = analService.uploadAnalysis(anal, STATION_UUID);
        
        AnalysisEntity analTest = analService.getAnalysisById(ae.getId());
        assertNotNull(analTest);
        assertEquals(Generators.TEST_ALGORITHM_VERSION, analTest.getAlgorithmVersion());
        assertEquals(Generators.TEST_DATE, analTest.getCreated());
        //assertEquals(new Integer(1), analEnt.getSubject()); //TODO fails - not done in ModelMappers
        
        List<AnalysisEntity> anals = analService.getAllAnalyses();
        assertEquals(1, anals.size());
        AnalysisEntity analEnt = anals.get(0);
        assertEquals("1.0", analEnt.getAlgorithmVersion());
        assertEquals(Generators.TEST_DATE, analEnt.getCreated());
        //assertEquals(new Integer(1), analEnt.getSubject()); //TODO fails - not done in ModelMappers
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
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
        
        BatchEntity be = batchService.uploadBatch(batch, STATION_UUID);
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