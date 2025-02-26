package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.NagpurPickupFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.extracharges.NagpurPickupExtraCharges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NagpurPickupFreightBillDtoHelper {

    private List<NagpurPickupFreightBillDto> nagpurPickupFreightBillDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;

    private List<NagpurPickupExtraCharges> nagpurPickupExtraCharges;
}
