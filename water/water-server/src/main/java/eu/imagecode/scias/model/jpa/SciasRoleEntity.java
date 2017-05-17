package eu.imagecode.scias.model.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the scias_role database table.
 * 
 */
@Entity
@Table(name = "scias_role")
@NamedQuery(name = "SciasRoleEntity.findAll", query = "SELECT s FROM SciasRoleEntity s")
public class SciasRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SCIAS_ROLE_ID_GENERATOR", sequenceName = "SCIAS_ROLE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCIAS_ROLE_ID_GENERATOR")
    private Integer id;

    private String description;

    private String role;

    // bi-directional many-to-many association to SciasUserEntity
    @ManyToMany(mappedBy = "sciasRoles")
    private List<SciasUserEntity> sciasUsers;

    public SciasRoleEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<SciasUserEntity> getSciasUsers() {
        return this.sciasUsers;
    }

    public void setSciasUsers(List<SciasUserEntity> sciasUsers) {
        this.sciasUsers = sciasUsers;
    }

}