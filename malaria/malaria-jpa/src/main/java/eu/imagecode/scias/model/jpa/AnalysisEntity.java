package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "analysis", uniqueConstraints = @UniqueConstraint(columnNames = "result_set_id"))
@NamedQueries({ @NamedQuery(name = "AnalysisEntity.findAll", query = "SELECT a FROM AnalysisEntity a"),
                @NamedQuery(name = "AnalysisEntity.findById", query = "SELECT a FROM AnalysisEntity a WHERE a.id = :analysisId") })
public class AnalysisEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "ANALYSIS_ID_GENERATOR", sequenceName = "ANALYSIS_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANALYSIS_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "local_id", nullable = false)
    private int localId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sample_id", nullable = false)
    private SampleEntity sample;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "result_set_id", unique = true)
    private ResultSetEntity resultSet;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", length = 29)
    private Date created;

    @Column(name = "algorithm_version", length = 256)
    private String algorithmVersion;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "analysis")
    private InputDataEntity inputData = new InputDataEntity();

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    public AnalysisEntity() {
    }

    public AnalysisEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public AnalysisEntity(int id, SampleEntity sample, ResultSetEntity resultSet, int localId, Date created,
                    String algorithmVersion, InputDataEntity inputDatas) {
        this.id = id;
        this.sample = sample;
        this.resultSet = resultSet;
        this.localId = localId;
        this.created = created;
        this.algorithmVersion = algorithmVersion;
        this.inputData = inputDatas;
    }

    public int getId() {
        return this.id;
    }

    public SampleEntity getSample() {
        return this.sample;
    }

    public void setSample(SampleEntity sample) {
        this.sample = sample;
    }

    public ResultSetEntity getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(ResultSetEntity resultSet) {
        this.resultSet = resultSet;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAlgorithmVersion() {
        return this.algorithmVersion;
    }

    public void setAlgorithmVersion(String algorithmVersion) {
        this.algorithmVersion = algorithmVersion;
    }

    public InputDataEntity getInputData() {
        return this.inputData;
    }

    public void setInputData(InputDataEntity inputData) {
        this.inputData = inputData;
    }

    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AnalysisEntity))
            return false;
        return id == ((AnalysisEntity) o).id;
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
