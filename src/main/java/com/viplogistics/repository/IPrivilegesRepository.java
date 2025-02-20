package com.viplogistics.repository;

import com.viplogistics.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrivilegesRepository extends JpaRepository<Privilege,Long> {
}
