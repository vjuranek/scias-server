package eu.imagecode.scias.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ApplyScriptBefore;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.imagecode.scias.model.jpa.AnalysisEntity;
import eu.imagecode.scias.service.AnalysisService;

@RunWith(Arquillian.class)
public class AnalysisServiceIT extends AbstractMalariaServiceIT {
    
    @Inject
    private AnalysisService analysisSrv;
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetAnalysisByLocalId() {
        AnalysisEntity analysis = analysisSrv.getAnalysisByLocalId(1, STATION1_UUID);
        assertNotNull(analysis);
        
        assertEquals(100, analysis.getId());
        assertEquals(1, analysis.getLocalId());
        assertEquals("1.0", analysis.getAlgorithmVersion());
        assertEquals(100, analysis.getResultSet().getId());
        assertEquals(STATION1_UUID, analysis.getStation().getUuid());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testIsAnalysisUploaded() {
        assertTrue(analysisSrv.isAnalysisUploaded(1, STATION1_UUID));
        assertFalse(analysisSrv.isAnalysisUploaded(2, STATION1_UUID));
    }
    
}