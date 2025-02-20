package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Vehicle;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface IVehicleService {

     Vehicle addVehicle(Vehicle vehicle);

     List<Vehicle> getAllVehicles();

     Vehicle updateVehicle(Vehicle vehicle) throws ResourceNotFoundException;

     ApiResponse<?> deleteVehicle(String vehicleId);

     Vehicle getVehicleByVehicleNumber(String vehicleNumber);

     Map<String,Long> getVehicleCount();

     Map<String, String> getLatestVehicleId();
}
