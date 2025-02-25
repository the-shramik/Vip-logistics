package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.helper.response.NagpurFreightBillResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NagpurFreightBillDtoHelper {

    private List<NagpurFreightBillResponseDto> nagpurFreightBillResponseDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;
}
