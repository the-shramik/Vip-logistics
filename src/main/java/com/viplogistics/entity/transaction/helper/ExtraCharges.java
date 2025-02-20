package com.viplogistics.entity.transaction.helper;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity(name = "lorry_receipt_extra_charges")
public class ExtraCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extraChargesId;

    private String chargesHeads;

    private Double chargesAmount;

    @ManyToOne
    @JoinColumn(name = "lorry_receipt_id")
    @JsonBackReference
    private LorryReceipt lorryReceipt;
}
