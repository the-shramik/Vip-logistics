package com.viplogistics.entity.transaction.dto;

import com.viplogistics.entity.transaction.dto.helper.CommonFreightBillDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChakanFreightBillDto {

    private Long lrId;

    private String lrNo;

    private String lrDate;

    private String unloadingDate;

    private String from;

    private String to;

    private String consignor;

    private String consignee;

    private String invoiceNo;

    private String vehicleNo;

    private String vehicleType;

    private String tripNo;

    private Double totalFreight;

    private Double unloadingCharges;

    private Double cgst;

    private Double sgst;

    private Double lrCharges;

    private Double totalBillValue;

    private CommonFreightBillDataDto commonFreightBillDataDto;

}
