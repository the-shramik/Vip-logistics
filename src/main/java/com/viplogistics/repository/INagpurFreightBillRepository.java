package com.viplogistics.repository;

import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.NagpurBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INagpurFreightBillRepository extends JpaRepository<NagpurBillReport,Long> {
    Boolean existsByBillNo(String billNo);

    Optional<NagpurBillReport> findByBillNo(String billNo);

    List<NagpurBillReport> findByIsVerifiedFalse();
}
