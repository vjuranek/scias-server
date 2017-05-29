package eu.imagecode.scias.model.jpa;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Batch generated by hbm2java
 */
@Entity
@Table(name = "batch")
@NamedQueries({
    @NamedQuery(name = "BatchEntity.findAll", query = "SELECT b FROM BatchEntity b"),
    @NamedQuery(name = "BatchEntity.findById", query = "SELECT b FROM BatchEntity b WHERE b.id = :batchId")
})
public class BatchEntity implements java.io.Serializable {

    private int id;
    private int localId;
    private Boolean finished;
    private List<SampleEntity> samples = new ArrayList<>(0);
    private PatientEntity patient = new PatientEntity();
    private StationEntity station;
    
    public BatchEntity() {
    }
    
    public BatchEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }
   
    @Id
    @SequenceGenerator(name = "BATCH_ID_GENERATOR", sequenceName = "BATCH_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    @Column(name = "local_id", nullable = false)
    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    @Column(name = "finished")
    public Boolean isFinished() {
        return this.finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "batch")
    public List<SampleEntity> getSamples() {
        return this.samples;
    }

    public void setSamples(List<SampleEntity> samples) {
        this.samples = samples;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public PatientEntity getPatient() {
        return this.patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
    
    @ManyToOne
    @JoinColumn(name = "station_id")
    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

}
