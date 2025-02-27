package com.viplogistics.repository;

import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import com.viplogistics.entity.transaction.RajkotBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRajkotFreightBillRepository extends JpaRepository<RajkotBillReport,Long> {
    Boolean existsByBillNo(String billNo);

    Optional<RajkotBillReport> findByBillNo(String billNo);

    List<RajkotBillReport> findByIsVerifiedFalse();

}
