package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.entity.transaction.RudrapurBillReport;
import com.viplogistics.entity.transaction.dto.helper.RudrapurFreightBillDtoHelper;
import com.viplogistics.exception.ResourceNotFoundException;

public interface IRudrapurFreightBillService {

    RudrapurFreightBillDtoHelper getRudrapurFreightBill(String billNo,String routeName) throws ResourceNotFoundException;

    RudrapurBillReport saveRudrapurFreightBill(RudrapurBillReport rudrapurBillReport);

    RudrapurBillReport updateRudrapurFreightBill(RudrapurBillReport rudrapurBillReport) throws ResourceNotFoundException;
    ApiResponse<?> deleteRudrapurFreightBill(Long freightBillReportId);
}
