package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the result database table.
 * 
 */
@Entity
@Table(name = "result")
@NamedQuery(name = "ResultEntity.findAll", query = "SELECT r FROM ResultEntity r")
public class ResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RESULT_ID_GENERATOR", sequenceName = "RESULT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULT_ID_GENERATOR")
    private Integer id;

    @Column(name = "local_id")
    private Integer localId;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;
    
    private double amount;

    @Column(name = "class_id")
    private Integer classId;

    public ResultEntity() {
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
    
    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

}