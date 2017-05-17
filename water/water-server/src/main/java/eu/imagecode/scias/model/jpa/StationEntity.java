package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

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

    // bi-directional many-to-one association to SubjectEntity
    @OneToMany(mappedBy = "station")
    private List<SubjectEntity> subjects;

    public StationEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<SubjectEntity> getSubjects() {
        return this.subjects;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public SubjectEntity addSubject(SubjectEntity subject) {
        getSubjects().add(subject);
        subject.setStation(this);

        return subject;
    }

    public SubjectEntity removeSubject(SubjectEntity subject) {
        getSubjects().remove(subject);
        subject.setStation(null);

        return subject;
    }

}