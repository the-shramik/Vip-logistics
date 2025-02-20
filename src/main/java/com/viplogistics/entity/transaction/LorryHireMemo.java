package com.viplogistics.entity.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.viplogistics.entity.master.Branch;
import com.viplogistics.entity.master.Vehicle;
import com.viplogistics.entity.transaction.helper.Memo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@code LorryHireMemo} - Entity class representing the transaction details for a lorry hire memo.
 *
 * This class is used for capturing information related to the hire of a lorry, including payment details,
 * vehicle, driver, branch information, and associated memo details.
 *
 * @see Branch
 * @see Vehicle
 * @see Memo
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lorry_hire_memo_transaction")
public class LorryHireMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lorryHireMemoId;

    @ManyToOne
    @JoinColumn(name = "branch_no", nullable = false)
    private Branch branch;


    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonBackReference
    private Vehicle vehicle;

    private String owner;

    private String driver;

    @OneToOne
    @JoinColumn(name = "memo_id", nullable = true, unique = true)
    private Memo memo;

    private String permitNo;

    private String licenceNo;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "lorry_hire_memo_id")
//    private List<LorryReceipt> lorryReceipts;

    private Double totalHire;

    private Double advance;

    private Double extraCollection;

    private Double commission;

    private Double hamali;

    private Double misc;

    private Double total;

    private Double balance;

    private String advancePaymentType;

    private String advanceCashBankAcNo;

    private String advanceCheDdNo;

    private String advanceCheDdDate;

    private String finalPaymentType;

    private String finalCashBankAcNo;

    private String finalCheNo;

    private String finalCheDate;
}
