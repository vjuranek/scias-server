package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "patient")
@NamedQueries({
    @NamedQuery(name = "PatientEntity.findAll", query = "SELECT p FROM PatientEntity p"),
    @NamedQuery(name = "PatientEntity.findById", query = "SELECT p FROM PatientEntity p WHERE p.id = :patientId"),
    @NamedQuery(name = "PatientEntity.findByLocalIdAndStation", query = "select p from PatientEntity p, StationEntity s where p.localId = :localId and p.station.id = s.id and s.uuid = :stationUUID")
})
public class PatientEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "PATIENT_ID_GENERATOR", sequenceName = "PATIENT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PATIENT_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @Column(name = "first_name", length = 256)
    private String firstName;
    
    @Column(name = "middle_name", length = 256)
    private String middleName;
    
    @Column(name = "last_name", length = 256)
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "day_of_birth", length = 13)
    private Date dayOfBirth;
    
    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;
    

    public PatientEntity() {
    }

    public PatientEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public PatientEntity(int id, BatchEntity batch, int localId, String firstName,
                    String middleName, String lastName, Date dayOfBirth) {
        this.id = id;
        this.localId = localId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dayOfBirth = dayOfBirth;
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

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDayOfBirth() {
        return this.dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
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
        result = prime * result + ((dayOfBirth == null) ? 0 : dayOfBirth.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + id;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + localId;
        result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
        result = prime * result + ((station == null) ? 0 : station.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PatientEntity))
            return false;
        PatientEntity other = (PatientEntity) obj;
        if (dayOfBirth == null) {
            if (other.getDayOfBirth() != null)
                return false;
        } else if (!dayOfBirth.equals(other.getDayOfBirth()))
            return false;
        if (firstName == null) {
            if (other.getFirstName() != null)
                return false;
        } else if (!firstName.equals(other.getFirstName()))
            return false;
        if (id != other.getId())
            return false;
        if (lastName == null) {
            if (other.getLastName() != null)
                return false;
        } else if (!lastName.equals(other.getLastName()))
            return false;
        if (localId != other.getLocalId())
            return false;
        if (middleName == null) {
            if (other.getMiddleName() != null)
                return false;
        } else if (!middleName.equals(other.getMiddleName()))
            return false;
        if (station == null) {
            if (other.getStation() != null)
                return false;
        } else if (station.equals(other.getStation()))
            return false;
        return true;
    }

}
