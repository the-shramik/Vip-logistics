package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.helper.Owner;
import com.viplogistics.service.IOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * OwnerController - Handles API requests for managing owners.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owner")
@CrossOrigin("*")
public class OwnerController {

    private final IOwnerService ownerService;


    /**
     * Adds a new owner.
     *
     * @param owner The owner details to be added.
     * @return {@link ResponseEntity} containing the result of the operation.
     */
    @PostMapping("/add-owner")
    public ResponseEntity<?> addOwner(@RequestBody Owner owner){
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.addOwner(owner));
    }

    /**
     * Retrieves all owners.
     *
     * @return {@link ResponseEntity} containing a list of all owners.
     */
    @GetMapping("/get-owners")
    public ResponseEntity<?> getAllOwners(){
        return ResponseEntity.status(HttpStatus.OK).body(ownerService.getAllOwners());
    }

    /**
     * Updates an existing owner's details.
     *
     * @param owner The updated owner details.
     * @return {@link ResponseEntity} with the updated owner details.
     */
    @PutMapping("/update-owner")
    public ResponseEntity<?> updateOwner(@RequestBody Owner owner){
     try {
         return ResponseEntity.status(HttpStatus.OK).body(ownerService.updateOwner(owner));
     }catch (Exception e){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
     }
    }

    /**
     * Deletes an owner by their ID.
     *
     * @param ownerId The ID of the owner to be deleted.
     * @return {@link ResponseEntity} containing the result of the delete operation.
     */
    @DeleteMapping("/delete-owner")
    public ResponseEntity<?> deleteOwner(@RequestParam Long ownerId){
        ApiResponse<?> response = ownerService.deleteOwner(ownerId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Retrieves an owner based on the vehicle ID.
     *
     * @param vehicleId The ID of the vehicle associated with the owner.
     * @return {@link ResponseEntity} containing the owner details.
     */
    @GetMapping("/get-owner-by-vehicle-id")
    public ResponseEntity<Owner> getOwnerByVehicleId(@RequestParam("vehicleId") String vehicleId){
        return ResponseEntity.ok(ownerService.getOwnerByVehicleId(vehicleId));
    }
}
