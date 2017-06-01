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

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof UserRoleId))
            return false;
        UserRoleId castOther = (UserRoleId) other;

        return (this.getSciasUserId() == castOther.getSciasUserId())
                        && (this.getSciasRoleId() == castOther.getSciasRoleId());
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getSciasUserId();
        result = 37 * result + this.getSciasRoleId();
        return result;
    }

}
