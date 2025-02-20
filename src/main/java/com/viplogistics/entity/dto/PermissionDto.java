package com.viplogistics.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * PermissionDto - Represents a permission assigned to a user role.
 *
 * This DTO contains details about user permissions and their associated privileges.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

    private String userPermission;

    private List<String> privileges;
}
