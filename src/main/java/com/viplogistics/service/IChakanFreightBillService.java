package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.dto.helper.ChakanFreightBillDtoHelper;
import com.viplogistics.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

public interface IChakanFreightBillService {

    ChakanFreightBillDtoHelper getChakanFreightBill(String billNo,String routeName) throws ResourceNotFoundException;

    ChakanBillReport saveChakanFreightBill(ChakanBillReport chakanBillReport);

    ChakanBillReport updateChakanFreightBill(ChakanBillReport chakanBillReport) throws ResourceNotFoundException;
    ApiResponse<?> deleteChakanFreightBill(Long freightBillReportId);
}
