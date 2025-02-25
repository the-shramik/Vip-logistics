package com.viplogistics.entity.transaction.dto.helper.response;

import com.viplogistics.entity.transaction.dto.ChakanFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChakanFreightBillResponseDto {

    private Double unloadingCharges;

    private Double lrCharges;

    private List<ChakanFreightBillDto> chakanFreightBillDtos;
}
