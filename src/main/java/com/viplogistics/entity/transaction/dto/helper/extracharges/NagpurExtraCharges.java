package com.viplogistics.entity.transaction.dto.helper.extracharges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NagpurExtraCharges {
    private String lrNo;

    private Double unloadingCharges;

    private Double plyWoodCharges;

    private Double collectionCharges;

    private Double lrCharges;

    private Double stCharges;
}
