package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.service.IDataBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * DatabaseController - Handles API request for managing database operations.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/database")
@CrossOrigin("*")
public class DatabaseController  {

    private final IDataBaseService dataBaseService;


    /**
     * Resets the database by clearing all data.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} object.
     *         Returns HTTP 200 OK if the reset is successful,
     *         otherwise returns HTTP 500 Internal Server Error.
     */
    @DeleteMapping("/data-reset")
    public ResponseEntity<?> resetData(){

        ApiResponse<?> res = dataBaseService.resetDatabase();

        if(res.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }


}
