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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "batch")
@NamedQueries({
    @NamedQuery(name = "BatchEntity.findAll", query = "SELECT b FROM BatchEntity b"),
    @NamedQuery(name = "BatchEntity.findById", query = "SELECT b FROM BatchEntity b WHERE b.id = :batchId"),
    @NamedQuery(name = "BatchEntity.findByLocalIdAndStation", query = "select b from BatchEntity b, StationEntity s where b.localId = :localId and b.station.id = s.id and s.uuid = :stationUUID")
})
public class BatchEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "BATCH_ID_GENERATOR", sequenceName = "BATCH_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @Column(name = "created")
    private Date created;
    
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((finished == null) ? 0 : finished.hashCode());
        result = prime * result + id;
        result = prime * result + localId;
        result = prime * result + ((patient == null) ? 0 : patient.hashCode());
        result = prime * result + ((samples == null) ? 0 : samples.hashCode());
        result = prime * result + ((station == null) ? 0 : station.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BatchEntity))
            return false;
        BatchEntity other = (BatchEntity) obj;
        if (created == null) {
            if (other.getCreated() != null)
                return false;
        } else if (!created.equals(other.getCreated()))
            return false;
        if (finished == null) {
            if (other.isFinished() != null)
                return false;
        } else if (!finished.equals(other.isFinished()))
            return false;
        if (id != other.getId())
            return false;
        if (localId != other.getLocalId())
            return false;
        if (patient == null) {
            if (other.getPatient() != null)
                return false;
        } else if (!patient.equals(other.getPatient()))
            return false;
        if (samples == null) {
            if (other.getSamples() != null)
                return false;
        } else if (!samples.equals(other.getSamples()))
            return false;
        if (station == null) {
            if (other.getStation() != null)
                return false;
        } else if (!station.equals(other.getStation()))
            return false;
        return true;
    }
    
}
