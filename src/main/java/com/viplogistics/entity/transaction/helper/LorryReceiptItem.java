package com.viplogistics.entity.transaction.helper;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.viplogistics.entity.master.Item;
import com.viplogistics.entity.transaction.LorryReceipt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lorry_receipt_item_transaction")
public class LorryReceiptItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lorryReceiptItemId;

    @ManyToOne
    @JoinColumn(name = "lorry_receipt_id")
    @JsonBackReference
    private LorryReceipt lorryReceipt;

    @Column(unique = true)
    private String chalanNo;

    private String chalanDate;

    private Double valueOnChalan;

    private String asnNo;

    private String packType;

    private Double valueRs;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer quantity;

    private String lcvFtl;

    private String calcOn;

    private Double totalWeight;

    private Double amount;

    private Double cgst;

    private Double sgst;

    private Double igst;

    private Double totalFreight; //Without GST calculation
}
