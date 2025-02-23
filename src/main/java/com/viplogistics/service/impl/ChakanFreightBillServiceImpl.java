package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.dto.ChakanFreightBillDto;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.helper.ChakanFreightBillDtoHelper;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IChakanFreightBillRepository;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.service.IChakanFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChakanFreightBillServiceImpl implements IChakanFreightBillService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final IChakanFreightBillRepository chakanFreightBillRepository;

    @Override
    public ChakanFreightBillDtoHelper getChakanFreightBill(String billNo,String routeName) throws ResourceNotFoundException {

        try {

            List<ChakanFreightBillDto> chakanFreightBillDtos = new ArrayList<>();

            lorryReceiptRepository.findByBillNoAndMemoStatusAndRouteName(billNo,routeName)
                    .forEach(lorryReceipt -> {
                        lorryReceipt.getLorryReceiptItems()
                                .forEach(lorryReceiptItem -> {
                                    ChakanFreightBillDto chakanFreightBillDto = new ChakanFreightBillDto();

                                    chakanFreightBillDto.setLrId(lorryReceipt.getLrId());
                                    chakanFreightBillDto.setLrNo(lorryReceipt.getLrNo());
                                    chakanFreightBillDto.setLrDate(lorryReceipt.getLrDate());
                                    chakanFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                    chakanFreightBillDto.setFrom(lorryReceipt.getRoute().getRouteFrom());
                                    chakanFreightBillDto.setTo(lorryReceipt.getRoute().getRouteTo());
                                    chakanFreightBillDto.setConsignor(lorryReceipt.getConsignor().getPartyName());
                                    chakanFreightBillDto.setConsignee(lorryReceipt.getConsignee().getPartyName());
                                    chakanFreightBillDto.setInvoiceNo(lorryReceiptItem.getChalanNo());
                                    chakanFreightBillDto.setVehicleNo(lorryReceipt.getRefTruckNo());
                                    chakanFreightBillDto.setVehicleType(lorryReceiptItem.getLcvFtl());
                                    chakanFreightBillDto.setTripNo(null);
                                    chakanFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());

                                    chakanFreightBillDto.setCgst(lorryReceiptItem.getCgst());
                                    chakanFreightBillDto.setSgst(lorryReceiptItem.getSgst());
                                    chakanFreightBillDto.setTotalBillValue(null);

                                    lorryReceipt.getExtraCharges()
                                                    .forEach(extraCharges -> {
                                                        if(extraCharges.getChargesHeads().equals("UNLOADING_CHARGES")){
                                                            chakanFreightBillDto.setUnloadingCharges(extraCharges.getChargesAmount());
                                                        }else if(extraCharges.getChargesHeads().equals("LR_CHARGES")){
                                                            chakanFreightBillDto.setLrCharges(extraCharges.getChargesAmount());
                                                        }else {
                                                            chakanFreightBillDto.setUnloadingCharges(null);
                                                            chakanFreightBillDto.setLrCharges(null);
                                                        }
                                                    });
                                    chakanFreightBillDtos.add(chakanFreightBillDto);

                                });
                    });

            ChakanFreightBillDtoHelper chakanFreightBillDtoHelper = new ChakanFreightBillDtoHelper();
            chakanFreightBillDtoHelper.setChakanFreightBillDtos(chakanFreightBillDtos);

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
                chakanFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);

            } else if (lorryReceipt.getWhoPay().equals("Consignee")) {
                CommonFreightBillDataDto commonFreightBillDataDto = new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignee().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignee().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignee().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignee().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignee().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                chakanFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);
            } else {
                chakanFreightBillDtoHelper.setCommonFreightBillDataDto(null);
            }

            return chakanFreightBillDtoHelper;
        }catch (Exception e){
            throw new ResourceNotFoundException("Freight bills are not found with this bill no");
        }
    }

    @Override
    public ChakanBillReport saveChakanFreightBill(ChakanBillReport chakanBillReport) {
        return chakanFreightBillRepository.save(chakanBillReport);
    }

    @Override
    public ChakanBillReport updateChakanFreightBill(ChakanBillReport chakanBillReport) throws ResourceNotFoundException {
        ChakanBillReport existedChakanBillReport = chakanFreightBillRepository.findById(chakanBillReport.getFreightBillReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Freight Bill Not Found"));

        existedChakanBillReport.setIsVerified(chakanBillReport.getIsVerified());
        existedChakanBillReport.setMl(chakanBillReport.getMl());
        existedChakanBillReport.setSac(chakanBillReport.getSac());
        existedChakanBillReport.setCodeNo(chakanBillReport.getCodeNo());
        existedChakanBillReport.setBillNo(chakanBillReport.getBillNo());
        existedChakanBillReport.setPartyName(chakanBillReport.getPartyName());
        existedChakanBillReport.setAddress(chakanBillReport.getAddress());
        existedChakanBillReport.setDistrict(chakanBillReport.getDistrict());
        existedChakanBillReport.setStateCode(chakanBillReport.getStateCode());
        existedChakanBillReport.setGstNo(chakanBillReport.getGstNo());
        existedChakanBillReport.setRouteName(chakanBillReport.getRouteName());
        existedChakanBillReport.setTelephoneNo(chakanBillReport.getTelephoneNo());

        return chakanFreightBillRepository.save(existedChakanBillReport);

    }

    @Override
    public ApiResponse<?> deleteChakanFreightBill(Long freightBillReportId) {
        Optional<ChakanBillReport> optionalChakanBillReport = chakanFreightBillRepository.findById(freightBillReportId);

        if(optionalChakanBillReport.isPresent()){
            chakanFreightBillRepository.delete(optionalChakanBillReport.get());
            return new ApiResponse<>(true,"Report deleted",null,HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Report not deleted",null, HttpStatus.NOT_FOUND);
        }
    }


}
