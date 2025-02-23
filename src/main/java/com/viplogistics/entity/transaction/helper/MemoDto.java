package com.viplogistics.entity.transaction.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoDto {

    private boolean memoExists;

    private String refTruckNo;

    private String whoPay;

    private String octroiPay;

    private String whoItemList;

    private String remark;
}
