package com.viplogistics.repository;

import com.viplogistics.entity.transaction.ChakanBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChakanFreightBillRepository extends JpaRepository<ChakanBillReport,Long> {

    Boolean existsByBillNo(String billNo);
}
