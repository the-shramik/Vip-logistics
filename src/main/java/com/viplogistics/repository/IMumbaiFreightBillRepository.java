package com.viplogistics.repository;

import com.viplogistics.entity.transaction.MumbaiBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMumbaiFreightBillRepository extends JpaRepository<MumbaiBillReport,Long> {
    Boolean existsByBillNo(String billNo);
}
