package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.NagpurFreightBillDto;
import com.viplogistics.entity.transaction.dto.NagpurPickupFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.extracharges.NagpurExtraCharges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NagpurFreightBillDtoHelper {

    private List<NagpurFreightBillDto> nagpurFreightBillDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;

    private List<NagpurExtraCharges> nagpurExtraCharges;
}
