package com.viplogistics.entity.transaction.dto.helper.extracharges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RajkotExtraCharges {
    private String lrNo;

    private Double loadingCharges;

    private Double unloadingCharges;

    private Double plyWoodCharges;

    private Double detentionCharges;

    private Double stCharges;
}
