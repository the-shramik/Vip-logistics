package com.viplogistics.repository;

import com.viplogistics.entity.transaction.NagpurBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INagpurFreightBillRepository extends JpaRepository<NagpurBillReport,Long> {
}
