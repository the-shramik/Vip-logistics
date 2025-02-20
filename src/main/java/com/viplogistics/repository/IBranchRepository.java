package com.viplogistics.repository;

import com.viplogistics.entity.master.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBranchRepository extends JpaRepository<Branch,String> {

    @Query("SELECT b.branchNo FROM branch_master b ORDER BY b.branchNo DESC LIMIT 1")
    String findTopByOrderByBranchNoDesc();
}
