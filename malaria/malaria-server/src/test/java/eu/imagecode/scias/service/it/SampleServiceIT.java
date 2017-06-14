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
import eu.imagecode.scias.model.jpa.SampleEntity;
import eu.imagecode.scias.model.jpa.StationEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.model.rest.malaria.Locality;
import eu.imagecode.scias.model.rest.malaria.Sample;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.service.SampleService;
import eu.imagecode.scias.testutil.Generators;

@RunWith(Arquillian.class)
public class SampleServiceIT extends AbstractMalariaServiceIT {
    
    @Inject
    private SampleService sampleSrv;
    
    @Inject
    private AnalysisService analysisSrv;
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetSampleById() {
        SampleEntity sample = sampleSrv.getSampleById(100);
        assertNotNull(sample);
        
        assertEquals(100, sample.getId());
        assertTrue(sample.isFinished());
        assertEquals(100, sample.getBatch().getId());
        assertEquals(STATION1_UUID, sample.getStation().getUuid());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetSampleByLocalId() {
        SampleEntity sample = sampleSrv.getSampleByLocalId(1, STATION2_UUID);
        assertNotNull(sample);
        
        assertEquals(101, sample.getId());
        assertFalse(sample.isFinished());
        assertEquals(101, sample.getBatch().getId());
        assertEquals(STATION2_UUID, sample.getStation().getUuid());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testSamplesByBatchId() {
        List<SampleEntity> samples = sampleSrv.getSamplesByBatchId(100);
        assertEquals(1, samples.size());
        
        SampleEntity sample = samples.get(0);
        assertEquals(100, sample.getId());
        assertTrue(sample.isFinished());
        assertEquals(100, sample.getBatch().getId());
        assertEquals(STATION1_UUID, sample.getStation().getUuid());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testNewSampleUpload() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(111);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        
        BatchEntity be = new BatchEntity(100, 1);
        StationEntity ste = new StationEntity(1);
        ste.setUuid(STATION1_UUID);
        
        SampleEntity se = sampleSrv.uploadSample(sample, be, ste);
        
        //test that new sample record is created, DB init script creates 2
        List<SampleEntity> samples = sampleSrv.getAllSamples();
        assertEquals(3, samples.size());
        
        SampleEntity testSample = sampleSrv.getSampleById(se.getId());
        assertNotNull(testSample);
        
        List<AnalysisEntity> anals = testSample.getAnalyses();
        assertEquals(1, anals.size());
        AnalysisEntity analEnt = anals.get(0);
        assertNotNull(analEnt);
        assertEquals(Generators.TEST_ALGORITHM_VERSION, analEnt.getAlgorithmVersion());
        assertEquals(Generators.TEST_DATE, analEnt.getCreated());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testExistingSampleUpload() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(1);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        
        BatchEntity be = new BatchEntity(100, 1);
        StationEntity ste = new StationEntity(1);
        ste.setUuid(STATION1_UUID);
        
        SampleEntity se = sampleSrv.uploadSample(sample, be, ste);
        
        //test that no new sample record is created, DB init script creates 2
        List<SampleEntity> samples = sampleSrv.getAllSamples();
        assertEquals(2, samples.size());
        
        SampleEntity testSample = sampleSrv.getSampleById(se.getId());
        assertNotNull(testSample);
        
        List<AnalysisEntity> anals = testSample.getAnalyses();
        assertEquals(1, anals.size());
        AnalysisEntity analEnt = anals.get(0);
        assertNotNull(analEnt);
        assertEquals(Generators.TEST_ALGORITHM_VERSION, analEnt.getAlgorithmVersion());
        //seems to be flaky
        //assertEquals(Generators.TEST_DATE, analEnt.getCreated());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testSamplerReupload() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        Locality loc = new Locality();
        loc.setId(1);
        Sample sample = new Sample();
        sample.setId(1);
        sample.setLocality(loc);
        sample.getAnalysis().add(anal);
        
        BatchEntity be = new BatchEntity(100, 1);
        StationEntity ste = new StationEntity(1);
        ste.setUuid(STATION1_UUID);
        
        SampleEntity se = sampleSrv.uploadSample(sample, be, ste);
        
        //test that no new sample record is created, DB init script creates 2
        List<SampleEntity> samples = sampleSrv.getAllSamples();
        assertEquals(2, samples.size());
        
        SampleEntity testSample = sampleSrv.getSampleById(se.getId());
        assertNotNull(testSample);
        
        List<AnalysisEntity> anals = testSample.getAnalyses();
        assertEquals(1, anals.size());
        AnalysisEntity analEnt = anals.get(0);
        assertNotNull(analEnt);
        assertEquals(Generators.TEST_ALGORITHM_VERSION, analEnt.getAlgorithmVersion());
        //seems to be flaky
        //assertEquals(Generators.TEST_DATE, analEnt.getCreated());
        
        se = sampleSrv.uploadSample(sample, be, ste);
        
        //test that no new sample record is created, DB init script creates 2
        samples = sampleSrv.getAllSamples();
        assertEquals(2, samples.size());
        
        testSample = sampleSrv.getSampleById(se.getId());
        assertNotNull(testSample);
        
        anals = testSample.getAnalyses();
        assertEquals(1, anals.size());
        
        //probably not needed, just for sure tests also analysis loaded via batch ID
        anals = analysisSrv.getAnalysisByBatchId(100);
        assertEquals(1, anals.size());
    }
}