package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "image")
public class ImageEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "IMAGE_ID_GENERATOR", sequenceName = "IMAGE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "local_id", nullable = false)
    private int localId;
    
    @Column(name = "path", length = 4095)
    private String path;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "sha256")
    private String sha256;
    
    @Column(name = "width")
    private Integer width;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "pixel_size", precision = 17, scale = 17)
    private Double pixelSize;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", length = 29)
    private Date created;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "mime_type", nullable = false, length = 16)
    private MimeTypeEntity mimeType;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "image")
    private InputDataEntity inputData;
    
    //@Lob //fails with PostreSQL bytea type, see http://in.relation.to/2010/05/11/postgre-sql-and-blo-bs/ and comments under it
    @Column(name = "bytes")
    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    public ImageEntity() {
    }

    public ImageEntity(int id, int localId, MimeTypeEntity mimeType) {
        this.id = id;
        this.localId = localId;
        this.mimeType = mimeType;
    }

    public ImageEntity(int id, ClientEntity client, int localId, String path, String name, String sha256, Integer width, Integer height,
                    Double pixelSize, Date created, MimeTypeEntity mimeType, InputDataEntity inputData) {
        this.id = id;
        this.localId = localId;
        this.path = path;
        this.name = name;
        this.sha256 = sha256;
        this.width = width;
        this.height = height;
        this.pixelSize = pixelSize;
        this.created = created;
        this.mimeType = mimeType;
        this.inputData = inputData;
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

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getPixelSize() {
        return this.pixelSize;
    }

    public void setPixelSize(Double pixelSize) {
        this.pixelSize = pixelSize;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public MimeTypeEntity getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(MimeTypeEntity mimeType) {
        this.mimeType = mimeType;
    }

    public InputDataEntity getInputData() {
        return this.inputData;
    }

    public void setInputData(InputDataEntity inputData) {
        this.inputData = inputData;
    }
    
    public byte[] getBytes() {
        return bytes;
    }
    
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((height == null) ? 0 : height.hashCode());
        result = prime * result + id;
        result = prime * result + localId;
        result = prime * result + ((mimeType == null) ? 0 : mimeType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((pixelSize == null) ? 0 : pixelSize.hashCode());
        result = prime * result + ((sha256 == null) ? 0 : sha256.hashCode());
        result = prime * result + ((width == null) ? 0 : width.hashCode());
        result = prime * result + ((bytes == null) ? 0 : bytes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ImageEntity))
            return false;
        ImageEntity other = (ImageEntity) obj;
        if (created == null) {
            if (other.getCreated() != null)
                return false;
        } else if (!created.equals(other.getCreated()))
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
        if (mimeType != other.getMimeType())
            return false;
        if (name == null) {
            if (other.getName() != null)
                return false;
        } else if (!name.equals(other.getName()))
            return false;
        if (path == null) {
            if (other.getPath() != null)
                return false;
        } else if (!path.equals(other.getPath()))
            return false;
        if (pixelSize == null) {
            if (other.getPixelSize() != null)
                return false;
        } else if (!pixelSize.equals(other.getPixelSize()))
            return false;
        if (sha256 == null) {
            if (other.getSha256() != null)
                return false;
        } else if (!sha256.equals(other.getSha256()))
            return false;
        if (width == null) {
            if (other.getWidth() != null)
                return false;
        } else if (!width.equals(other.getWidth()))
            return false;
        if (bytes == null) {
            if (other.getBytes() != null)
                return false;
        } else if (!bytes.equals(other.getBytes()))
            return false;
        return true;
    }
    
}
