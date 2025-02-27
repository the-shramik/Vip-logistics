package com.viplogistics.repository;

import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IChakanFreightBillRepository extends JpaRepository<ChakanBillReport,Long> {

    Boolean existsByBillNo(String billNo);

    Optional<ChakanBillReport> findByBillNo(String billNo);

    List<ChakanBillReport> findByIsVerifiedFalse();

}
