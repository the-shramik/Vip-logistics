package com.viplogistics.entity.transaction.dto.helper.response;


import com.viplogistics.entity.transaction.dto.RajkotFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RajkotFreightBillResponseDto {

    private Double loadingCharges;

    private Double unloadingCharges;

    private Double plyWoodCharges;

    private Double detentionCharges;

    private List<RajkotFreightBillDto> rajkotFreightBillDtos;
}
