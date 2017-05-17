package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the result_set database table.
 * 
 */
@Entity
@Table(name = "result_set")
@NamedQuery(name = "ResultSetEntity.findAll", query = "SELECT r FROM ResultSetEntity r")
public class ResultSetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RESULT_SET_ID_GENERATOR", sequenceName = "RESULT_SET_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SET_ID_GENERATOR")
    private Integer id;
    
    @Column(name = "local_id")
    private Integer localId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //TODO eager due to ModelMappers, think how to stay with lazy
    @JoinColumn(name = "result_set_id")
    private Set<ResultEntity> results;  //needs to be Set, otherwise fails with org.hibernate.loader.MultipleBagFetchException - once lazy, can be moved back to list (but probably better to keep set)

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //TODO eager due to ModelMappers, think how to stay with lazy
    @JoinColumn(name = "result_set_id")
    private Set<UnclassifiedObjectEntity> unclassifiedObjects; //needs to be Set, otherwise fails with org.hibernate.loader.MultipleBagFetchException - once lazy, can be moved back to list (but probably better to keep set)

    public ResultSetEntity() {
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

    public Set<ResultEntity> getResults() {
        return this.results;
    }

    public void setResults(Set<ResultEntity> results) {
        this.results = results;
    }

    public Set<UnclassifiedObjectEntity> getUnclassifiedObjects() {
        return this.unclassifiedObjects;
    }

    public void setUnclassifiedObjects(Set<UnclassifiedObjectEntity> unclassifiedObjects) {
        this.unclassifiedObjects = unclassifiedObjects;
    }

    public UnclassifiedObjectEntity addUnclassifiedObject(UnclassifiedObjectEntity unclassifiedObject) {
        getUnclassifiedObjects().add(unclassifiedObject);
        return unclassifiedObject;
    }

    public UnclassifiedObjectEntity removeUnclassifiedObject(UnclassifiedObjectEntity unclassifiedObject) {
        getUnclassifiedObjects().remove(unclassifiedObject);
        return unclassifiedObject;
    }

}