package com.viplogistics.entity.transaction.dto.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonFreightBillDataDto {

    private String partyName;

    private String address;

    private String district;

    private String stateCode;

    private String gstNo;

    private String to;

    private String code;
}
