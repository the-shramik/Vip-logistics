package com.viplogistics.repository;

import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INagpurPickupFreightBillRepository extends JpaRepository<NagpurPickupBillReport,Long> {

    Boolean existsByBillNo(String billNo);
}
