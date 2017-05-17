package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the scias_user database table.
 * 
 */
@Entity
@Table(name = "scias_user")
@NamedQuery(name = "SciasUserEntity.findAll", query = "SELECT s FROM SciasUserEntity s")
public class SciasUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SCIAS_USER_ID_GENERATOR", sequenceName = "SCIAS_USER_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCIAS_USER_ID_GENERATOR")
    private Integer id;

    private String password;

    private String username;

    // bi-directional many-to-many association to SciasRoleEntity
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "scias_user_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "scias_role_id") })
    private List<SciasRoleEntity> sciasRoles;

    // bi-directional many-to-one association to UnclassifiedObjectEntity
    @OneToMany(mappedBy = "resolvedBy")
    private List<UnclassifiedObjectEntity> unclassifiedObjects;

    public SciasUserEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<SciasRoleEntity> getSciasRoles() {
        return this.sciasRoles;
    }

    public void setSciasRoles(List<SciasRoleEntity> sciasRoles) {
        this.sciasRoles = sciasRoles;
    }

    public List<UnclassifiedObjectEntity> getUnclassifiedObjects() {
        return this.unclassifiedObjects;
    }

    public void setUnclassifiedObjects(List<UnclassifiedObjectEntity> unclassifiedObjects) {
        this.unclassifiedObjects = unclassifiedObjects;
    }

    public UnclassifiedObjectEntity addUnclassifiedObject(UnclassifiedObjectEntity unclassifiedObject) {
        getUnclassifiedObjects().add(unclassifiedObject);
        unclassifiedObject.setResolvedBy(this);

        return unclassifiedObject;
    }

    public UnclassifiedObjectEntity removeUnclassifiedObject(UnclassifiedObjectEntity unclassifiedObject) {
        getUnclassifiedObjects().remove(unclassifiedObject);
        unclassifiedObject.setResolvedBy(null);

        return unclassifiedObject;
    }

}