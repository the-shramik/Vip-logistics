package com.viplogistics.repository;

import com.viplogistics.entity.transaction.FreightBillReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFreightBillReportRepository extends JpaRepository<FreightBillReport,Long> {
}
