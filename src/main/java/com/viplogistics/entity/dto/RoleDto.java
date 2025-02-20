package com.viplogistics.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * RoleDto - Represents a user role in the system.
 *
 * This DTO contains details about a role, including its ID, name, description,
 * and associated permissions.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long roleId;
    private String roleName;
    private String roleDescription;
    private List<PermissionDto> permissions;
}
