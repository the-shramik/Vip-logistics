package com.viplogistics.entity.transaction.dto.helper.response;

import com.viplogistics.entity.transaction.dto.RudrapurFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RudrapurFreightBillResponseDto {

    private Double loadingCharges;

    private Double detentionCharges;

    private Double lrCharges;

    private List<RudrapurFreightBillDto> rudrapurFreightBillDtos;
}
