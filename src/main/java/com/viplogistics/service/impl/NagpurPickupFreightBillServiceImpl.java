package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.NagpurPickupBillReport;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.NagpurPickupFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.NagpurPickupFreightBillDtoHelper;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.repository.INagpurPickupFreightBillRepository;
import com.viplogistics.service.INagpurPickupFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NagpurPickupFreightBillServiceImpl implements INagpurPickupFreightBillService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final INagpurPickupFreightBillRepository nagpurPickupFreightBillRepository;

    @Override
    public NagpurPickupFreightBillDtoHelper getNagpurPickupFreightBill(String billNo,String routeName) throws ResourceNotFoundException {
        try {

            List<NagpurPickupFreightBillDto> nagpurPickupFreightBillDtos = new ArrayList<>();

            lorryReceiptRepository.findByBillNoAndMemoStatusAndRouteName(billNo,routeName)
                    .forEach(lorryReceipt -> {
                        lorryReceipt.getLorryReceiptItems()
                                .forEach(lorryReceiptItem -> {
                                    NagpurPickupFreightBillDto nagpurPickupFreightBillDto = new NagpurPickupFreightBillDto();

                                    nagpurPickupFreightBillDto.setLrId(lorryReceipt.getLrId());
                                    nagpurPickupFreightBillDto.setLrNo(lorryReceipt.getLrNo());
                                    nagpurPickupFreightBillDto.setLrDate(lorryReceipt.getLrDate());
                                    nagpurPickupFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                    nagpurPickupFreightBillDto.setVendorName(lorryReceiptItem.getItem().getParty().getPartyName());
                                    nagpurPickupFreightBillDto.setWeight(lorryReceiptItem.getTotalWeight());
                                    nagpurPickupFreightBillDto.setVehicleNo(lorryReceipt.getRefTruckNo());
                                    nagpurPickupFreightBillDto.setVehicleType(null);
                                    nagpurPickupFreightBillDto.setRate(lorryReceiptItem.getLcvFtl());
                                    nagpurPickupFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());

                                    nagpurPickupFreightBillDto.setStCharges(lorryReceipt.getStCharges());
                                    nagpurPickupFreightBillDto.setCgst(null);
                                    nagpurPickupFreightBillDto.setSgst(null);
                                    nagpurPickupFreightBillDto.setTotalBillValue(null);

                                    lorryReceipt.getExtraCharges()
                                            .forEach(extraCharges -> {
                                                if(extraCharges.getChargesHeads().equals("COLLECTION_CHARGES")){
                                                    nagpurPickupFreightBillDto.setCollectionCharges(extraCharges.getChargesAmount());
                                                }else if(extraCharges.getChargesHeads().equals("LOADING_CHARGES")){
                                                    nagpurPickupFreightBillDto.setLoadingCharges(extraCharges.getChargesAmount());
                                                }else if(extraCharges.getChargesHeads().equals("PLYWOOD_CHARGES")){
                                                    nagpurPickupFreightBillDto.setPlyWoodCharges(extraCharges.getChargesAmount());
                                                }
                                                else {
                                                    nagpurPickupFreightBillDto.setLoadingCharges(null);
                                                    nagpurPickupFreightBillDto.setPlyWoodCharges(null);
                                                    nagpurPickupFreightBillDto.setCollectionCharges(null);
                                                }
                                            });
                                    nagpurPickupFreightBillDtos.add(nagpurPickupFreightBillDto);

                                });
                    });

            NagpurPickupFreightBillDtoHelper nagpurPickupFreightBillDtoHelper = new NagpurPickupFreightBillDtoHelper();
            nagpurPickupFreightBillDtoHelper.setNagpurPickupFreightBillDtos(nagpurPickupFreightBillDtos);

            LorryReceipt lorryReceipt = lorryReceiptRepository.findByBillNoAndMemoStatusAndRouteName(billNo,routeName).stream().findFirst().get();
            if (lorryReceipt.getWhoPay().equals("Consignor")) {
                CommonFreightBillDataDto commonFreightBillDataDto = new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignor().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignor().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignor().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignor().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignor().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                nagpurPickupFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);

            } else if (lorryReceipt.getWhoPay().equals("Consignee")) {
                CommonFreightBillDataDto commonFreightBillDataDto = new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignee().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignee().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignee().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignee().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignee().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                nagpurPickupFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);
            } else {
                nagpurPickupFreightBillDtoHelper.setCommonFreightBillDataDto(null);
            }


            return nagpurPickupFreightBillDtoHelper;
        }catch (Exception e){
            throw new ResourceNotFoundException("Freight bills are not found with this bill no");
        }
    }

    @Override
    public NagpurPickupBillReport saveNagpurPickupFreightBill(NagpurPickupBillReport nagpurPickupBillReport) {
        return nagpurPickupFreightBillRepository.save(nagpurPickupBillReport);
    }

    @Override
    public NagpurPickupBillReport updateNagpurPickupFreightBill(NagpurPickupBillReport nagpurPickupBillReport) throws ResourceNotFoundException {
        NagpurPickupBillReport existedNagpurPickupBillReport = nagpurPickupFreightBillRepository.findById(nagpurPickupBillReport.getFreightBillReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Freight Bill Not Found"));

        existedNagpurPickupBillReport.setIsVerified(nagpurPickupBillReport.getIsVerified());
        existedNagpurPickupBillReport.setMl(nagpurPickupBillReport.getMl());
        existedNagpurPickupBillReport.setSac(nagpurPickupBillReport.getSac());
        existedNagpurPickupBillReport.setCodeNo(nagpurPickupBillReport.getCodeNo());
        existedNagpurPickupBillReport.setPartyName(nagpurPickupBillReport.getPartyName());
        existedNagpurPickupBillReport.setAddress(nagpurPickupBillReport.getAddress());
        existedNagpurPickupBillReport.setDistrict(nagpurPickupBillReport.getDistrict());
        existedNagpurPickupBillReport.setStateCode(nagpurPickupBillReport.getStateCode());
        existedNagpurPickupBillReport.setGstNo(nagpurPickupBillReport.getGstNo());
        existedNagpurPickupBillReport.setRouteName(nagpurPickupBillReport.getRouteName());
        existedNagpurPickupBillReport.setTelephoneNo(nagpurPickupBillReport.getTelephoneNo());

        return nagpurPickupFreightBillRepository.save(existedNagpurPickupBillReport);

    }

    @Override
    public ApiResponse<?> deleteNagpurPickupFreightBill(Long freightBillReportId) {
        Optional<NagpurPickupBillReport> optionalNagpurPickupBillReport = nagpurPickupFreightBillRepository.findById(freightBillReportId);

        if(optionalNagpurPickupBillReport.isPresent()){
            nagpurPickupFreightBillRepository.delete(optionalNagpurPickupBillReport.get());
            return new ApiResponse<>(true,"Report deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Report not deleted",null, HttpStatus.NOT_FOUND);
        }
    }


}
