package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.Permissions;
import com.viplogistics.entity.Privilege;
import com.viplogistics.entity.Role;
import com.viplogistics.entity.dto.RoleDto;
import com.viplogistics.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IRoleService {
    Role createRole(RoleDto roleDto);
    List<RoleDto> getAllRoles();
    List<Permissions> createPermissions(List<Permissions> permissions);
    Privilege createPrivilege(Privilege privilege);

    RoleDto getRoleByRoleName(String roleName) throws ResourceNotFoundException;

    Role updateRole(RoleDto roleDto) throws ResourceNotFoundException;

    ApiResponse<?> deleteRole(Long roleId);

    Role findById(Long roleId);
}
