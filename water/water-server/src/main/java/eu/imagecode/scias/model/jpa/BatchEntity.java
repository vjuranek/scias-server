package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "batch")
@NamedQueries({
    @NamedQuery(name = "BatchEntity.findAll", query = "SELECT b FROM BatchEntity b"),
    @NamedQuery(name = "BatchEntity.findById", query = "SELECT b FROM BatchEntity b WHERE b.id = :batchId")
})
public class BatchEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "BATCH_ID_GENERATOR", sequenceName = "BATCH_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_GENERATOR")
    private Integer id;

    @Column(name = "local_id")
    private Integer localId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;
    
    private boolean finished;

    @OneToOne(mappedBy = "batch")
    private SubjectEntity subject;
    
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
    private List<AnalysisEntity> analyses;

    public BatchEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getLocalId() {
        return this.localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }
    
    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    public SubjectEntity getSubject() {
        return this.subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
    
    public List<AnalysisEntity> getAnalyses() {
        return this.analyses;
    }

    public void setAnalyses(List<AnalysisEntity> analyses) {
        this.analyses = analyses;
    }

    public AnalysisEntity addAnalysis(AnalysisEntity analysis) {
        getAnalyses().add(analysis);
        analysis.setBatch(this);

        return analysis;
    }

    public AnalysisEntity removeAnalysis(AnalysisEntity analysis) {
        getAnalyses().remove(analysis);
        analysis.setBatch(null);

        return analysis;
    }

}
