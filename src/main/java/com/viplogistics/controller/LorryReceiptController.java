package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.helper.Bill;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.service.ILorryReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * LorryReceiptController - Handles API requests for managing lorry receipt.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lorry-receipt")
@CrossOrigin("*")
public class LorryReceiptController {

    private final ILorryReceiptService lorryReceiptService;

    /**
     * Retrieves the latest Lorry Receipt (LR) number.
     *
     * @return A {@link ResponseEntity} containing the latest LR number.
     *         Returns HTTP 200 OK with the LR number if successful.
     */
    @GetMapping("/get-latest-lr-no")
    public ResponseEntity<?> getLatestLrNo(){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLatestLrNo());
    }

    /**
     * Adds a new Lorry Receipt (LR).
     *
     * @param lorryReceipt The {@link LorryReceipt} object to be added.
     * @return A {@link ResponseEntity} containing the added Lorry Receipt.
     *         Returns HTTP 200 OK if successful.
     */
    @PostMapping("/add-lorry-receipt")
    public ResponseEntity<?> addLorryReceipt(@RequestBody LorryReceipt lorryReceipt){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.addLorryReceipt(lorryReceipt));
    }

    /**
     * Retrieves all lorry receipts from the system.
     *
     * @return {@link ResponseEntity} containing a list of all {@link LorryReceipt}.
     *         - Returns HTTP 200 (OK) with the list if records are found.
     *         - Returns HTTP 204 (No Content) if no records exist.
     */
    @GetMapping("/get-lorry-receipts")
    public ResponseEntity<?> getAllLorryReceipts(){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getAllLorryReceipts());
    }

    /**
     * Deletes a lorry receipt by its ID.
     *
     * @param lrId The ID of the lorry receipt to be deleted.
     * @return {@link ResponseEntity} containing an {@link ApiResponse}:
     *         - Returns HTTP 200 (OK) if the deletion is successful.
     *         - Returns HTTP 404 (Not Found) if the receipt does not exist.
     * @throws ResourceNotFoundException if the specified lorry receipt is not found.
     */
    @DeleteMapping("/delete-lorry-receipt")
    public ResponseEntity<?> deleteLorryReceipt(@RequestParam Long lrId) throws ResourceNotFoundException {
        ApiResponse<?> response = lorryReceiptService.deleteLorryReceipt(lrId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Updates an existing lorry receipt.
     *
     * @param lorryReceipt The updated LorryReceipt object.
     * @return {@link ResponseEntity} containing:
     *         - HTTP 200 (OK) with the updated {@link LorryReceipt} if successful.
     *         - HTTP 404 (Not Found) if the update fails.
     */
    @PutMapping("/update-lorry-receipt")
    public ResponseEntity<?> updateLorryReceipt(@RequestBody LorryReceipt lorryReceipt){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.updateLorryReceipt(lorryReceipt));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Updates the status of a lorry receipt.
     *
     * @param lrId   The ID of the lorry receipt to be updated.
     * @param status The new status of the lorry receipt (true for active, false for inactive).
     * @return {@link ResponseEntity} containing:
     *         - HTTP 200 (OK) with the updated status if successful.
     *         - HTTP 404 (Not Found) if the lorry receipt is not found or an error occurs.
     */
    @PutMapping("/update-lorry-status")
    public ResponseEntity<?> updateLorryStatus(@RequestParam Long lrId,@RequestParam Boolean status){
       try {
           return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.updateLorryStatus(lrId,status));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }

    /**
     * Checks if a memo exists by its memo number.
     *
     * @param memoNo The memo number to check.
     * @return {@link ResponseEntity} with true if the memo exists, false otherwise.
     */
    @GetMapping("/check-memo-exists")
    public ResponseEntity<?> checkMemoExists(@RequestParam String memoNo){
          try {
              return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.checkMemoExists(memoNo));
          }catch (Exception e){
              return ResponseEntity.ok(0);
          }
    }

    /**
     * Retrieves the last memo number.
     *
     * @return {@link ResponseEntity} containing the last memo number.
     */
    @GetMapping("/get-last-memo-no")
    public ResponseEntity<?> getLastMemoNo(){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLastMemoNo());
    }

    /**
     * Gets a summary of lorry receipts between a start and end date.
     *
     * @param startDate The start date in YYYY-MM-DD format.
     * @param endDate   The end date in YYYY-MM-DD format.
     * @return {@link ResponseEntity} with the summary of lorry receipts.
     */
    @GetMapping("/get-lr-summary")
    public ResponseEntity<?> getLrSummary(@RequestParam String startDate,@RequestParam String endDate){
       return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLrSummary(startDate,endDate));
    }

    /**
     * Updates the bill details for a given lorry receipt.
     *
     * @param bill   The bill details to update.
     * @param lrNo   The lorry receipt number.
     * @param lrDate The lorry receipt date.
     * @return {@link ResponseEntity} containing the update status.
     */
    @PutMapping("/update-bill-details")
    public ResponseEntity<?> updateBillDetails(@RequestBody Bill bill,@RequestParam String lrNo,
                                               @RequestParam String lrDate) throws BillAlreadySavedException {

       try {
           ApiResponse<?> response = lorryReceiptService.updateBillDetails(bill, lrNo, lrDate);
           if (response.isSuccess()) {
               return ResponseEntity.status(HttpStatus.OK).body(response);
           } else {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
           }
       }catch (BillAlreadySavedException e){
           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
       }
    }

    /**
     * Retrieves a lorry receipt by memo number and lorry receipt number.
     *
     * @param lrNo   The lorry receipt number.
     * @param memoNo The memo number.
     * @return {@link ResponseEntity} with the lorry receipt details.
     */
    @GetMapping("/get-lr-by-memoNo-lrNo")
    public ResponseEntity<?> getLrByMemoNoLrNo(@RequestParam String lrNo,@RequestParam String memoNo){
          try {
              return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLrByMemoNoLrNo(lrNo,memoNo));
          }catch (Exception e){
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
          }
    }

    /**
     * Retrieves lorry receipts based on lorry receipt number and a date range.
     *
     * @param lrNo        The lorry receipt number.
     * @param lrStartDate The start date in YYYY-MM-DD format.
     * @param lrEndDate   The end date in YYYY-MM-DD format.
     * @return {@link ResponseEntity} containing the list of lorry receipts.
     */
    @GetMapping("/get-lrs-by-lrNo-lrDate")
    public ResponseEntity<?> getLrsByLrNoLrDate(@RequestParam String lrNo,
                                                @RequestParam String lrStartDate,
                                                @RequestParam String lrEndDate){

       return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLrsByLrNoLrDate(lrNo,lrStartDate,lrEndDate));
    }

    /**
     * Retrieves lorry receipts by memo number.
     *
     * @param memoNo The memo number.
     * @return {@link ResponseEntity} containing the list of lorry receipts.
     */
    @GetMapping("/get-lrs-by-memo-no")
    public ResponseEntity<?> getLrsByMemoNo(@RequestParam String memoNo){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLrsByMemoNo(memoNo));
    }

    /**
     * Updates the memo date and lorry receipt date.
     *
     * @param lrNo     The lorry receipt number.
     * @param memoNo   The memo number.
     * @param lrDate   The new lorry receipt date.
     * @param memoDate The new memo date.
     * @return {@link ResponseEntity} with the update status.
     */
    @PutMapping("/update-memo-date-lr-date")
    public ResponseEntity<?> updateMemoDateLrDate(@RequestParam String lrNo,
                                                  @RequestParam String memoNo,
                                                  @RequestParam String lrDate,
                                                  @RequestParam String memoDate){
        try {

            ApiResponse<?> response = lorryReceiptService.updateMemoDateLrDate(lrNo, memoNo, lrDate, memoDate);

            if(response.isSuccess()){
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves lorry receipts by lorry receipt number.
     *
     * @param lrNo The lorry receipt number.
     * @return {@link ResponseEntity} containing the list of lorry receipts.
     */
    @GetMapping("/get-lrs-by-lr-no")
    public ResponseEntity<?> getLrsByLrNo(@RequestParam String lrNo){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLrsByLrNo(lrNo));
    }

    /**
     * Retrieves lorry receipts based on branch number and a date range.
     *
     * @param branchNo  The branch number.
     * @param startDate The start date in YYYY-MM-DD format.
     * @param endDate   The end date in YYYY-MM-DD format.
     * @return {@link ResponseEntity} containing the list of lorry receipts.
     */
    @GetMapping("/get-lrs-by-branch-start-date-end-date")
    public ResponseEntity<?> getLrsByBranchStartDateAndEndDate(@RequestParam String branchNo,
                                                               @RequestParam String startDate,
                                                               @RequestParam String endDate){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLrsByBranchStartDateAndEndDate(branchNo,startDate,endDate));
    }

    /**
     * Retrieves lorry receipt items based on a chalan number.
     *
     * @param chalanNo The chalan number.
     * @return {@link ResponseEntity} containing the lorry receipt items.
     */
    @GetMapping("/get-lorry-receipt-item-by-chalan-no")
    public ResponseEntity<?> getLorryReceiptItem(@RequestParam String chalanNo){

         return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLorryReceiptItem(chalanNo));
    }


    /**
     * Retrieves the total count of lorry receipts.
     *
     * @return A {@link ResponseEntity} containing the total number of lorry receipts (HTTP 200 OK).
     */
    @GetMapping("/get-lr-count")
    public ResponseEntity<?> getLrCount(){
        return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.getLrCount());
    }

    /**
     * Checks if a LoryReceipt exists by its LR number.
     *
     * @param lrNo The LR number to check.
     * @return {@link ResponseEntity} with true if the memo exists, false otherwise.
     */
    @GetMapping("/check-lr-no-exists")
    public ResponseEntity<?> checkLrExists(@RequestParam String lrNo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(lorryReceiptService.checkLrNoExists(lrNo));
        }catch (Exception e){
            return ResponseEntity.ok(0);
        }
    }
}
