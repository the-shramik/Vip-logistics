package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.RajkotFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.extracharges.RajkotExtraCharges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RajkotFreightBillDtoHelper {
    private List<RajkotFreightBillDto> rajkotFreightBillDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;

    private List<RajkotExtraCharges> rajkotExtraCharges;
}
