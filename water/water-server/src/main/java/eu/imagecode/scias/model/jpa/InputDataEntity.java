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
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the input_data database table.
 * 
 */
@Entity
@Table(name = "input_data")
@NamedQuery(name = "InputDataEntity.findAll", query = "SELECT i FROM InputDataEntity i")
public class InputDataEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "INPUT_DATA_ID_GENERATOR", sequenceName = "INPUT_DATA_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INPUT_DATA_ID_GENERATOR")
    private Integer id;
    
    @Column(name = "local_id")
    private Integer localId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //TODO eager due to ModelMappers, think how to stay with lazy
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "analysis_id")
    private AnalysisEntity analysis;
    
    public InputDataEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocalId() {
        return this.localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }
    
    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }
    
    public ImageEntity getImage() {
        return this.image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }
    
    public ClientEntity getClient() {
        return this.client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }
    
    public AnalysisEntity getAnalysis() {
        return this.analysis;
    }
    
    public void setAnalysis(AnalysisEntity analysis) {
        this.analysis = analysis;
    }
    
}