package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the analysis database table.
 * 
 */
@Entity
@Table(name = "analysis")
@NamedQueries ({
    @NamedQuery(name = "AnalysisEntity.findAll", query = "SELECT a FROM AnalysisEntity a"),
    @NamedQuery(name = "AnalysisEntity.findById", query = "SELECT a FROM AnalysisEntity a WHERE a.id = :analysisId")
})
public class AnalysisEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ANALYSIS_ID_GENERATOR", sequenceName = "ANALYSIS_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANALYSIS_ID_GENERATOR")
    private Integer id;
    
    @Column(name = "local_id")
    private Integer localId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @Column(name = "algorithm_version")
    private String algorithmVersion;

    private Timestamp created;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_id")
    private BatchEntity batch;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "analysis")
    private InputDataEntity inputData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_set_id")
    private ResultSetEntity resultSet;

    public AnalysisEntity() {
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
    
    public String getAlgorithmVersion() {
        return this.algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
    
    public BatchEntity getBatch() {
        return this.batch;
    }

    public void setBatch(BatchEntity batch) {
        this.batch = batch;
    }

    public InputDataEntity getInputData() {
        return this.inputData;
    }

    public void setInputData(InputDataEntity inputData) {
        this.inputData = inputData;
    }

    public ResultSetEntity getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(ResultSetEntity resultSet) {
        this.resultSet = resultSet;
    }

}