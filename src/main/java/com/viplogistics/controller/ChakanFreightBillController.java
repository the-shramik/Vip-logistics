package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.service.IChakanFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chakan-freight")
@CrossOrigin("*")
public class ChakanFreightBillController {

    private final IChakanFreightBillService chakanFreightBillService;

    @GetMapping("/chakan-freight")
    public ResponseEntity<?> getChakanFreightBill(@RequestParam String billNo,@RequestParam String routeName){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(chakanFreightBillService.getChakanFreightBill(billNo,routeName));
        }catch (Exception e){
            System.out.println(e);
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/save-chakan-freight-bill")
    public ResponseEntity<?> saveChakanFreightBill(@RequestBody ChakanBillReport chakanBillReport) throws BillAlreadySavedException {
        return ResponseEntity.status(HttpStatus.OK).body(chakanFreightBillService.saveChakanFreightBill(chakanBillReport));
    }

    @PutMapping("/update-chakan-freight-bill")
    public ResponseEntity<?> updateChakanFreightBill(@RequestBody ChakanBillReport chakanBillReport){
      try {
          return ResponseEntity.status(HttpStatus.OK).body(chakanFreightBillService.updateChakanFreightBill(chakanBillReport));
      }catch (Exception e){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
    }

    @DeleteMapping("/delete-chakan-freight-bill")
    public ResponseEntity<?> deleteChakanFreightBill(@RequestParam Long freightBillReportId){
        ApiResponse<?> response = chakanFreightBillService.deleteChakanFreightBill(freightBillReportId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/get-chakan-freight-bills")
    public ResponseEntity<?> getAllChakanFreightBills(){
          return ResponseEntity.status(HttpStatus.OK).body(chakanFreightBillService.getAllChakanFreightBills());
    }

    @GetMapping("/get-chakan-freight-by-bill-no")
    public ResponseEntity<?> getChakanFreightByBillNo(@RequestParam String billNo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(chakanFreightBillService.getChakanFreightByBillNo(billNo));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }

    @GetMapping("/get-chakan-requested-freight-bills")
    public ResponseEntity<?> getAllChakanRequestedFreightBills(){
        return ResponseEntity.status(HttpStatus.OK).body(chakanFreightBillService.getAllChakanRequestedFreightBills());
    }
}
