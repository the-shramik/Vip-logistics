package com.viplogistics.entity.transaction.dto.helper.response;

import com.viplogistics.entity.transaction.dto.NagpurPickupFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NagpurPickupFreightBillResponseDto {

    private Double loadingCharges;

    private Double plyWoodCharges;

    private Double collectionCharges;

    private List<NagpurPickupFreightBillDto> nagpurPickupFreightBillDtos;
}
