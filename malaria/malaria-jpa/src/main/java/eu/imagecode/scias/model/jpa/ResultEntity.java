package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

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

@Entity
@Table(name = "result")
public class ResultEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "RESULT_ID_GENERATOR", sequenceName = "RESULT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_set_id")
    private ResultSetEntity resultSet;
    
    @Column(name = "class_id")
    private Integer classId;
    
    @Column(name = "amount", precision = 17, scale = 17)
    private Double amount;

    public ResultEntity() {
    }

    public ResultEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }

    public ResultEntity(int id, ResultSetEntity resultSet, int localId, Integer classId, Double amount) {
        this.id = id;
        this.resultSet = resultSet;
        this.localId = localId;
        this.classId = classId;
        this.amount = amount;
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

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
