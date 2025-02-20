package com.viplogistics.repository;

import com.viplogistics.entity.transaction.helper.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepository extends JpaRepository<Bill,Long> {
}
