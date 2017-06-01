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
@Table(name = "client_version")
public class ClientVersionEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "CLIENT_VERSION_ID_GENERATOR", sequenceName = "CLIENT_VERSION_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_VERSION_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "major")
    private Short major;
    
    @Column(name = "minor")
    private Short minor;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "released", length = 29)
    private Date released;

    public ClientVersionEntity() {
    }

    public ClientVersionEntity(int id) {
        this.id = id;
    }

    public ClientVersionEntity(int id, Short major, Short minor, Date released) {
        this.id = id;
        this.major = major;
        this.minor = minor;
        this.released = released;
    }

    public int getId() {
        return this.id;
    }

    public Short getMajor() {
        return this.major;
    }

    public void setMajor(Short major) {
        this.major = major;
    }

    public Short getMinor() {
        return this.minor;
    }

    public void setMinor(Short minor) {
        this.minor = minor;
    }

    public Date getReleased() {
        return this.released;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

}
