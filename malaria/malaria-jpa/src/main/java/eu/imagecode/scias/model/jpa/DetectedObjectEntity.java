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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "detected_object")
public class DetectedObjectEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "DETECTED_OBJECT_ID_GENERATOR", sequenceName = "DETECTED_OBJECT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETECTED_OBJECT_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    /*@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private ImageEntity image;*/
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id")
    private CellEntity cell;
    
    @Column(name = "class_id")
    private Integer classId;
    
    @Column(name = "x")
    private Integer x;
    
    @Column(name = "y")
    private Integer y;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "resolved")
    private Boolean resolved;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolved_by_id")
    private SciasUserEntity resolvedBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "resolved_time", length = 29)
    private Date resolvedTime;

    public DetectedObjectEntity() {
    }

    public DetectedObjectEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public DetectedObjectEntity(int id, CellEntity cell, SciasUserEntity sciasUser,
                    int localId, Integer classId, Integer x, Integer y, Integer width, Integer height, Boolean resolved, Date resolvedTime) {
        this.id = id;
        this.cell = cell;
        this.localId = localId;
        this.classId = classId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.resolved = resolved;
        this.resolvedBy = sciasUser;
        this.resolvedTime = resolvedTime;
    }

    public int getId() {
        return this.id;
    }

    public CellEntity getCell() {
        return this.cell;
    }

    public void setCell(CellEntity cell) {
        this.cell = cell;
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

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((classId == null) ? 0 : classId.hashCode());
        result = prime * result + id;
        result = prime * result + localId;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        result = prime * result + ((width == null) ? 0 : width.hashCode());
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
        result = prime * result + ((resolvedBy == null) ? 0 : resolvedBy.hashCode());
        result = prime * result + ((resolvedTime == null) ? 0 : resolvedTime.hashCode());
        result = prime * result + ((cell == null) ? 0 : cell.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof DetectedObjectEntity))
            return false;
        DetectedObjectEntity other = (DetectedObjectEntity) obj;
        if (classId == null) {
            if (other.getClassId() != null)
                return false;
        } else if (!classId.equals(other.getClassId()))
            return false;
        if (id != other.getId())
            return false;
        if (localId != other.getLocalId())
            return false;
        if (x == null) {
            if (other.getX() != null)
                return false;
        } else if (!x.equals(other.getX()))
            return false;
        if (y == null) {
            if (other.getY() != null)
                return false;
        } else if (!y.equals(other.getY()))
            return false;
        if (width == null) {
            if (other.getWidth() != null)
                return false;
        } else if (!width.equals(other.getWidth()))
            return false;
        if (height == null) {
            if (other.getHeight() != null)
                return false;
        } else if (!height.equals(other.getHeight()))
            return false;
        if (resolved == null) {
            if (other.getResolved() != null)
                return false;
        } else if (!resolved.equals(other.getResolved()))
            return false;
        if (resolvedBy == null) {
            if (other.getResolved() != null)
                return false;
        } else if (!resolvedBy.equals(other.getResolvedBy()))
            return false;
        if (resolvedTime == null) {
            if (other.getResolvedTime() != null)
                return false;
        } else if (!resolvedTime.equals(other.getResolvedTime()))
            return false;
        if (cell == null) {
            if (other.getCell() != null)
                return false;
        } else if (!cell.equals(other.getCell()))
            return false;
        return true;
    }

}
