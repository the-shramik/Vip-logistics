package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.dto.ItemListDto;
import com.viplogistics.entity.transaction.dto.MisReportDto;
import com.viplogistics.entity.transaction.dto.RegisterDto;
import com.viplogistics.entity.transaction.helper.Bill;
import com.viplogistics.entity.transaction.helper.MemoDto;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface ILorryReceiptService {

    LorryReceipt addLorryReceipt(LorryReceipt lorryReceipt);

    List<LorryReceipt> getAllLorryReceipts();

    LorryReceipt updateLorryReceipt(LorryReceipt lorryReceipt) throws ResourceNotFoundException;

    ApiResponse<?> deleteLorryReceipt(Long lrId) throws ResourceNotFoundException;

    Map<String,String> getLatestLrNo();

    LorryReceipt updateLorryStatus(Long lrId,Boolean status) throws ResourceNotFoundException;

    MemoDto checkMemoExists(String memoNo) throws ResourceNotFoundException;

    Map<String,String> getLastMemoNo();

    List<MisReportDto> getLrSummary(String startDate, String endDate);

    ApiResponse<?> updateBillDetails(Bill bill,String lrNo,String lrDate) throws BillAlreadySavedException;

    LorryReceipt getLrByMemoNoLrNo(String lrNo,String memoNo) throws ResourceNotFoundException;

    List<LorryReceipt> getLrsByLrNoLrDate(String lrNo,String lrStartDate,String lrEndDate);

    List<LorryReceipt> getLrsByMemoNo(String memoNo);

    ApiResponse<?> updateMemoDateLrDate(String lrNo,String memoNo,String lrDate,String memoDate) throws ResourceNotFoundException;

    List<LorryReceipt> getLrsByLrNo(String lrNo);

    List<RegisterDto> getLrsByBranchStartDateAndEndDate(String branchNo, String startDate, String endDate);

    ItemListDto getLorryReceiptItem(String chalanNo);

    Map<String,Long> getLrCount();

    Boolean checkLrNoExists(String lrNo);
}
