package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the image database table.
 * 
 */
@Entity
@Table(name = "image")
@NamedQuery(name = "ImageEntity.findAll", query = "SELECT i FROM ImageEntity i")
public class ImageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "IMAGE_ID_GENERATOR", sequenceName = "IMAGE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_ID_GENERATOR")
    private Integer id;
    
    @Column(name = "local_id")
    private Integer localId;

    private Timestamp created;

    private Integer height;

    private String name;

    private String path;

    @Column(name = "pixel_size")
    private double pixelSize;

    private String sha256;

    private Integer width;

    @Enumerated(EnumType.STRING)
    @Column(name = "mime_type", nullable = false)
    private MimeTypeEntity mimeType;

    public ImageEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getLocalId() {
        return localId;
    }
    
    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getPixelSize() {
        return this.pixelSize;
    }

    public void setPixelSize(double pixelSize) {
        this.pixelSize = pixelSize;
    }

    public String getSha256() {
        return this.sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public MimeTypeEntity getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeTypeEntity mimeType) {
        this.mimeType = mimeType;
    }

}