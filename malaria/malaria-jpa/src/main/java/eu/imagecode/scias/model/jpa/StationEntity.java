package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the station database table.
 * 
 */
@Entity
@Table(name = "station")
@NamedQueries( {
    @NamedQuery(name = StationEntity.QUERY_FIND_ALL, query = "SELECT s FROM StationEntity s"),
    @NamedQuery(name = StationEntity.QUERY_FIND_BY_ID, query = "SELECT s FROM StationEntity s WHERE s.id = :stationId"),
    @NamedQuery(name = StationEntity.QUERY_FIND_BY_UUID, query = "SELECT s FROM StationEntity s WHERE s.uuid = :stationUuid")
})
public class StationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String QUERY_FIND_ALL = "StationEntity.findAll";
    public static final String QUERY_FIND_BY_ID = "StationEntity.findById";
    public static final String QUERY_FIND_BY_UUID = "StationEntity.findByUuid";

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((stationGroup == null) ? 0 : stationGroup.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof StationEntity))
            return false;
        StationEntity other = (StationEntity) obj;
        if (id == null) {
            if (other.getId() != null)
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
        if (uuid == null) {
            if (other.getUuid() != null)
                return false;
        } else if (!uuid.equals(other.getUuid()))
            return false;
        return true;
    }

}