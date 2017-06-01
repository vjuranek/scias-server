package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "patient")
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

}
