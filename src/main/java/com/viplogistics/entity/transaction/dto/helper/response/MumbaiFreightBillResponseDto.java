package com.viplogistics.entity.transaction.dto.helper.response;

import com.viplogistics.entity.transaction.dto.MumbaiFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MumbaiFreightBillResponseDto {

    private Double loadingCharges;

    private Double unloadingCharges;

    private Double plyWoodCharges;

    private Double detentionCharges;

    private List<MumbaiFreightBillDto> mumbaiFreightBillDtos;
}
