package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Party;
import com.viplogistics.entity.master.helper.Owner;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IOwnerRepository;
import com.viplogistics.service.IOwnerService;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements IOwnerService {


    private final IOwnerRepository ownerRepository;

    @Override
    public Owner addOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner updateOwner(Owner owner) throws ResourceNotFoundException {

        Owner existedOwner=ownerRepository.findById(owner.getOwnerId())
                .orElseThrow(()->new ResourceNotFoundException("Owner not found with id"));

        existedOwner.setOilServiceDate(owner.getOilServiceDate());
        existedOwner.setOilServiceKm(owner.getOilServiceKm());
        existedOwner.setOilServiceTargetKm(owner.getOilServiceTargetKm());
        existedOwner.setGreasePackingDate(owner.getGreasePackingDate());
        existedOwner.setGreasePackingKm(owner.getGreasePackingKm());
        existedOwner.setGreasePackingTargetKm(owner.getGreasePackingTargetKm());
        existedOwner.setInsuranceDate(owner.getInsuranceDate());
        existedOwner.setInsuranceFrom(owner.getInsuranceFrom());
        existedOwner.setInsuranceTo(owner.getInsuranceTo());
        existedOwner.setInsuranceNextDate(owner.getInsuranceNextDate());
        existedOwner.setRtoTaxDate(owner.getRtoTaxDate());
        existedOwner.setRtoTaxFrom(owner.getRtoTaxFrom());
        existedOwner.setRtoTaxTo(owner.getRtoTaxTo());
        existedOwner.setRtoTaxNextDate(owner.getRtoTaxNextDate());
        existedOwner.setFitnessDate(owner.getFitnessDate());
        existedOwner.setFitnessFrom(owner.getFitnessFrom());
        existedOwner.setFitnessTo(owner.getFitnessTo());
        existedOwner.setFitnessNextDate(owner.getFitnessNextDate());
        existedOwner.setPermitDate(owner.getPermitDate());
        existedOwner.setPermitFrom(owner.getPermitFrom());
        existedOwner.setPermitTo(owner.getPermitTo());
        existedOwner.setPermitNextDate(owner.getPermitNextDate());
        existedOwner.setVehicle(owner.getVehicle());

        return ownerRepository.save(owner);
    }

    @Override
    public ApiResponse<?> deleteOwner(Long ownerId) {
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);

        if(optionalOwner.isPresent()){
            Owner owner=optionalOwner.get();
            ownerRepository.delete(owner);
            System.out.println("Owner going to delete............... : " + owner.getOwnerId());
            return new ApiResponse<>(true,"Party deleted successfully",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Party not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Owner getOwnerByVehicleId(String id) {
        return ownerRepository.getOwnerByVehicleVehicleId(id);
    }
}
