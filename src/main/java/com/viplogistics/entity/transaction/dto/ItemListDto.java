package com.viplogistics.entity.transaction.dto;

import com.viplogistics.entity.master.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ItemListDto - DTO class representing the item details in a lorry receipt.
 *
 * This Data Transfer Object (DTO) is used to transfer item-related information associated with a lorry receipt,
 * including quantity, value, taxes, and other related fields.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemListDto {

    private Long lorryReceiptItemId;

    private String chalanNo;

    private String chalanDate;

    private Item item;

    private Integer quantity;

    private String lcvFtl;

    private String calcOn;

    private String asnNo;

    private String packType;

    private Double valueOnChalan;

    private Double valueRs;

    private Double amount;

    private Double totalFreight;

    private Double cgst;

    private Double sgst;

    private Double igst;
}
