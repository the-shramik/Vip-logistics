package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.dto.helper.NagpurFreightBillDtoHelper;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;

public interface INagpurFreightBillService {

    NagpurFreightBillDtoHelper getNagpurFreightBill(String billNo,String routeName) throws ResourceNotFoundException;

    NagpurBillReport saveNagpurFreightBill(NagpurBillReport nagpurBillReport) throws BillAlreadySavedException;

    NagpurBillReport updateNagpurFreightBill(NagpurBillReport nagpurBillReport) throws ResourceNotFoundException;
    ApiResponse<?> deleteNagpurFreightBill(Long freightBillReportId);

    List<NagpurBillReport> getAllNagpurFreightBills();

    NagpurBillReport getNagpurFreightByBillNo(String billNo) throws ResourceNotFoundException;

    List<NagpurBillReport> getAllNagpurRequestedFreightBills();

}
