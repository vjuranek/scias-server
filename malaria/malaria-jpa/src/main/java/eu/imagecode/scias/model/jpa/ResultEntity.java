package eu.imagecode.scias.model.jpa;

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

/**
 * Result generated by hbm2java
 */
@Entity
@Table(name = "result")
public class ResultEntity implements java.io.Serializable {

    private int id;
    private ResultSetEntity resultSet;
    private int localId;
    private Integer classId;
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

    @Id
    @SequenceGenerator(name = "RESULT_ID_GENERATOR", sequenceName = "RESULT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_set_id")
    public ResultSetEntity getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(ResultSetEntity resultSet) {
        this.resultSet = resultSet;
    }

    @Column(name = "local_id", nullable = false)
    public int getLocalId() {
        return this.localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    @Column(name = "class_id")
    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    @Column(name = "amount", precision = 17, scale = 17)
    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
