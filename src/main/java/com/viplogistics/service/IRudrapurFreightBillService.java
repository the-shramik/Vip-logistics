package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.entity.transaction.RudrapurBillReport;
import com.viplogistics.entity.transaction.dto.helper.RudrapurFreightBillDtoHelper;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;

public interface IRudrapurFreightBillService {

    RudrapurFreightBillDtoHelper getRudrapurFreightBill(String billNo,String routeName) throws ResourceNotFoundException;

    RudrapurBillReport saveRudrapurFreightBill(RudrapurBillReport rudrapurBillReport) throws BillAlreadySavedException;

    RudrapurBillReport updateRudrapurFreightBill(RudrapurBillReport rudrapurBillReport) throws ResourceNotFoundException;
    ApiResponse<?> deleteRudrapurFreightBill(Long freightBillReportId);

    List<RudrapurBillReport> getAllRudrapurFreightBills();
}
