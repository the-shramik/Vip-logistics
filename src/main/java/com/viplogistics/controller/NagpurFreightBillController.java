package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.service.INagpurFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nagpur-freight")
@CrossOrigin("*")
public class NagpurFreightBillController {

    private final INagpurFreightBillService nagpurFreightBillService;

    @GetMapping("/nagpur-freight")
    public ResponseEntity<?> getNagpurFreightBill(@RequestParam String billNo,@RequestParam String routeName){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(nagpurFreightBillService.getNagpurFreightBill(billNo,routeName));
        }catch (Exception e){
            return ResponseEntity.ok(0);
        }
    }

    @PostMapping("/save-nagpur-freight-bill")
    public ResponseEntity<?> saveNagpurFreightBill(@RequestBody NagpurBillReport nagpurBillReport){
        return ResponseEntity.status(HttpStatus.OK).body(nagpurFreightBillService.saveNagpurFreightBill(nagpurBillReport));
    }

    @PutMapping("/update-nagpur-freight-bill")
    public ResponseEntity<?> updateNagpurFreightBill(@RequestBody NagpurBillReport nagpurBillReport){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(nagpurFreightBillService.updateNagpurFreightBill(nagpurBillReport));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-nagpur-freight-bill")
    public ResponseEntity<?> deleteNagpurFreightBill(@RequestParam Long freightBillReportId){
        ApiResponse<?> response = nagpurFreightBillService.deleteNagpurFreightBill(freightBillReportId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
