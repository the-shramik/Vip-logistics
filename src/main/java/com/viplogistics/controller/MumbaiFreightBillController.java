package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.service.IMumbaiFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mumbai-freight")
@CrossOrigin("*")
public class MumbaiFreightBillController {

    private final IMumbaiFreightBillService mumbaiFreightBillService;

    @GetMapping("/mumbai-freight")
    public ResponseEntity<?> getMumbaiFreightBill(@RequestParam String billNo,@RequestParam String routeName){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mumbaiFreightBillService.getMumbaiFreightBill(billNo,routeName));
        }catch (Exception e){
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/save-mumbai-freight-bill")
    public ResponseEntity<?> saveMumbaiFreightBill(@RequestBody MumbaiBillReport mumbaiBillReport){
        return ResponseEntity.status(HttpStatus.OK).body(mumbaiFreightBillService.saveMumbaiFreightBill(mumbaiBillReport));
    }

    @PutMapping("/update-mumbai-freight-bill")
    public ResponseEntity<?> updateMumbaiFreightBill(@RequestBody MumbaiBillReport mumbaiBillReport){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mumbaiFreightBillService.updateMumbaiFreightBill(mumbaiBillReport));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-mumbai-freight-bill")
    public ResponseEntity<?> deleteMumbaiFreightBill(@RequestParam Long freightBillReportId){
        ApiResponse<?> response = mumbaiFreightBillService.deleteMumbaiFreightBill(freightBillReportId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
