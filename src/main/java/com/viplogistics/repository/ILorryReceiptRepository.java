package com.viplogistics.repository;

import com.viplogistics.entity.transaction.LorryReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILorryReceiptRepository extends JpaRepository<LorryReceipt,Long> {

    @Query("SELECT lr.lrNo FROM lorry_receipt_transaction lr ORDER BY lr.lrNo DESC LIMIT 1")
    String findTopByOrderByLrNoDesc();

    @Query("SELECT lr FROM lorry_receipt_transaction lr " +
            "WHERE lr.lrDate BETWEEN :startDate AND :endDate")
    List<LorryReceipt> findByLrDateBetween(@Param("startDate") String startDate,
                                           @Param("endDate") String endDate);

    @Query("SELECT lr FROM lorry_receipt_transaction lr WHERE lr.lrNo = :lrNo AND lr.lrDate = :lrDate")
    List<LorryReceipt> findByLrNoAndLrDate(@Param("lrNo") String lrNo, @Param("lrDate") String lrDate);

    List<LorryReceipt> findByMemo_MemoNoAndLrNo(String memoNo, String lrNo);

    @Query("SELECT lr FROM lorry_receipt_transaction lr WHERE lr.lrNo = :lrNo AND lr.lrDate BETWEEN :lrStartDate AND :lrEndDate")
    List<LorryReceipt> findByLrNoAndLrDateBetween(@Param("lrNo") String lrNo, @Param("lrStartDate") String lrStartDate,@Param("lrEndDate") String lrEndDate);

    @Query("SELECT lr FROM lorry_receipt_transaction lr JOIN FETCH lr.memo m WHERE m.memoNo = :memoNo")
    List<LorryReceipt> findByMemoNo(String memoNo);

    @Query("SELECT lr FROM lorry_receipt_transaction lr " +
            "JOIN lr.memo m " +
            "WHERE (:lrNo IS NULL OR lr.lrNo = :lrNo) " +
            "AND (:memoNo IS NULL OR m.memoNo = :memoNo)")
    List<LorryReceipt> findByLrNoAndMemoNo(String lrNo, String memoNo);

    List<LorryReceipt> getAllByLrNo(String lrNo);

    @Query("SELECT lr FROM lorry_receipt_transaction lr WHERE lr.lrDate BETWEEN :startDate AND :endDate AND lr.branch.branchNo = :branchNo")
    List<LorryReceipt> findByLrDateBetweenAndBranchNo(@Param("branchNo") String branchNo,
                                                      @Param("startDate") String startDate,
                                                      @Param("endDate") String endDate);

    @Query("SELECT lr FROM lorry_receipt_transaction lr " +
            "WHERE lr.bill.billNo = :billNo " +
            "AND lr.bill IS NOT NULL " +
            "AND lr.route.routeName = :routeName")
    List<LorryReceipt> findByBillNoAndRouteName(@Param("billNo") String billNo,
                                                             @Param("routeName") String routeName);

    boolean existsByLrNo(String lrNo);

    List<LorryReceipt> findByBill_BillNo(String billNo);

}
