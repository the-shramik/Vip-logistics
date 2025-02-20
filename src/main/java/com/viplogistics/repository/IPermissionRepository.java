package com.viplogistics.repository;

import com.viplogistics.entity.Permissions;
import com.viplogistics.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPermissionRepository extends JpaRepository<Permissions, Long> {

    void deleteAllByRoleId(Long roleId);

    List<Permissions> getPermissionsByRole(Role role);
}
