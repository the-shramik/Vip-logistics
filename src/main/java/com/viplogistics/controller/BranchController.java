package com.viplogistics.controller;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Branch;
import com.viplogistics.service.IBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * BranchController - Handles API requests for managing branches.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branch")
@CrossOrigin("*")
public class BranchController {

    private final IBranchService branchService;

    /**
     * Adds a new branch.
     *
     * @param branch The request body containing branch details.
     * @return A {@link ResponseEntity} containing the saved Branch object (HTTP 200 OK).
     */
    @PostMapping("/add-branch")
    public ResponseEntity<?> addBranch(@RequestBody Branch branch){
        return ResponseEntity.status(HttpStatus.OK).body(branchService.addBranch(branch));
    }

    /**
     * Retrieves all branches.
     *
     * @return A {@link ResponseEntity} containing a list of all Branch objects (HTTP 200 OK).
     */
    @GetMapping("/get-branches")
    public ResponseEntity<?> getAllBranches(){
       return ResponseEntity.status(HttpStatus.OK).body(branchService.getAllBranches());
    }

    /**
     * Updates an existing branch.
     *
     * @param branch The request body containing updated branch details.
     * @return A {@link ResponseEntity} containing:
     *         - The updated Branch object if successful (HTTP 200 OK).
     *         - An error message if the branch is not found (HTTP 404 Not Found).
     */
    @PutMapping("/update-branch")
    public ResponseEntity<?> updateBranch(@RequestBody Branch branch){
       try {
           return ResponseEntity.status(HttpStatus.OK).body(branchService.updateBranch(branch));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }

    /**
     * Deletes a branch by its branch number.
     *
     * @param branchNo The unique identifier of the branch to be deleted.
     * @return A {@link ResponseEntity} containing:
     *         - A success message if the deletion is successful (HTTP 200 OK).
     *         - An error message if the branch is not found (HTTP 404 Not Found).
     */
    @DeleteMapping("/delete-branch")
    public ResponseEntity<?> deleteBranch(@RequestParam String branchNo){
        ApiResponse<?> response = branchService.deleteBranch(branchNo);

        if(response.isSuccess()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

    /**
     * Retrieves the latest branch number.
     *
     * @return A {@link ResponseEntity} containing the latest branch number (HTTP 200 OK).
     */
    @GetMapping("/get-latest-branch-no")
    public ResponseEntity<?> getLatestBranchNo(){
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getLatestBranchNo());
    }

    /**
     * Retrieves the total count of branches.
     *
     * @return A {@link ResponseEntity} containing the total number of branches (HTTP 200 OK).
     */
    @GetMapping("/get-branch-count")
    public ResponseEntity<?> getBranchCount(){
        return ResponseEntity.status(HttpStatus.OK).body(branchService.getBranchCount());
    }

    @PostMapping("/test-branch")
    public ResponseEntity<?> testBranch(){
        branchService.testBranch();
      return ResponseEntity.ok("All branches added!");
    }

    @PostMapping("/test-delete-branch")
    public ResponseEntity<?> deleteBranch(){
        branchService.deleteBranch();
        return ResponseEntity.ok("All branches deleted!");
    }
}
