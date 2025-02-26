package com.viplogistics.entity.transaction.dto.helper.extracharges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RudrapurExtraCharges {
    private String lrNo;

    private Double loadingCharges;

    private Double detentionCharges;

    private Double lrCharges;

    private Double stCharges;

}
