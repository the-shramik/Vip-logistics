package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import com.viplogistics.entity.transaction.dto.helper.NagpurPickupFreightBillDtoHelper;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;

public interface INagpurPickupFreightBillService {

    NagpurPickupFreightBillDtoHelper getNagpurPickupFreightBill(String billNo,String routeName) throws ResourceNotFoundException;

    NagpurPickupBillReport saveNagpurPickupFreightBill(NagpurPickupBillReport nagpurPickupBillReport) throws BillAlreadySavedException;

    NagpurPickupBillReport updateNagpurPickupFreightBill(NagpurPickupBillReport nagpurPickupBillReport) throws ResourceNotFoundException;
    ApiResponse<?> deleteNagpurPickupFreightBill(Long freightBillReportId);

    List<NagpurPickupBillReport> getAllNagpurPickupFreightBills();

    NagpurPickupBillReport getNagpurPickupFreightByBillNo(String billNo) throws ResourceNotFoundException;

    List<NagpurPickupBillReport> getAllNagpurPickupRequestedFreightBills();

}
