package com.viplogistics.entity.transaction.dto.helper.extracharges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChakanExtraCharges {

    private String lrNo;

    private Double unloadingCharges;

    private Double lrCharges;

    private Double stCharges;
}
