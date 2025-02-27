package com.viplogistics.service.impl;

import com.viplogistics.repository.*;
import com.viplogistics.service.IFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FreightBillServiceImpl implements IFreightBillService {

    private final IChakanFreightBillRepository chakanFreightBillRepository;

    private final IMumbaiFreightBillRepository mumbaiFreightBillRepository;

    private final INagpurFreightBillRepository nagpurFreightBillRepository;

    private final INagpurPickupFreightBillRepository nagpurPickupFreightBillRepository;

    private final IRajkotFreightBillRepository rajkotFreightBillRepository;

    private final IRudrapurFreightBillRepository rudrapurFreightBillRepository;


    @Override
    public List<Object> getAllFreightBills() {

        List<Object> objects=new ArrayList<>();
        objects.addAll(chakanFreightBillRepository.findByIsVerifiedFalse());
        objects.addAll(mumbaiFreightBillRepository.findByIsVerifiedFalse());
        objects.addAll(nagpurFreightBillRepository.findByIsVerifiedFalse());
        objects.addAll(nagpurPickupFreightBillRepository.findByIsVerifiedFalse());
        objects.addAll(rajkotFreightBillRepository.findByIsVerifiedFalse());
        objects.addAll(rudrapurFreightBillRepository.findByIsVerifiedFalse());

        return objects;
    }
}
