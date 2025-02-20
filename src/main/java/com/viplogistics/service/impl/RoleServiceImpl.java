package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.Permissions;
import com.viplogistics.entity.Privilege;
import com.viplogistics.entity.Role;
import com.viplogistics.entity.dto.PermissionDto;
import com.viplogistics.entity.dto.RoleDto;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IPermissionRepository;
import com.viplogistics.repository.IPrivilegesRepository;
import com.viplogistics.repository.IRoleRepository;
import com.viplogistics.service.IRoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.collection.spi.PersistentBag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    private final IPermissionRepository permissionRepository;

    private final IPrivilegesRepository privilegesRepository;

    @Override
    public Role createRole(RoleDto roleDto) {

        List<String> validPrivilegesList = List.of("READ", "WRITE", "UPDATE", "DELETE");

        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        role.setRoleDescription(roleDto.getRoleDescription());

        List<Permissions> permissions = new ArrayList<>();

        for (PermissionDto permissionDto : roleDto.getPermissions()) {
            Permissions permission = new Permissions();
            permission.setUserPermission(permissionDto.getUserPermission());

            Privilege privilege = new Privilege();

            for (String prg : permissionDto.getPrivileges()) {
                if (validPrivilegesList.contains(prg)) {
                    if (prg.equals("READ")) {
                        privilege.setReadPermission("READ");
                    } else if (prg.equals("WRITE")) {
                        privilege.setWritePermission("WRITE");
                    } else if (prg.equals("UPDATE")) {
                        privilege.setUpdatePermission("UPDATE");
                    } else if (prg.equals("DELETE")) {
                        privilege.setDeletePermission("DELETE");
                    }
                }
            }

            permission.setPrivilege(privilege);

            permissions.add(permission);
        }

        role.setPermissions(permissions);

        return roleRepository.save(role);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<RoleDto> roleDtos=new ArrayList<>();

        for(Role role:roleRepository.findAll()){
            RoleDto roleDto=new RoleDto();
            roleDto.setRoleId(role.getId());
            roleDto.setRoleName(role.getRoleName());
            roleDto.setRoleDescription(role.getRoleDescription());

            List<PermissionDto> permissionDtos=new ArrayList<>();
            for(Permissions permission:role.getPermissions()){
                PermissionDto permissionDto=new PermissionDto();
                List<String> privileges=new ArrayList<>();
                permissionDto.setUserPermission(permission.getUserPermission());
                privileges.add(permission.getPrivilege().getReadPermission());
                privileges.add(permission.getPrivilege().getWritePermission());
                privileges.add(permission.getPrivilege().getUpdatePermission());
                privileges.add(permission.getPrivilege().getDeletePermission());

                permissionDto.setPrivileges(privileges);
                permissionDtos.add(permissionDto);
            }

            roleDto.setPermissions(permissionDtos);
            roleDtos.add(roleDto);
        }

        return roleDtos;
    }

    @Override
    public List<Permissions> createPermissions(List<Permissions> permissions) {
        return permissionRepository.saveAll(permissions);
    }

    @Override
    public Privilege createPrivilege(Privilege privilege) {
        return privilegesRepository.save(privilege);
    }

    @Override
    public RoleDto getRoleByRoleName(String roleName) throws ResourceNotFoundException {
        Role role=roleRepository.findByRoleName(roleName)
                .orElseThrow(()->new ResourceNotFoundException("Role Not found with the role name"));

        RoleDto roleDto=new RoleDto();

        roleDto.setRoleId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setRoleDescription(role.getRoleDescription());

        List<PermissionDto> permissionDtos=new ArrayList<>();
        for(Permissions permission:role.getPermissions()){
            PermissionDto permissionDto=new PermissionDto();
            List<String> privileges=new ArrayList<>();
            permissionDto.setUserPermission(permission.getUserPermission());
            privileges.add(permission.getPrivilege().getReadPermission());
            privileges.add(permission.getPrivilege().getWritePermission());
            privileges.add(permission.getPrivilege().getUpdatePermission());
            privileges.add(permission.getPrivilege().getDeletePermission());

            permissionDto.setPrivileges(privileges);
            permissionDtos.add(permissionDto);
        }

        roleDto.setPermissions(permissionDtos);
        return roleDto;
    }

    @Override
    public Role updateRole(RoleDto roleDto) throws ResourceNotFoundException {

        List<String> validPrivilegesList = List.of("READ", "WRITE", "UPDATE", "DELETE");

        Role existedRole=roleRepository.findById(roleDto.getRoleId())
                .orElseThrow(()->new ResourceNotFoundException("Role not found by id "+roleDto.getRoleId()));

        existedRole.setRoleName(roleDto.getRoleName());
        existedRole.setRoleDescription(roleDto.getRoleDescription());

        if (existedRole.getPermissions() != null) {
            existedRole.getPermissions().clear();

            List<Permissions> permissions=new ArrayList<>();
            for (PermissionDto prms:roleDto.getPermissions()){
                Permissions permission=new Permissions();
                permission.setUserPermission(prms.getUserPermission());

                Privilege privilege = new Privilege();

                for (String prg : prms.getPrivileges()) {
                    if (validPrivilegesList.contains(prg)) {
                        if (prg.equals("READ")) {
                            privilege.setReadPermission("READ");
                        } else if (prg.equals("WRITE")) {
                            privilege.setWritePermission("WRITE");
                        } else if (prg.equals("UPDATE")) {
                            privilege.setUpdatePermission("UPDATE");
                        } else if (prg.equals("DELETE")) {
                            privilege.setDeletePermission("DELETE");
                        }
                    }
                }
                permission.setPrivilege(privilege);

                permissions.add(permission);

            }
            existedRole.getPermissions().addAll(permissions);
        } else {
            List<Permissions> permissions=new ArrayList<>();
            for (PermissionDto prms:roleDto.getPermissions()){
                Permissions permission=new Permissions();
                permission.setUserPermission(prms.getUserPermission());

                Privilege privilege = new Privilege();

                for (String prg : prms.getPrivileges()) {
                    if (validPrivilegesList.contains(prg)) {
                        if (prg.equals("READ")) {
                            privilege.setReadPermission("READ");
                        } else if (prg.equals("WRITE")) {
                            privilege.setWritePermission("WRITE");
                        } else if (prg.equals("UPDATE")) {
                            privilege.setUpdatePermission("UPDATE");
                        } else if (prg.equals("DELETE")) {
                            privilege.setDeletePermission("DELETE");
                        }
                    }
                }
                permission.setPrivilege(privilege);

                permissions.add(permission);

            }
            existedRole.setPermissions(permissions);
        }

        return roleRepository.save(existedRole);
    }

    @Override
    public ApiResponse<?> deleteRole(Long roleId) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if(optionalRole.isPresent()){
            Role role=optionalRole.get();
            roleRepository.delete(role);
            return new ApiResponse<>(true,"Role deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Role not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).get();
    }
}
