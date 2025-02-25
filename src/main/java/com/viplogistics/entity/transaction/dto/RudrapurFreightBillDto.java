package com.viplogistics.entity.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RudrapurFreightBillDto {

    private Long lrId;

    private String sac;

    private String lrNo;

    private String lrDate;

    private String unloadingDate;

    private String from;

    private String to;

    private String invoiceNo;

    private String supplierName;

    private Double weight;

    private Double totalFreight;

    private Double stCharges;

    private Double taxableAmt;

    private Double igst;

    private String roundOff;

    private Double totalBillValue;

    private Double grandTotal;

    private CommonFreightBillDataDto commonFreightBillDataDto;

}
