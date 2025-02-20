package com.viplogistics.entity.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code RegisterDto} - DTO class representing the details of a register related to lorry receipt and memo.
 *
 * This DTO is used for transferring data related to lorry receipt registers, including details about consignors,
 * consignees, quantities, weights, chalan, billing, and payment statuses.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private Long id;

    private String memoDate;

    private String memoNo;

    private String lcvFtl;

    private String refTruckNo;

    private String lrNo;

    private String consignor;

    private String consignee;

    private Integer quantity;

    private Double weight;

    private String chalanNo;

    private Double paid;

    private Double toPay;

    private String billed;

    private String billNo;
}
