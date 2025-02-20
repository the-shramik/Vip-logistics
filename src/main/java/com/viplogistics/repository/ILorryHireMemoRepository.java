package com.viplogistics.repository;

import com.viplogistics.entity.transaction.LorryHireMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILorryHireMemoRepository extends JpaRepository<LorryHireMemo,Long> {
}
