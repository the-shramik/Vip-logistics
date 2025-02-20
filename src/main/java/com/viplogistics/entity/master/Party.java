package com.viplogistics.entity.master;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viplogistics.entity.transaction.LorryReceipt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Party - Represents a party involved in logistics transactions.
 *
 * This entity stores the details of a party, including their financial information,
 * address, associated items, and receipts.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "party_master")
public class Party {

    @Id
    @Column(name = "party_no", nullable = false, length = 20)
    private String partyNo;

    private String partyName;

    private String accountType;

    private String balanceMarkType;

    private Double openingBalance;

    private Double balanceMarkAmount;

    private LocalDate partyDate;

    @Lob
    private String address;

    private String district;

    private String codeNo;

    private String division;

    @Column(unique = true)
    private String gstNumber;

    private String stateCode;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<Item> items;

    @OneToMany(mappedBy = "consignor", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<LorryReceipt> consignorReceipts;

    @OneToMany(mappedBy = "consignee", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<LorryReceipt> consigneeReceipts;

}
