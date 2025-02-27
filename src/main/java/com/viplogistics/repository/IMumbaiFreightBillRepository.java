package com.viplogistics.repository;

import com.viplogistics.entity.transaction.MumbaiBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMumbaiFreightBillRepository extends JpaRepository<MumbaiBillReport,Long> {
    Boolean existsByBillNo(String billNo);

    Optional<MumbaiBillReport> findByBillNo(String billNo);

    List<MumbaiBillReport> findByIsVerifiedFalse();
}
