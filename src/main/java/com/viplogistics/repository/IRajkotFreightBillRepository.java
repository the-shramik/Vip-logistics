package com.viplogistics.repository;

import com.viplogistics.entity.transaction.RajkotBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRajkotFreightBillRepository extends JpaRepository<RajkotBillReport,Long> {
    Boolean existsByBillNo(String billNo);
}
