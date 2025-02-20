package com.viplogistics.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

/**
 * {@code Permissions} - Represents the permissions assigned to a role in the system.
 * A permission defines the specific actions a user can perform based on their assigned role and privileges.
 * This entity maps to the permissions granted to a role through associated privileges.
 */
@Entity
@Data
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userPermission;
    @ManyToOne
    @JsonBackReference
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "privilege_id")
    private Privilege privilege;
}
