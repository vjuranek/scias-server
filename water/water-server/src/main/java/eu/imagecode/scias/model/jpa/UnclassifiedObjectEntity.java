package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the unclassified_object database table.
 * 
 */
@Entity
@Table(name = "unclassified_object")
@NamedQuery(name = "UnclassifiedObjectEntity.findAll", query = "SELECT u FROM UnclassifiedObjectEntity u")
public class UnclassifiedObjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "UNCLASSIFIED_OBJECT_ID_GENERATOR", sequenceName = "UNCLASSIFIED_OBJECT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNCLASSIFIED_OBJECT_ID_GENERATOR")
    private Integer id;
    
    @Column(name = "local_id")
    private Integer localId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @Column(name = "class_id")
    private Integer classId;

    private Boolean resolved;

    @Column(name = "resolved_time")
    private Timestamp resolvedTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) //TODO eager due to ModelMappers, think how to stay with lazy
    @JoinColumn(name = "image_id")
    private ImageEntity image;
 
    @ManyToOne
    @JoinColumn(name = "resolved_by_id")
    private SciasUserEntity resolvedBy;

    public UnclassifiedObjectEntity() {
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

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Boolean getResolved() {
        return this.resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }

    public Timestamp getResolvedTime() {
        return this.resolvedTime;
    }

    public void setResolvedTime(Timestamp resolvedTime) {
        this.resolvedTime = resolvedTime;
    }

    public ImageEntity getImage() {
        return this.image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }

    public SciasUserEntity getResolvedBy() {
        return this.resolvedBy;
    }

    public void setResolvedBy(SciasUserEntity resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

}