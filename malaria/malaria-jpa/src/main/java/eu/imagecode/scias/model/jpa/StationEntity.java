package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the station database table.
 * 
 */
@Entity
@Table(name = "station")
@NamedQueries( {
    @NamedQuery(name = "StationEntity.findAll", query = "SELECT s FROM StationEntity s"),
    @NamedQuery(name = "StationEntity.findById", query = "SELECT s FROM StationEntity s WHERE s.id = :stationId"),
    @NamedQuery(name = "StationEntity.findByUuid", query = "SELECT s FROM StationEntity s WHERE s.uuid = :stationUuid")
})
public class StationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "STATION_ID_GENERATOR", sequenceName = "STATION_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATION_ID_GENERATOR")
    private Integer id;

    private String uuid;
    
    private String name;

    // bi-directional many-to-one association to StationGroupEntity
    @ManyToOne
    @JoinColumn(name = "station_group_id")
    private StationGroupEntity stationGroup;

    public StationEntity() {
    }
    
    public StationEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StationGroupEntity getStationGroup() {
        return this.stationGroup;
    }

    public void setStationGroup(StationGroupEntity stationGroup) {
        this.stationGroup = stationGroup;
    }

}