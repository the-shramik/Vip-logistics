package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Branch;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IBranchRepository;
import com.viplogistics.service.IBranchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements IBranchService {

    private final IBranchRepository branchRepository;

    @Override
    public Branch addBranch(Branch branch) {

        if (branch.getBranchNo() == null || branch.getBranchNo().isEmpty()) {
            String lastBranchNo = branchRepository.findTopByOrderByBranchNoDesc();
            String newBranchNo = generateBranchNo(lastBranchNo).get("newBranchNo");
            branch.setBranchNo(newBranchNo);
        }
        branch.setBranchDate(LocalDate.now());
        return branchRepository.save(branch);
    }

    private Map<String, String> generateBranchNo(String lastBranchNo) {
        Map<String, String> result = new HashMap<>();

        if (lastBranchNo == null || lastBranchNo.isEmpty()) {
            result.put("newBranchNo", "B0001");
            return result;
        }

        int numericPart = Integer.parseInt(lastBranchNo.substring(1));
        String newBranchNo = String.format("B%04d", numericPart + 1);

        result.put("newBranchNo", newBranchNo);

        return result;
    }

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch updateBranch(Branch branch) throws ResourceNotFoundException {

        Branch existedBranch=branchRepository.findById(branch.getBranchNo())
                .orElseThrow(()->new ResourceNotFoundException("Branch not found with branch no: "+branch.getBranchNo()));

        existedBranch.setBranchName(branch.getBranchName());
        existedBranch.setBranchDate(existedBranch.getBranchDate());

        return branchRepository.save(existedBranch);
    }

    @Override
    public ApiResponse<?> deleteBranch(String branchNo) {
        Optional<Branch> optionalBranch = branchRepository.findById(branchNo);

        if(optionalBranch.isPresent()){

            Branch branch=optionalBranch.get();
            branchRepository.delete(branch);

            return new ApiResponse<>(true,"Branch deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Branch not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Map<String, String> getLatestBranchNo() {
        String lastBranchNo=branchRepository.findTopByOrderByBranchNoDesc();

        return generateBranchNo(lastBranchNo);
    }

    @Override
    public Map<String, Long> getBranchCount() {
        HashMap<String,Long> result=new HashMap<>();
        result.put("totalBranches",branchRepository.count());

        return result;
    }

    @Override
    public void testBranch() {
        List<Branch> branches = new ArrayList<>();

        for (int i = 1; i <= 2000; i++) {
            Branch branch = new Branch();
            branch.setBranchNo(null); // Let addBranch() generate it
            branch.setBranchName("Branch " + i);
            branch.setBranchDate(LocalDate.now());

            branches.add(branch);
        }

        branches.forEach(this::addBranch);
    }

    @Transactional
    @Override
    public void deleteBranch() {
        List<Branch> branches = branchRepository.findAll(); // Fetch all branches
        if (branches.size() > 3) {
            List<Branch> branchesToDelete = branches.subList(3, branches.size()); // Skip first 3 records
            branchRepository.deleteAll(branchesToDelete); // Delete remaining records
        }
    }

}
