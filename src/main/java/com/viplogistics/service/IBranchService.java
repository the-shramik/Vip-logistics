package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Branch;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface IBranchService {

    Branch addBranch(Branch branch);

    List<Branch> getAllBranches();

    Branch updateBranch(Branch branch) throws ResourceNotFoundException;

    ApiResponse<?> deleteBranch(String branchNo);

    Map<String,String> getLatestBranchNo();

    Map<String,Long> getBranchCount();

    void testBranch();

    void deleteBranch();
}
