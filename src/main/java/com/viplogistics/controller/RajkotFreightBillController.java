package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.service.IRajkotFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rajkot-freight")
@CrossOrigin("*")
public class RajkotFreightBillController {

    private IRajkotFreightBillService rajkotFreightBillService;
    @GetMapping("/rajkot-freight")
    public ResponseEntity<?> getRajkotFreightBill(@RequestParam String billNo,@RequestParam String routeName){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rajkotFreightBillService.getRajkotFreightBill(billNo,routeName));
        }catch (Exception e){
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/save-rajkot-freight-bill")
    public ResponseEntity<?> saveRajkotFreightBill(@RequestBody RajkotBillReport rajkotBillReport){
        return ResponseEntity.status(HttpStatus.OK).body(rajkotFreightBillService.saveRajkotFreightBill(rajkotBillReport));
    }

    @PutMapping("/update-rajkot-freight-bill")
    public ResponseEntity<?> updateRajkotFreightBill(@RequestBody RajkotBillReport rajkotBillReport){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rajkotFreightBillService.updateRajkotFreightBill(rajkotBillReport));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-rajkot-freight-bill")
    public ResponseEntity<?> deleteRajkotFreightBill(@RequestParam Long freightBillReportId){
        ApiResponse<?> response = rajkotFreightBillService.deleteRajkotFreightBill(freightBillReportId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
