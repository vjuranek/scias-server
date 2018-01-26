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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "result_set")
public class ResultSetEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "RESULT_SET_ID_GENERATOR", sequenceName = "RESULT_SET_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_SET_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resultSet", cascade = CascadeType.ALL)
    private Set<CellEntity> cells = new HashSet<CellEntity>(0);
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "resultSet", cascade = CascadeType.ALL)
    private AnalysisEntity analysis;
    

    public ResultSetEntity() {
    }

    public ResultSetEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public ResultSetEntity(int id, int localId, Set<CellEntity> results, AnalysisEntity analysis) {
        this.id = id;
        this.localId = localId;
        this.cells = results;
        this.analysis = analysis;
    }

    public int getId() {
        return this.id;
    }

    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public Set<CellEntity> getCells() {
        return this.cells;
    }

    public void setCells(Set<CellEntity> cells) {
        this.cells = cells;
    }

    
    public AnalysisEntity getAnalysis() {
        return this.analysis;
    }

    public void setAnalysis(AnalysisEntity analysis) {
        this.analysis = analysis;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + localId;
        result = prime * result + ((cells == null) ? 0 : cells.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ResultSetEntity))
            return false;
        ResultSetEntity other = (ResultSetEntity) obj;
        if (id != other.getId())
            return false;
        if (localId != other.getLocalId())
            return false;
        if (cells == null) {
            if (other.getCells() != null)
                return false;
        } else if (!cells.equals(other.getCells()))
            return false;
        return true;
    }
    
}
