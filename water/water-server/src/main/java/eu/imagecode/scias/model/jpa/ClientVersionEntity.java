package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the client_version database table.
 * 
 */
@Entity
@Table(name = "client_version")
@NamedQuery(name = "ClientVersionEntity.findAll", query = "SELECT c FROM ClientVersionEntity c")
public class ClientVersionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "CLIENT_VERSION_ID_GENERATOR", sequenceName = "CLIENT_VERSION_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_VERSION_ID_GENERATOR")
    private Integer id;

    private Integer major;

    private Integer minor;

    private Timestamp released;

    public ClientVersionEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMajor() {
        return this.major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getMinor() {
        return this.minor;
    }

    public void setMinor(Integer minor) {
        this.minor = minor;
    }

    public Timestamp getReleased() {
        return this.released;
    }

    public void setReleased(Timestamp released) {
        this.released = released;
    }

}