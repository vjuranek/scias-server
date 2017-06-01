package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRoleEntity implements Serializable {

    @EmbeddedId
    @AttributeOverrides({
                    @AttributeOverride(name = "sciasUserId", column = @Column(name = "scias_user_id", nullable = false)),
                    @AttributeOverride(name = "sciasRoleId", column = @Column(name = "scias_role_id", nullable = false)) })
    private UserRoleId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scias_role_id", nullable = false, insertable = false, updatable = false)
    private SciasRoleEntity sciasRole;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scias_user_id", nullable = false, insertable = false, updatable = false)
    private SciasUserEntity sciasUser;

    public UserRoleEntity() {
    }

    public UserRoleEntity(UserRoleId id, SciasRoleEntity sciasRole, SciasUserEntity sciasUser) {
        this.id = id;
        this.sciasRole = sciasRole;
        this.sciasUser = sciasUser;
    }

    public UserRoleId getId() {
        return this.id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public SciasRoleEntity getSciasRole() {
        return this.sciasRole;
    }

    public void setSciasRole(SciasRoleEntity sciasRole) {
        this.sciasRole = sciasRole;
    }

    public SciasUserEntity getSciasUser() {
        return this.sciasUser;
    }

    public void setSciasUser(SciasUserEntity sciasUser) {
        this.sciasUser = sciasUser;
    }

}
