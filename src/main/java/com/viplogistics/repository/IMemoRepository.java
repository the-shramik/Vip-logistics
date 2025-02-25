package com.viplogistics.repository;

import com.viplogistics.entity.transaction.helper.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMemoRepository extends JpaRepository<Memo,Long> {

    Optional<Memo> findByMemoNo(String memoNo);

    @Query("SELECT m.memoNo FROM memo_lorry_receipt m ORDER BY m.memoId DESC LIMIT 1")
    String findLastMemoNo();


}
