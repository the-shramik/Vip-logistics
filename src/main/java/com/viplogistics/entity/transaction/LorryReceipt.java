package com.viplogistics.entity.transaction;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.viplogistics.entity.master.*;
import com.viplogistics.entity.transaction.helper.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code LorryReceipt} - Entity class representing the transaction details for a lorry receipt.
 *
 * This class captures the details of a lorry receipt transaction, including the associated branch, route,
 * consignor, consignee, and various charges related to the lorry receipt.
 *
 * @see Branch
 * @see Route
 * @see Party
 * @see Memo
 * @see Bill
 * @see LorryReceiptItem
 * @see ExtraCharges
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lorry_receipt_transaction")
public class LorryReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lrId;

    private String lrNo;

    @ManyToOne
    @JoinColumn(name = "branch_no", nullable = false)
    private Branch branch;


    @ManyToOne
    @JoinColumn(name = "route_no", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "consignor_no", nullable = false)
    private Party consignor;

    @ManyToOne
    @JoinColumn(name = "consignee_no", nullable = false)
    private Party consignee;

    private String remark;

    private String whoItemList;

    private String octBill;

    private String lrDate;

    private String whoPay;

    private String octroiPay;

    private String refTruckNo;

    @OneToMany(mappedBy = "lorryReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LorryReceiptItem> lorryReceiptItems = new ArrayList<>();

    @OneToMany(mappedBy = "lorryReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ExtraCharges> extraCharges = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "memo_id")
    private Memo memo;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    private Double grandTotal;

    private String deliveryAt;

    private String lrNote;

    private Double stCharges;

    private Boolean lrStatus;
}
