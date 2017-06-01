package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
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
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @Column(name = "finished")
    private Boolean finished;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "batch")
    private List<SampleEntity> samples = new ArrayList<>(0);
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PatientEntity patient = new PatientEntity();
    
    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;
    
    public BatchEntity() {
    }
    
    public BatchEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
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

    public Boolean isFinished() {
        return this.finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public List<SampleEntity> getSamples() {
        return this.samples;
    }

    public void setSamples(List<SampleEntity> samples) {
        this.samples = samples;
    }

    public PatientEntity getPatient() {
        return this.patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
    
    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

}
