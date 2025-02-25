package com.viplogistics.entity.transaction.dto.helper.response;

import com.viplogistics.entity.transaction.dto.NagpurFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NagpurFreightBillResponseDto {

    private Double unloadingCharges;

    private Double plyWoodCharges;

    private Double collectionCharges;

    private Double lrCharges;

    private List<NagpurFreightBillDto> nagpurFreightBillDtos;
}
