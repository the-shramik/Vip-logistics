package com.viplogistics.repository;

import com.viplogistics.entity.master.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle,String> {

    Vehicle getByVehicleNumber(String vehicleNumber);

    @Query("SELECT v.vehicleId FROM vehicle_master v ORDER BY v.vehicleId DESC LIMIT 1")
    String findTopByOrderByVehicleIdDesc();
}
