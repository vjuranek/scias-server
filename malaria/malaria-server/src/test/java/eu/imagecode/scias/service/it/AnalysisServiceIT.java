package eu.imagecode.scias.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.model.rest.malaria.Analysis;
import eu.imagecode.scias.service.AnalysisService;
import eu.imagecode.scias.testutil.Generators;

@RunWith(Arquillian.class)
public class AnalysisServiceIT extends AbstractMalariaServiceIT {
    
    @Inject
    private AnalysisService analService;
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testAnalysisUploadAndLoadBack() throws Exception {
        Analysis anal = Generators.generateAnalysis();
        AnalysisEntity ae = analService.uploadAnalysis(anal, STATION1_UUID);
        
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
    
}