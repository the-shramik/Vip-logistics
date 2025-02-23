package com.viplogistics.entity.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MumbaiFreightBillDto {

    private Long lrId;

    private String lrNo;

    private String lrDate;

    private String unloadingDate;

    private String from;

    private String to;

    private String invoiceNo;

    private String vendorName;

    private Integer quantity;

    private String vehicleNo;

    private String rate;

    private Double totalFreight;

    private Double loadingCharges;

    private Double unloadingCharges;

    private Double plyWoodCharges;

    private Double detentionCharges;

    private Double stCharges;

    private Double subTotal;

    private Double cgst;

    private Double sgst;

    private Double totalBillValue;

    private CommonFreightBillDataDto commonFreightBillDataDto;
}
