package com.viplogistics.entity.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MisReportDto - DTO class representing a MIS (Management Information System) report for a lorry receipt.
 *
 * This DTO is used to transfer comprehensive details related to a lorry receipt, including vendor details,
 * item information, chalan, bill, freight values, taxes, and other relevant fields.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MisReportDto {

    private Long lrId;

    private String vendorCode;

    private String vendorName;

    private String partNo;

    private String partName;

    private String chalanNo;

    private String chalanDate;

    private Integer quantity;

    private String lcvFtl;

    private String packType;

    private String lrNo;

    private String lrDate;

    private String vehicleNo;

    private Double valueOnChalan;

    private String billNo;

    private String billDate;

    private String unloadDate;

    private Double totalWight;

    private Double totalFreight;

    private String memoNo;

    private String pu;

    private String asnNo;

    private Double cgst;

    private Double sgst;

    private Double igst;

    private Double grandTotal; //With GST calculation


}
