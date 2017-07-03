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
@NamedQueries({ 
    @NamedQuery(name = "AnalysisEntity.findAll", query = "SELECT a FROM AnalysisEntity a"),
    @NamedQuery(name = "AnalysisEntity.findById", query = "SELECT a FROM AnalysisEntity a WHERE a.id = :analysisId"),
    @NamedQuery(name = "AnalysisEntity.findByBatchId", query = "SELECT a FROM AnalysisEntity a WHERE a.sample.batch.id = :batchId"),
    @NamedQuery(name = "AnalysisEntity.findByLocalIdAndStation", query = "select a from AnalysisEntity a where a.localId = :localId and a.station.id = :stationID"),
    @NamedQuery(name = "AnalysisEntity.findByLocalIdAndStationUuid", query = "select a from AnalysisEntity a, StationEntity s where a.localId = :localId and a.station.id = s.id and s.uuid = :stationUUID")
})
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "result_set_id", unique = true)
    private ResultSetEntity resultSet;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", length = 29)
    private Date created;

    @Column(name = "algorithm_version", length = 256)
    private String algorithmVersion;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "analysis", fetch = FetchType.EAGER)
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((algorithmVersion == null) ? 0 : algorithmVersion.hashCode());
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + id;
        result = prime * result + ((inputData == null) ? 0 : inputData.hashCode());
        result = prime * result + localId;
        result = prime * result + ((resultSet == null) ? 0 : resultSet.hashCode());
        result = prime * result + ((station == null) ? 0 : station.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof AnalysisEntity))
            return false;
        AnalysisEntity other = (AnalysisEntity) obj;
        if (algorithmVersion == null) {
            if (other.getAlgorithmVersion() != null)
                return false;
        } else if (!algorithmVersion.equals(other.getAlgorithmVersion()))
            return false;
        if (created == null) {
            if (other.getCreated() != null)
                return false;
        } else if (!created.equals(other.getCreated()))
            return false;
        if (id != other.id)
            return false;
        if (inputData == null) {
            if (other.getInputData() != null)
                return false;
        } else if (!inputData.equals(other.getInputData()))
            return false;
        if (localId != other.getLocalId())
            return false;
        if (resultSet == null) {
            if (other.getResultSet() != null)
                return false;
        } else if (!resultSet.equals(other.getResultSet()))
            return false;
        if (station == null) {
            if (other.getStation() != null)
                return false;
        } else if (!station.equals(other.getStation()))
            return false;
        return true;
    }

}
