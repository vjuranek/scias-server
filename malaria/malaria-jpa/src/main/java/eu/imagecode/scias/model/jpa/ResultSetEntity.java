package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "result_set")
public class ResultSetEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "RESULT_SET_ID_GENERATOR", sequenceName = "RESULT_SET_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SET_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resultSet", cascade = CascadeType.ALL)
    private Set<UnclassifiedObjectEntity> unclassifiedObjects = new HashSet<UnclassifiedObjectEntity>(0);
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resultSet", cascade = CascadeType.ALL)
    private Set<ResultEntity> results = new HashSet<ResultEntity>(0);
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "resultSet", cascade = CascadeType.ALL)
    private AnalysisEntity analysis;
    

    public ResultSetEntity() {
    }

    public ResultSetEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public ResultSetEntity(int id, int localId, Set<UnclassifiedObjectEntity> unclassifiedObjects,
                    Set<ResultEntity> results, AnalysisEntity analysis) {
        this.id = id;
        this.localId = localId;
        this.unclassifiedObjects = unclassifiedObjects;
        this.results = results;
        this.analysis = analysis;
    }

    public int getId() {
        return this.id;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public Set<UnclassifiedObjectEntity> getUnclassifiedObjects() {
        return this.unclassifiedObjects;
    }

    public void setUnclassifiedObjects(Set<UnclassifiedObjectEntity> unclassifiedObjects) {
        this.unclassifiedObjects = unclassifiedObjects;
    }

    public Set<ResultEntity> getResults() {
        return this.results;
    }

    public void setResults(Set<ResultEntity> results) {
        this.results = results;
    }

    
    public AnalysisEntity getAnalysis() {
        return this.analysis;
    }

    public void setAnalysis(AnalysisEntity analysis) {
        this.analysis = analysis;
    }
    
}
