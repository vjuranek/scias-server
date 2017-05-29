package eu.imagecode.scias.model.jpa;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Patient generated by hbm2java
 */
@Entity
@Table(name = "patient")
public class PatientEntity implements java.io.Serializable {

    private int id;
    private int localId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dayOfBirth;
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

    @Id
    @SequenceGenerator(name = "PATIENT_ID_GENERATOR", sequenceName = "PATIENT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PATIENT_ID_GENERATOR")
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

    @Column(name = "first_name", length = 256)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "middle_name", length = 256)
    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "last_name", length = 256)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "day_of_birth", length = 13)
    public Date getDayOfBirth() {
        return this.dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
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
