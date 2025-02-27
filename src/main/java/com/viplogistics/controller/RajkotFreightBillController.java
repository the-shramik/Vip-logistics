package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.exception.BillAlreadySavedException;
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

    private final IRajkotFreightBillService rajkotFreightBillService;
    @GetMapping("/rajkot-freight")
    public ResponseEntity<?> getRajkotFreightBill(@RequestParam String billNo,@RequestParam String routeName){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rajkotFreightBillService.getRajkotFreightBill(billNo,routeName));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/save-rajkot-freight-bill")
    public ResponseEntity<?> saveRajkotFreightBill(@RequestBody RajkotBillReport rajkotBillReport) throws BillAlreadySavedException {
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

    @GetMapping("/get-rajkot-freight-bills")
    public ResponseEntity<?> getAllNagpurPickupFreightBills(){
        return ResponseEntity.status(HttpStatus.OK).body(rajkotFreightBillService.getAllRajkotFreightBills());
    }

    @GetMapping("/get-rajkot-freight-by-bill-no")
    public ResponseEntity<?> getNagpurPickupRajkotFreightByBillNo(@RequestParam String billNo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(rajkotFreightBillService.getRajkotFreightByBillNo(billNo));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }

    @GetMapping("/get-rajkot-requested-freight-bills")
    public ResponseEntity<?> getAllRajkotRequestedFreightBills(){
        return ResponseEntity.status(HttpStatus.OK).body(rajkotFreightBillService.getAllRajkotRequestedFreightBills());
    }

}
