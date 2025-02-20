package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Vehicle;
import com.viplogistics.service.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * VehicleController - Handles API requests for vehicles.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
@CrossOrigin("*")
public class VehicleController {

    private final IVehicleService vehicleService;

    /**
     * Adds a new vehicle.
     *
     * @param vehicle The vehicle details to be added.
     * @return {@link ResponseEntity} containing the added vehicle details.
     */
    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.addVehicle(vehicle));
    }

    /**
     * Retrieves all vehicles.
     *
     * @return {@link ResponseEntity} containing a list of all vehicles.
     */
    @GetMapping("/get-vehicles")
    public ResponseEntity<?> getAllVehicles(){
       return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getAllVehicles());
    }

    /**
     * Updates an existing vehicle.
     *
     * @param vehicle The updated vehicle details.
     * @return {@link ResponseEntity} containing the updated vehicle details.
     */
    @PutMapping("/update-vehicle")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(vehicleService.updateVehicle(vehicle));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes a vehicle by its ID.
     *
     * @param vehicleId The ID of the vehicle to be deleted.
     * @return {@link ResponseEntity} containing the success or failure response.
     */
    @DeleteMapping("/delete-vehicle")
    public ResponseEntity<?> deleteVehicle(@RequestParam String vehicleId){
        ApiResponse<?> response = vehicleService.deleteVehicle(vehicleId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Retrieves a vehicle by its vehicle number.
     *
     * @param vehicleNumber The vehicle number to search for.
     * @return {@link ResponseEntity} containing the vehicle details.
     */
    @GetMapping("/get-vehicle-by-vehicle-number")
    public ResponseEntity<?> getVehicleByVehicleNumber(@RequestParam String vehicleNumber){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicleByVehicleNumber(vehicleNumber));
    }

    /**
     * Retrieves the total count of vehicles.
     *
     * @return {@link ResponseEntity} containing the vehicle count.
     */
    @GetMapping("/get-vehicle-count")
    public ResponseEntity<?> getVehicleCount(){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getVehicleCount());
    }

    /**
     * Retrieves the latest vehicle ID.
     *
     * @return {@link ResponseEntity} containing the latest vehicle ID.
     */
    @GetMapping("/get-latest-vehicle-id")
    public ResponseEntity<?> getLatestVehicleId(){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getLatestVehicleId());
    }

}
