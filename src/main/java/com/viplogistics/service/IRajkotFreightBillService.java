package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.entity.transaction.dto.helper.RajkotFreightBillDtoHelper;
import com.viplogistics.exception.ResourceNotFoundException;

public interface IRajkotFreightBillService {

    RajkotFreightBillDtoHelper getRajkotFreightBill(String billNo,String routeName) throws ResourceNotFoundException;

    RajkotBillReport saveRajkotFreightBill(RajkotBillReport rajkotBillReport);

    RajkotBillReport updateRajkotFreightBill(RajkotBillReport rajkotBillReport) throws ResourceNotFoundException;
    ApiResponse<?> deleteRajkotFreightBill(Long freightBillReportId);

}
