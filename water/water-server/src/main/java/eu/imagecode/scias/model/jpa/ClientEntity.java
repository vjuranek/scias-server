package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the client database table.
 * 
 */
@Entity
@Table(name = "client")
@NamedQuery(name = "ClientEntity.findAll", query = "SELECT c FROM ClientEntity c")
public class ClientEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "CLIENT_ID_GENERATOR", sequenceName = "CLIENT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_ID_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "version_id")
    private ClientVersionEntity clientVersion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<InputDataEntity> inputData;

    public ClientEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientVersionEntity getClientVersion() {
        return this.clientVersion;
    }

    public void setClientVersion(ClientVersionEntity clientVersion) {
        this.clientVersion = clientVersion;
    }

    public List<InputDataEntity> getInputData() {
        return this.inputData;
    }

    public void setInputDataEntity(List<InputDataEntity> inputData) {
        this.inputData = inputData;
    }

    public InputDataEntity addInputData(InputDataEntity inputData) {
        getInputData().add(inputData);
        inputData.setClient(this);
        return inputData;
    }

    public InputDataEntity removeImage(InputDataEntity inputData) {
        getInputData().remove(inputData);
        inputData.setClient(null);
        return inputData;
    }

}