package com.viplogistics.repository;

import com.viplogistics.entity.master.helper.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner,Long> {
    Owner getOwnerByVehicleVehicleId(String id);
}
