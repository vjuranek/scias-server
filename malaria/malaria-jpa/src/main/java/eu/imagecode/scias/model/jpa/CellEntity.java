package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
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

@Entity
@Table(name = "cell")
public class CellEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "CELL_ID_GENERATOR", sequenceName = "CELL_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CELL_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_set_id")
    private ResultSetEntity resultSet;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cell", cascade = CascadeType.ALL)
    private Set<DetectedObjectEntity> detectedObjects = new HashSet<DetectedObjectEntity>(0);
    
    @Column(name = "x")
    private Integer x;
    
    @Column(name = "y")
    private Integer y;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "height")
    private Integer height;

    public CellEntity() {
    }

    public CellEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public CellEntity(int id, ResultSetEntity resultSet, Set<DetectedObjectEntity> detectedObjects, int localId, Integer x, Integer y, Integer width, Integer height) {
        this.id = id;
        this.resultSet = resultSet;
        this.detectedObjects = detectedObjects;
        this.localId = localId;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return this.id;
    }

    public ResultSetEntity getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(ResultSetEntity resultSet) {
        this.resultSet = resultSet;
    }
    
    public Set<DetectedObjectEntity> getDetectedObjects() {
        return this.detectedObjects;
    }

    public void setDetectedObjects(Set<DetectedObjectEntity> detectedObjects) {
        this.detectedObjects = detectedObjects;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        result = prime * result + ((width == null) ? 0 : width.hashCode());
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + id;
        result = prime * result + localId;
        result = prime * result + ((detectedObjects == null) ? 0 : detectedObjects.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CellEntity))
            return false;
        CellEntity other = (CellEntity) obj;
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
        if (id != other.getId())
            return false;
        if (localId != other.getLocalId())
            return false;
        if (detectedObjects == null) {
            if (other.getDetectedObjects() != null)
                return false;
        } else if (!detectedObjects.equals(other.getDetectedObjects()))
            return false;
        return true;
    }
    
}
