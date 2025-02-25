package com.viplogistics.repository;

import com.viplogistics.entity.transaction.RudrapurBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRudrapurFreightBillRepository extends JpaRepository<RudrapurBillReport,Long> {
    Boolean existsByBillNo(String billNo);
}
