package com.viplogistics.repository;

import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INagpurPickupFreightBillRepository extends JpaRepository<NagpurPickupBillReport,Long> {

    Boolean existsByBillNo(String billNo);

    Optional<NagpurPickupBillReport> findByBillNo(String billNo);

    List<NagpurPickupBillReport> findByIsVerifiedFalse();

}
