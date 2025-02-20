package com.viplogistics.service;

import com.viplogistics.entity.transaction.FreightBillReport;
import com.viplogistics.entity.transaction.dto.*;

import java.util.List;

public interface IFreightService {

    List<MumbaiFreightBillDto> getMumbaiFreightBill(String billNo);
    List<RajkotFreightBillDto> getRajkotFreightBill(String billNo);
    List<NagpurPickupFreightBillDto> getNagpurPickupFreightBill(String billNo);
    List<ChakanFreightBillDto> getChakanFreightBill(String billNo);
    List<RudrapurFreightBillDto> getRudrapurFreightBill(String billNo);
    List<NagpurFreightBillDto> getNagpurFreightBill(String billNo);
    FreightBillReport saveFreightBill(FreightBillReport freightBillReport);
}
