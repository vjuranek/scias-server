package eu.imagecode.scias.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "scias_role")
public class SciasRoleEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SCIAS_ROLE_ID_GENERATOR", sequenceName = "SCIAS_ROLE_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCIAS_ROLE_ID_GENERATOR")
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "role", nullable = false, length = 32)
    private String role;
    
    @Column(name = "description", length = 256)
    private String description;

    public SciasRoleEntity() {
    }

    public SciasRoleEntity(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public SciasRoleEntity(int id, String role, String description) {
        this.id = id;
        this.role = role;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
