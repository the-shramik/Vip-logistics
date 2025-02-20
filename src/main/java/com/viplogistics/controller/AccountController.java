package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Account;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * AccountController - Handles API requests for managing accounts.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
@CrossOrigin("*")
public class AccountController {

    private final IAccountService accountService;

    /**
     * Creates a new account.
     *
     * @param account The account details to be created.
     * @return A {@link ResponseEntity} containing the created account details (HTTP 200 OK).
     */
    @PostMapping("/create-account")
    public ResponseEntity<?> addAccount(@RequestBody Account account){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.addAccount(account));
    }

    /**
     * Retrieves a list of all accounts.
     *
     * @return A {@link ResponseEntity} containing a list of all accounts (HTTP 200 OK).
     */
    @GetMapping("/get-accounts")
    public ResponseEntity<?> getAllAccounts(){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
    }


    /**
     * Updates an existing account.
     *
     * @param account The updated account details.
     * @return A {@link ResponseEntity} containing the updated account details (HTTP 200 OK)
     *         or an error message if the account is not found (HTTP 404 NOT FOUND).
     */
    @PutMapping("/update-account")
    public ResponseEntity<?> updateAccount(@RequestBody Account account){
        try {
             return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(account));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Deletes an account by its ID.
     *
     * @param accountId The ID of the account to be deleted.
     * @return A {@link ResponseEntity} containing a success response (HTTP 200 OK)
     *         if the account is deleted successfully, or an error response (HTTP 404 NOT FOUND)
     *         if the account is not found.
     */
    @DeleteMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestParam Long accountId){
        ApiResponse<?> response = accountService.deleteAccount(accountId);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Retrieves an account by its account number.
     *
     * @param accountNumber The account number to search for.
     * @return A {@link ResponseEntity} containing the account details (HTTP 200 OK).
     * @throws ResourceNotFoundException If no account is found with the given account number.
     */
    @GetMapping("/get-account-by-account-numebr")
    public ResponseEntity<?> getAccountByAccountNumber(@RequestParam String accountNumber) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountByAccountNumber(accountNumber));
    }


    /**
     * Retrieves the total count of accounts.
     *
     * @return A {@link ResponseEntity} containing the total number of accounts (HTTP 200 OK).
     */
    @GetMapping("/get-account-count")
    public ResponseEntity<?> getAccountCount(){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountCount());
    }
}
