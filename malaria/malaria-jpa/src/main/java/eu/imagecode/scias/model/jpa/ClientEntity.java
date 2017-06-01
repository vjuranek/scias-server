package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "client", uniqueConstraints = @UniqueConstraint(columnNames = "version_id"))
public class ClientEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "CLIENT_ID_GENERATOR", sequenceName = "CLIENT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", unique = true)
    private ClientVersionEntity clientVersion;

    public ClientEntity() {
    }

    public ClientEntity(int id) {
        this.id = id;
    }

    public ClientEntity(int id, ClientVersionEntity clientVersion) {
        this.id = id;
        this.clientVersion = clientVersion;
    }

    public int getId() {
        return this.id;
    }

    public ClientVersionEntity getClientVersion() {
        return this.clientVersion;
    }

    public void setClientVersion(ClientVersionEntity clientVersion) {
        this.clientVersion = clientVersion;
    }

}
