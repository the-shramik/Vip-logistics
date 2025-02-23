package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.RajkotFreightBillDto;
import com.viplogistics.entity.transaction.dto.RudrapurFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RudrapurFreightBillDtoHelper {
    private List<RudrapurFreightBillDto> rudrapurFreightBillDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;
}
