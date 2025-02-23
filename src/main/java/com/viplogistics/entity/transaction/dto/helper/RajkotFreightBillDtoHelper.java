package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.NagpurPickupFreightBillDto;
import com.viplogistics.entity.transaction.dto.RajkotFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RajkotFreightBillDtoHelper {
    private List<RajkotFreightBillDto> rajkotFreightBillDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;
}
