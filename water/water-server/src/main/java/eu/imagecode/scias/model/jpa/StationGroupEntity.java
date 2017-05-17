package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the station_group database table.
 * 
 */
@Entity
@Table(name = "station_group")
@NamedQuery(name = "StationGroupEntity.findAll", query = "SELECT s FROM StationGroupEntity s")
public class StationGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "STATION_GROUP_ID_GENERATOR", sequenceName = "STATION_GROUP_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATION_GROUP_ID_GENERATOR")
    private Integer id;

    private String name;

    // bi-directional many-to-one association to StationEntity
    @OneToMany(mappedBy = "stationGroup")
    private List<StationEntity> stations;

    // bi-directional many-to-one association to StationGroupEntity
    @ManyToOne
    @JoinColumn(name = "parent_group_id")
    private StationGroupEntity stationGroup;

    // bi-directional many-to-one association to StationGroupEntity
    @OneToMany(mappedBy = "stationGroup")
    private List<StationGroupEntity> stationGroups;

    public StationGroupEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StationEntity> getStations() {
        return this.stations;
    }

    public void setStations(List<StationEntity> stations) {
        this.stations = stations;
    }

    public StationEntity addStation(StationEntity station) {
        getStations().add(station);
        station.setStationGroup(this);

        return station;
    }

    public StationEntity removeStation(StationEntity station) {
        getStations().remove(station);
        station.setStationGroup(null);

        return station;
    }

    public StationGroupEntity getStationGroup() {
        return this.stationGroup;
    }

    public void setStationGroup(StationGroupEntity stationGroup) {
        this.stationGroup = stationGroup;
    }

    public List<StationGroupEntity> getStationGroups() {
        return this.stationGroups;
    }

    public void setStationGroups(List<StationGroupEntity> stationGroups) {
        this.stationGroups = stationGroups;
    }

    public StationGroupEntity addStationGroup(StationGroupEntity stationGroup) {
        getStationGroups().add(stationGroup);
        stationGroup.setStationGroup(this);

        return stationGroup;
    }

    public StationGroupEntity removeStationGroup(StationGroupEntity stationGroup) {
        getStationGroups().remove(stationGroup);
        stationGroup.setStationGroup(null);

        return stationGroup;
    }

}