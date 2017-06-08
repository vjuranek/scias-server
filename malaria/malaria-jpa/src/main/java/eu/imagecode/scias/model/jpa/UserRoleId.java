package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserRoleId implements Serializable {

    @Column(name = "scias_user_id", nullable = false)
    private int sciasUserId;
    
    @Column(name = "scias_role_id", nullable = false)
    private int sciasRoleId;

    public UserRoleId() {
    }

    public UserRoleId(int sciasUserId, int sciasRoleId) {
        this.sciasUserId = sciasUserId;
        this.sciasRoleId = sciasRoleId;
    }

    public int getSciasUserId() {
        return this.sciasUserId;
    }

    public void setSciasUserId(int sciasUserId) {
        this.sciasUserId = sciasUserId;
    }

    public int getSciasRoleId() {
        return this.sciasRoleId;
    }

    public void setSciasRoleId(int sciasRoleId) {
        this.sciasRoleId = sciasRoleId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + sciasRoleId;
        result = prime * result + sciasUserId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof UserRoleId))
            return false;
        UserRoleId other = (UserRoleId) obj;
        if (sciasRoleId != other.getSciasRoleId())
            return false;
        if (sciasUserId != other.getSciasUserId())
            return false;
        return true;
    }

}
