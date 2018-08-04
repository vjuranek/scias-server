package eu.imagecode.scias.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "locality")
public class LocalityEntity implements java.io.Serializable {

    
    @Id
    @SequenceGenerator(name = "LOCALITY_ID_GENERATOR", sequenceName = "LOCALITY_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCALITY_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "local_id", nullable = false)
    @NotNull
    private int localId;
    
    @Column(name = "longtitude", nullable = false, precision = 17, scale = 17)
    @NotNull
    private double longtitude;
    
    @Column(name = "latitude", nullable = false, precision = 17, scale = 17)
    @NotNull
    private double latitude;
    
    @Column(name = "altitude", nullable = false, precision = 17, scale = 17)
    @NotNull
    private double altitude;
    
    public LocalityEntity() {
    }

    public LocalityEntity(int id, int localId) {
        this.id = id;
        this.localId = localId;
    }
    
    public LocalityEntity(int id, double longtitude, double latitude, double altitude) {
        this.id = id;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.altitude = altitude;
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
    
    public double getLongtitude() {
        return this.longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(altitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + id;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + localId;
        temp = Double.doubleToLongBits(longtitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof LocalityEntity))
            return false;
        LocalityEntity other = (LocalityEntity) obj;
        if (Double.doubleToLongBits(altitude) != Double.doubleToLongBits(other.getAltitude()))
            return false;
        if (id != other.getId())
            return false;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.getLatitude()))
            return false;
        if (localId != other.getLocalId())
            return false;
        if (Double.doubleToLongBits(longtitude) != Double.doubleToLongBits(other.getLongtitude()))
            return false;
        return true;
    }
    
}
