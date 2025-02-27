package com.viplogistics.controller;

import com.viplogistics.service.IFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/freight")
@CrossOrigin("*")
public class FreightBillController {

    private final IFreightBillService freightBillService;

    @GetMapping("/get-all-freight-bills")
    public ResponseEntity<?> getAllFreightBills(){
        return ResponseEntity.status(HttpStatus.OK).body(freightBillService.getAllFreightBills());
    }
}
