package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.entity.transaction.RudrapurBillReport;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.service.IRudrapurFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rudrapur-freight")
@CrossOrigin("*")
public class RudrapurFreightBillController {

    private IRudrapurFreightBillService rudrapurFreightBillService;

    @GetMapping("/rudrapur-freight")
    public ResponseEntity<?> getRudrapurFreightBill(@RequestParam String billNo,@RequestParam String routeName){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rudrapurFreightBillService.getRudrapurFreightBill(billNo,routeName));
        }catch (Exception e){
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/save-rudrapur-freight-bill")
    public ResponseEntity<?> saveRudrapurFreightBill(@RequestBody RudrapurBillReport rudrapurBillReport) throws BillAlreadySavedException {
        return ResponseEntity.status(HttpStatus.OK).body(rudrapurFreightBillService.saveRudrapurFreightBill(rudrapurBillReport));
    }

    @PutMapping("/update-rudrapur-freight-bill")
    public ResponseEntity<?> updateRudrapurFreightBill(@RequestBody RudrapurBillReport rudrapurBillReport){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rudrapurFreightBillService.updateRudrapurFreightBill(rudrapurBillReport));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-rudrapur-freight-bill")
    public ResponseEntity<?> deleteRudrapurFreightBill(@RequestParam Long freightBillReportId){
        ApiResponse<?> response = rudrapurFreightBillService.deleteRudrapurFreightBill(freightBillReportId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
