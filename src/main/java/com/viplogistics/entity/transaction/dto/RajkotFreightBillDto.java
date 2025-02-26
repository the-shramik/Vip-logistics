package com.viplogistics.entity.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RajkotFreightBillDto {

    private Long lrId;

    private String lrNo;

    private String lrDate;

    private String unloadingDate;

    private String from;

    private String to;

    private String invoiceNo;

    private String vendorName;

    private Integer quantity;

    private Double weight;

    private String vehicleNo;

    private String rate;

    private Double totalFreight;

    private Double subTotal;

    private Double igst;

    private Double totalBillValue;

    private Double grandTotal;

    private CommonFreightBillDataDto commonFreightBillDataDto;
}
