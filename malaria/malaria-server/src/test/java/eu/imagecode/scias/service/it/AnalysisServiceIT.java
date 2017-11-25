package eu.imagecode.scias.service.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

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
    public void testGetAnalysisByLocalIdStationId() {
        AnalysisEntity analysis = analysisSrv.getAnalysisByLocalId(100, STATION1_ID);
        assertNotNull(analysis);
        
        assertEquals(100, analysis.getId());
        assertEquals(100, analysis.getLocalId());
        assertEquals("1.0", analysis.getAlgorithmVersion());
        assertEquals(100, analysis.getResultSet().getId());
        assertEquals(STATION1_UUID, analysis.getStation().getUuid());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetAnalysisByLocalIdStationUUID() {
        AnalysisEntity analysis = analysisSrv.getAnalysisByLocalId(100, STATION1_UUID);
        assertNotNull(analysis);
        
        assertEquals(100, analysis.getId());
        assertEquals(100, analysis.getLocalId());
        assertEquals("1.0", analysis.getAlgorithmVersion());
        assertEquals(100, analysis.getResultSet().getId());
        assertEquals(STATION1_UUID, analysis.getStation().getUuid());
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testIsAnalysisUploaded() {
        assertTrue(analysisSrv.isAnalysisUploaded(100, STATION1_UUID));
        assertFalse(analysisSrv.isAnalysisUploaded(200, STATION1_UUID));
    }
    
    @Test
    @ApplyScriptBefore({"populate_db.sql"})
    public void testGetAnalysisInTimeRange() {
        Timestamp past1 = Timestamp.valueOf("2016-10-10 00:00:00");
        Timestamp past2 = Timestamp.valueOf("2016-10-25 00:00:00");
        Timestamp now = Timestamp.valueOf("2016-11-25 00:00:00");
        Timestamp future1 = Timestamp.valueOf("2016-12-01 00:00:00");
        Timestamp future2 = Timestamp.valueOf("2017-10-10 00:00:00");
        Timestamp futureNow = new Timestamp(System.currentTimeMillis());
        
        assertEquals(0, analysisSrv.getAnalysisInTimeRange(past1, past2).size());
        assertEquals(0, analysisSrv.getAnalysisInTimeRange(past2, now).size());
        assertEquals(1, analysisSrv.getAnalysisInTimeRange(now, future1).size());
        assertEquals(101, analysisSrv.getAnalysisInTimeRange(now, future1).get(0).getId());
        assertEquals(101, analysisSrv.getAnalysisInTimeRange(now, future1).get(0).getLocalId());
        assertEquals(0, analysisSrv.getAnalysisInTimeRange(future1, future2).size());
        assertEquals(1, analysisSrv.getAnalysisInTimeRange(future2, futureNow).size());
        assertEquals(100, analysisSrv.getAnalysisInTimeRange(future2, futureNow).get(0).getId());
        assertEquals(100, analysisSrv.getAnalysisInTimeRange(future2, futureNow).get(0).getLocalId());
    }
    
}