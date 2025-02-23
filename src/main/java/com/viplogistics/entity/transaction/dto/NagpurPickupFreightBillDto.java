package com.viplogistics.entity.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NagpurPickupFreightBillDto {

    private Long lrId;

    private String lrNo;

    private String lrDate;

    private String unloadingDate;

    private String vendorName;

    private Double weight;

    private String vehicleNo;

    private String vehicleType;

    private String rate;

    private Double totalFreight;

    private Double loadingCharges;

    private Double plyWoodCharges;

    private Double collectionCharges;

    private Double stCharges;

    private Double cgst;

    private Double sgst;

    private Double totalBillValue;

    private CommonFreightBillDataDto commonFreightBillDataDto;
}
