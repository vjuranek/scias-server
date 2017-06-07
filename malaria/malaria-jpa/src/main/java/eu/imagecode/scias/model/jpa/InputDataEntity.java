package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "input_data", uniqueConstraints = { @UniqueConstraint(columnNames = "analysis_id"),
                @UniqueConstraint(columnNames = "image_id") })
public class InputDataEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "INPUT_DATA_ID_GENERATOR", sequenceName = "INPUT_DATA_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INPUT_DATA_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "analysis_id", unique = true)
    private AnalysisEntity analysis;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;
    
    @OneToOne(cascade = CascadeType.ALL)
    private ImageEntity image;
    
    public InputDataEntity() {
    }

    public InputDataEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public InputDataEntity(int id, int localId, AnalysisEntity analysis, ClientEntity client, ImageEntity image) {
        this.id = id;
        this.localId = localId;
        this.analysis = analysis;
        this.client = client;
        this.image = image;
    }

    public int getId() {
        return this.id;
    }

    public AnalysisEntity getAnalysis() {
        return this.analysis;
    }

    public void setAnalysis(AnalysisEntity analysis) {
        this.analysis = analysis;
    }

    public ClientEntity getClient() {
        return this.client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public ImageEntity getImage() {
        return this.image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

}
