package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "scias_user")
public class SciasUserEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SCIAS_USER_ID_GENERATOR", sequenceName = "SCIAS_USER_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCIAS_USER_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "username", nullable = false)
    @NotNull
    private String username;
    
    @Column(name = "password", nullable = false)
    @NotNull
    private String password;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sciasUser")
    @NotNull
    private Set<UserRoleEntity> userRoles = new HashSet<UserRoleEntity>(0);

    public SciasUserEntity() {
    }

    public SciasUserEntity(int id) {
        this.id = id;
    }
    
    public SciasUserEntity(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public SciasUserEntity(int id, String username, String password, Set<UserRoleEntity> userRoles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
    }
    
    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRoleEntity> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((userRoles == null) ? 0 : userRoles.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof SciasUserEntity))
            return false;
        SciasUserEntity other = (SciasUserEntity) obj;
        if (id != other.id)
            return false;
        if (password == null) {
            if (other.getPassword() != null)
                return false;
        } else if (!password.equals(other.getPassword()))
            return false;
        if (userRoles == null) {
            if (other.getUserRoles() != null)
                return false;
        } else if (!userRoles.equals(other.getUserRoles()))
            return false;
        if (username == null) {
            if (other.getUsername() != null)
                return false;
        } else if (!username.equals(other.getUsername()))
            return false;
        return true;
    }

}
