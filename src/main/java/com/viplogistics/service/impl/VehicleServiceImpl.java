package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Vehicle;
import com.viplogistics.entity.transaction.helper.Memo;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IVehicleRepository;
import com.viplogistics.service.IVehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements IVehicleService {

    private final IVehicleRepository vehicleRepository;


    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        if (vehicle.getVehicleId() == null || vehicle.getVehicleId().isEmpty()) {
            String lastVehicleId = vehicleRepository.findTopByOrderByVehicleIdDesc();
            String newVehicleId = generateNewVehicleId(lastVehicleId).get("newVehicleId");
            vehicle.setVehicleId(newVehicleId);
        }

        return vehicleRepository.save(vehicle);
    }

    private Map<String, String> generateNewVehicleId(String lastVehicleId) {
        Map<String, String> result = new HashMap<>();

        if (lastVehicleId == null || lastVehicleId.isEmpty()) {
            result.put("newVehicleId", "V0001");
            return result;
        }

        int numericPart = Integer.parseInt(lastVehicleId.substring(1));
        String newVehicleId = String.format("V%04d", numericPart + 1);

        result.put("newVehicleId", newVehicleId);

        return result;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) throws ResourceNotFoundException {
        Vehicle existedVehicle=vehicleRepository.findById(vehicle.getVehicleId())
                .orElseThrow(()->new ResourceNotFoundException("Vehicle not found with this id: "+vehicle.getVehicleId()));

        existedVehicle.setVehicleNumber(vehicle.getVehicleNumber());
        existedVehicle.setVehicleType(vehicle.getVehicleType());
        existedVehicle.setOwner(vehicle.getOwner());
        existedVehicle.setLicenceNo(vehicle.getLicenceNo());
        existedVehicle.setDriverName(vehicle.getDriverName());
        existedVehicle.setPermitNo(vehicle.getPermitNo());
        existedVehicle.setVehicleExpAcNo(vehicle.getVehicleExpAcNo());
        existedVehicle.setOwnerName(vehicle.getOwnerName());

        return vehicleRepository.save(existedVehicle);
    }

    @Override
    public ApiResponse<?> deleteVehicle(String vehicleId) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);

        if(optionalVehicle.isPresent()){
            Vehicle vehicle = optionalVehicle.get();
            vehicleRepository.deleteById(vehicle.getVehicleId());
            return new ApiResponse<>(true,"Vehicle deleted successfully",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Vehicle not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Vehicle getVehicleByVehicleNumber(String vehicleNumber) {
        return vehicleRepository.getByVehicleNumber(vehicleNumber);
    }

    @Override
    public Map<String, Long> getVehicleCount() {

        HashMap<String,Long> result=new HashMap<>();

        result.put("totalVehicles",vehicleRepository.count());

        return result;
    }

    @Override
    public Map<String, String> getLatestVehicleId() {
        String lastVehicleId=vehicleRepository.findTopByOrderByVehicleIdDesc();

        return generateNewVehicleId(lastVehicleId);
    }
}
