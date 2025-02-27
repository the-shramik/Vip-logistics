package com.viplogistics.repository;

import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.entity.transaction.RudrapurBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRudrapurFreightBillRepository extends JpaRepository<RudrapurBillReport,Long> {
    Boolean existsByBillNo(String billNo);

    Optional<RudrapurBillReport> findByBillNo(String billNo);

    List<RudrapurBillReport> findByIsVerifiedFalse();

}
