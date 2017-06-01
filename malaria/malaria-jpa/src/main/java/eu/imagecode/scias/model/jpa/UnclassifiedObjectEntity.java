package eu.imagecode.scias.model.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "unclassified_object")
public class UnclassifiedObjectEntity implements java.io.Serializable {

    @Id
    @SequenceGenerator(name = "UNCLASSIFIED_OBJECT_ID_GENERATOR", sequenceName = "UNCLASSIFIED_OBJECT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNCLASSIFIED_OBJECT_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageEntity image;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_set_id")
    private ResultSetEntity resultSet;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by_id")
    private SciasUserEntity resolvedBy;
    
    @Column(name = "class_id")
    private Integer classId;
    
    @Column(name = "resolved")
    private Boolean resolved;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "resolved_time", length = 29)
    private Date resolvedTime;

    public UnclassifiedObjectEntity() {
    }

    public UnclassifiedObjectEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public UnclassifiedObjectEntity(int id, ImageEntity image, ResultSetEntity resultSet, SciasUserEntity sciasUser,
                    int localId, Integer classId, Boolean resolved, Date resolvedTime) {
        this.id = id;
        this.image = image;
        this.resultSet = resultSet;
        this.resolvedBy = sciasUser;
        this.localId = localId;
        this.classId = classId;
        this.resolved = resolved;
        this.resolvedTime = resolvedTime;
    }

    public int getId() {
        return this.id;
    }

    public ImageEntity getImage() {
        return this.image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }

    public ResultSetEntity getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(ResultSetEntity resultSet) {
        this.resultSet = resultSet;
    }

    public SciasUserEntity getResolvedBy() {
        return this.resolvedBy;
    }

    public void setResolvedBy(SciasUserEntity sciasUser) {
        this.resolvedBy = sciasUser;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
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

    public Date getResolvedTime() {
        return this.resolvedTime;
    }

    public void setResolvedTime(Date resolvedTime) {
        this.resolvedTime = resolvedTime;
    }

}
