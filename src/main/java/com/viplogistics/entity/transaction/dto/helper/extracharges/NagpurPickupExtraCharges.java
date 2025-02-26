package com.viplogistics.entity.transaction.dto.helper.extracharges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NagpurPickupExtraCharges {
    private String lrNo;

    private Double loadingCharges;

    private Double plyWoodCharges;

    private Double collectionCharges;

    private Double stCharges;
}
