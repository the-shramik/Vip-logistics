package com.viplogistics.controller;

import com.viplogistics.entity.transaction.FreightBillReport;
import com.viplogistics.service.IFreightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * FreightBillController - Handles API requests for managing freight bill report.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/freight")
@CrossOrigin("*")
public class FreightBillController {

    private final IFreightService freightService;

    @GetMapping("/mumbai-freight")
    public ResponseEntity<?> getMumbaiFreightBill(@RequestParam String billNo){
        return ResponseEntity.status(HttpStatus.OK).body(freightService.getMumbaiFreightBill(billNo));
    }

    @GetMapping("/rajkot-freight")
    public ResponseEntity<?> getRajkotFreightBill(@RequestParam String billNo){
     return ResponseEntity.status(HttpStatus.OK).body(freightService.getRajkotFreightBill(billNo));
    }

    @GetMapping("/nagpur-pickup-freight")
    public ResponseEntity<?> getNagpurPickupFreightBill(@RequestParam String billNo){
        return ResponseEntity.status(HttpStatus.OK).body(freightService.getNagpurPickupFreightBill(billNo));
    }

    @GetMapping("/chakan-freight")
    public ResponseEntity<?> getChakanFreightBill(@RequestParam String billNo){
        return ResponseEntity.status(HttpStatus.OK).body(freightService.getChakanFreightBill(billNo));
    }

    @GetMapping("/rudrapur-freight")
    public ResponseEntity<?> getRudrapurFreightBill(@RequestParam String billNo){
        return ResponseEntity.status(HttpStatus.OK).body(freightService.getRudrapurFreightBill(billNo));
    }

    @GetMapping("/nagpur-freight")
    public ResponseEntity<?> getNagpurFreightBill(@RequestParam String billNo){
        return ResponseEntity.status(HttpStatus.OK).body(freightService.getNagpurFreightBill(billNo));
    }

    @PostMapping("/save-freight-bill")
    public ResponseEntity<?> saveFreightBill(@RequestBody FreightBillReport freightBillReport){
        return ResponseEntity.status(HttpStatus.OK).body(freightService.saveFreightBill(freightBillReport));
    }

}
