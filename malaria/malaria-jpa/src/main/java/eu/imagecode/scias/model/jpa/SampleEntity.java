package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "sample")
@NamedQueries({
    @NamedQuery(name = "SampleEntity.findAll", query = "SELECT s FROM SampleEntity s"),
    @NamedQuery(name = "SampleEntity.findById", query = "SELECT s FROM SampleEntity s WHERE s.id = :sampleId"),
    @NamedQuery(name = "SampleEntity.findByLocalIdAndStation", query = "select s from SampleEntity s, StationEntity st where s.localId = :localId and s.station.id = st.id and st.uuid = :stationUUID"),
    @NamedQuery(name = "SampleEntity.findByBatchId", query = "select s from SampleEntity s where s.batch.id = :batchId")
})
public class SampleEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SAMPLE_ID_GENERATOR", sequenceName = "SAMPLE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMPLE_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private BatchEntity batch;

    @Column(name = "created")
    private Date created;
    
    @Column(name = "update_time")
    private Date updateTime;
    
    @Column(name = "finished")
    private Boolean finished;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "locality_id")
    private LocalityEntity locality;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sample", cascade = CascadeType.ALL)
    private List<AnalysisEntity> analysises = new ArrayList<AnalysisEntity>(0);
    
    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    public SampleEntity() {
    }

    public SampleEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public SampleEntity(int id, BatchEntity batch, int localId, Boolean finished) {
        this.id = id;
        this.batch = batch;
        this.localId = localId;
        this.finished = finished;
    }

    public int getId() {
        return this.id;
    }

    public BatchEntity getBatch() {
        return this.batch;
    }

    public void setBatch(BatchEntity batch) {
        this.batch = batch;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean isFinished() {
        return this.finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
    
    public LocalityEntity getLocality() {
        return this.locality;
    }

    public void setLocality(LocalityEntity locality) {
        this.locality = locality;
    }
    
    public List<AnalysisEntity> getAnalyses() {
        return this.analysises;
    }

    public void setAnalyses(List<AnalysisEntity> analysises) {
        this.analysises = analysises;
    }
    
    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

}
