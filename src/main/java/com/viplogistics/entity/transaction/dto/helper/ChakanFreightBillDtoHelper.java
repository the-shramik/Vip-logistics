package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.ChakanFreightBillDto;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.helper.extracharges.ChakanExtraCharges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChakanFreightBillDtoHelper {
    private List<ChakanFreightBillDto> chakanFreightBillDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;

    private List<ChakanExtraCharges> chakanExtraCharges;

}
