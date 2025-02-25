package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.service.INagpurPickupFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nagpur-pickup-freight")
@CrossOrigin("*")
public class NagpurPickupFreightBillController {

    private final INagpurPickupFreightBillService nagpurPickupFreightBillService;

    @GetMapping("/nagpur-pickup-freight")
    public ResponseEntity<?> getNagpurPickupFreightBill(@RequestParam String billNo,@RequestParam String routeName){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(nagpurPickupFreightBillService.getNagpurPickupFreightBill(billNo,routeName));
        }catch (Exception e){

            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/save-nagpur-pickup-freight-bill")
    public ResponseEntity<?> saveNagpurPickupFreightBill(@RequestBody NagpurPickupBillReport nagpurPickupBillReport) throws BillAlreadySavedException {
        return ResponseEntity.status(HttpStatus.OK).body(nagpurPickupFreightBillService.saveNagpurPickupFreightBill(nagpurPickupBillReport));
    }

    @PutMapping("/update-nagpur-pickup-freight-bill")
    public ResponseEntity<?> updateNagpurPickupFreightBill(@RequestBody NagpurPickupBillReport nagpurPickupBillReport){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(nagpurPickupFreightBillService.updateNagpurPickupFreightBill(nagpurPickupBillReport));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-nagpur-pickup-freight-bill")
    public ResponseEntity<?> deleteNagpurPickupFreightBill(@RequestParam Long freightBillReportId){
        ApiResponse<?> response = nagpurPickupFreightBillService.deleteNagpurPickupFreightBill(freightBillReportId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
