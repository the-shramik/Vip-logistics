package com.viplogistics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@code Privilege} - Represents a set of permissions associated with a specific action or resource.
 * Each privilege grants certain actions (read, delete, update, write) on resources, which can be assigned to roles
 * via the {@link Permissions} entity.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privilegeId;

    private String readPermission;

    private String deletePermission;

    private String updatePermission;

    private String writePermission;

    @OneToOne(mappedBy = "privilege")
    @JsonIgnore
    private Permissions permission;

}
