package com.viplogistics.entity.transaction.dto.helper;

import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.MumbaiFreightBillDto;
import com.viplogistics.entity.transaction.dto.NagpurFreightBillDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NagpurFreightBillDtoHelper {

    private List<NagpurFreightBillDto> nagpurFreightBillDtos;

    private CommonFreightBillDataDto commonFreightBillDataDto;
}
