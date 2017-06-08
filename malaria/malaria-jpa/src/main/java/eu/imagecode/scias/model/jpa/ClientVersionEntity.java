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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((major == null) ? 0 : major.hashCode());
        result = prime * result + ((minor == null) ? 0 : minor.hashCode());
        result = prime * result + ((released == null) ? 0 : released.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ClientVersionEntity))
            return false;
        ClientVersionEntity other = (ClientVersionEntity) obj;
        if (id != other.getId())
            return false;
        if (major == null) {
            if (other.getMajor() != null)
                return false;
        } else if (!major.equals(other.getMajor()))
            return false;
        if (minor == null) {
            if (other.getMinor() != null)
                return false;
        } else if (!minor.equals(other.getMinor()))
            return false;
        if (released == null) {
            if (other.getReleased() != null)
                return false;
        } else if (!released.equals(other.getReleased()))
            return false;
        return true;
    }

}
