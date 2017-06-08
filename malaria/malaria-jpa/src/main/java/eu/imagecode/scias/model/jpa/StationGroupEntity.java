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

    public StationGroupEntity(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((stationGroup == null) ? 0 : stationGroup.hashCode());
        result = prime * result + ((stationGroups == null) ? 0 : stationGroups.hashCode());
        result = prime * result + ((stations == null) ? 0 : stations.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof StationGroupEntity))
            return false;
        StationGroupEntity other = (StationGroupEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        if (name == null) {
            if (other.getName() != null)
                return false;
        } else if (!name.equals(other.getName()))
            return false;
        if (stationGroup == null) {
            if (other.getStationGroup() != null)
                return false;
        } else if (!stationGroup.equals(other.getStationGroup()))
            return false;
        if (stationGroups == null) {
            if (other.getStationGroups() != null)
                return false;
        } else if (!stationGroups.equals(other.getStationGroups()))
            return false;
        if (stations == null) {
            if (other.getStations() != null)
                return false;
        } else if (!stations.equals(other.getStations()))
            return false;
        return true;
    }

}