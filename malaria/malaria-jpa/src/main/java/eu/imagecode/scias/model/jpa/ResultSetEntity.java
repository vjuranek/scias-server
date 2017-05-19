package eu.imagecode.scias.model.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ResultSet generated by hbm2java
 */
@Entity
@Table(name = "result_set")
public class ResultSetEntity implements java.io.Serializable {

    private int id;
    private int localId;
    private Set<UnclassifiedObjectEntity> unclassifiedObjects = new HashSet<UnclassifiedObjectEntity>(0);
    private Set<ResultEntity> results = new HashSet<ResultEntity>(0);
    private Set<AnalysisEntity> analysises = new HashSet<AnalysisEntity>(0);
    private StationEntity station;

    public ResultSetEntity() {
    }

    public ResultSetEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public ResultSetEntity(int id, int localId, Set<UnclassifiedObjectEntity> unclassifiedObjects,
                    Set<ResultEntity> results, Set<AnalysisEntity> analysises) {
        this.id = id;
        this.localId = localId;
        this.unclassifiedObjects = unclassifiedObjects;
        this.results = results;
        this.analysises = analysises;
    }

    @Id
    @SequenceGenerator(name = "RESULT_SET_ID_GENERATOR", sequenceName = "RESULT_SET_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SET_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "local_id", nullable = false)
    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resultSet")
    public Set<UnclassifiedObjectEntity> getUnclassifiedObjects() {
        return this.unclassifiedObjects;
    }

    public void setUnclassifiedObjects(Set<UnclassifiedObjectEntity> unclassifiedObjects) {
        this.unclassifiedObjects = unclassifiedObjects;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resultSet")
    public Set<ResultEntity> getResults() {
        return this.results;
    }

    public void setResults(Set<ResultEntity> results) {
        this.results = results;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resultSet")
    public Set<AnalysisEntity> getAnalysises() {
        return this.analysises;
    }

    public void setAnalysises(Set<AnalysisEntity> analysises) {
        this.analysises = analysises;
    }
    
    @ManyToOne
    @JoinColumn(name = "station_id")
    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

}
