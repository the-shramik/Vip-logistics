package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryHireMemo;
import com.viplogistics.exception.MemoLockedException;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.service.ILorryHireMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * LorryHireMemoController - Handles API requests for managing lorry hire memo.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lorry-hire-memo")
@CrossOrigin("*")
public class LorryHireMemoController {

    private final ILorryHireMemoService lorryHireMemoService;

    /**
     * Adds a new Lorry Hire Memo.
     *
     * @param lorryHireMemo The request body containing lorry hire memo details.
     * @return A {@link ResponseEntity} containing:
     *         - The saved Lorry Hire Memo object if successful (HTTP 200 OK).
     *         - An error message if an exception occurs (HTTP 502 Bad Gateway).
     */
    @PostMapping("/add-lorry-hire-memo")
    public ResponseEntity<?> addLorryHireMemo(@RequestBody LorryHireMemo lorryHireMemo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(lorryHireMemoService.addLorryHireMemo(lorryHireMemo));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
        }
    }

    /**
     * Retrieves all Lorry Hire Memos.
     *
     * @return A {@link ResponseEntity} containing a list of all Lorry Hire Memos (HTTP 200 OK).
     */
    @GetMapping("/get-lorry-hire-memos")
    public ResponseEntity<?> getAllLorryHireMemos(){
       return ResponseEntity.status(HttpStatus.OK).body(lorryHireMemoService.getAllLorryHireMemos());
    }

    /**
     * Updates an existing Lorry Hire Memo.
     *
     * @param lorryHireMemo The request body containing updated Lorry Hire Memo details.
     * @return A {@link ResponseEntity} containing:
     *         - The updated Lorry Hire Memo object if successful (HTTP 200 OK).
     *         - An error message if the memo is not found (HTTP 404 Not Found).
     */
    @PutMapping("/update-lorry-hire-memo")
    public ResponseEntity<?> updateLorryHireMemo(@RequestBody LorryHireMemo lorryHireMemo){
       try {
          return ResponseEntity.status(HttpStatus.OK).body(lorryHireMemoService.updateLorryHireMemo(lorryHireMemo));
       }catch (ResourceNotFoundException exception){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
       }
    }

    /**
     * Deletes a Lorry Hire Memo by its ID.
     *
     * @param hireMemoId The ID of the Lorry Hire Memo to be deleted.
     * @return A {@link ResponseEntity} containing:
     *         - A success message if the deletion is successful (HTTP 200 OK).
     *         - An error message if the memo is not found (HTTP 404 Not Found).
     */
    @DeleteMapping("/delete-lorry-hire")
    public ResponseEntity<?> deleteLorryHireMemos(@RequestParam Long hireMemoId){
        ApiResponse<?> response = lorryHireMemoService.deleteLorryHireMemo(hireMemoId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
