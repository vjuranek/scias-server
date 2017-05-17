package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the subject database table.
 * 
 */
@Entity
@Table(name = "subject")
@NamedQuery(name = "SubjectEntity.findAll", query = "SELECT s FROM SubjectEntity s")
public class SubjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SUBJECT_ID_GENERATOR", sequenceName = "SUBJECT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBJECT_ID_GENERATOR")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "batch_id")
    private BatchEntity batch;

    // bi-directional many-to-one association to StationEntity
    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    public SubjectEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BatchEntity getBatch() {
        return this.batch;
    }

    public void setBatch(BatchEntity batch) {
        this.batch = batch;
    }

    public StationEntity getStation() {
        return this.station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

}