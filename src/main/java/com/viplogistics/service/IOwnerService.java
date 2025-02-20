package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.helper.Owner;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;

public interface IOwnerService {

    Owner addOwner(Owner owner);

    List<Owner> getAllOwners();

    Owner updateOwner(Owner owner) throws ResourceNotFoundException;

    ApiResponse<?> deleteOwner(Long ownerId);

    Owner getOwnerByVehicleId(String id);
}
