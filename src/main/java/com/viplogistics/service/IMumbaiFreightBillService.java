package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.dto.helper.MumbaiFreightBillDtoHelper;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;

public interface IMumbaiFreightBillService {
    MumbaiFreightBillDtoHelper getMumbaiFreightBill(String billNo,String routeName) throws ResourceNotFoundException;

    MumbaiBillReport saveMumbaiFreightBill(MumbaiBillReport mumbaiBillReport) throws BillAlreadySavedException;

    MumbaiBillReport updateMumbaiFreightBill(MumbaiBillReport mumbaiBillReport) throws ResourceNotFoundException;
    ApiResponse<?> deleteMumbaiFreightBill(Long freightBillReportId);

}
