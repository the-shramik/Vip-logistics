package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.NagpurFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.NagpurFreightBillDtoHelper;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.repository.INagpurFreightBillRepository;
import com.viplogistics.service.INagpurFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NagpurFreightBillServiceImpl implements INagpurFreightBillService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final INagpurFreightBillRepository nagpurFreightBillRepository;

    @Override
    public NagpurFreightBillDtoHelper getNagpurFreightBill(String billNo,String routeName) throws ResourceNotFoundException {
        try{
            List<NagpurFreightBillDto> nagpurFreightBillDtos=new ArrayList<>();

            lorryReceiptRepository.findByBillNoAndMemoStatusAndRouteName(billNo,routeName)
                    .forEach(lorryReceipt -> {
                        lorryReceipt.getLorryReceiptItems()
                                .forEach(lorryReceiptItem -> {
                                    NagpurFreightBillDto nagpurFreightBillDto=new NagpurFreightBillDto();

                                    nagpurFreightBillDto.setLrId(lorryReceipt.getLrId());
                                    nagpurFreightBillDto.setLrNo(lorryReceipt.getLrNo());
                                    nagpurFreightBillDto.setLrDate(lorryReceipt.getLrDate());
                                    nagpurFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                    nagpurFreightBillDto.setVendorName(lorryReceiptItem.getItem().getParty().getPartyName());
                                    nagpurFreightBillDto.setVehicleNo(lorryReceipt.getRefTruckNo());
                                    nagpurFreightBillDto.setWeight(lorryReceiptItem.getTotalWeight());
                                    nagpurFreightBillDto.setVehicleType(null);
                                    nagpurFreightBillDto.setRate(lorryReceiptItem.getLcvFtl());
                                    nagpurFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());

                                    nagpurFreightBillDto.setStCharges(lorryReceipt.getStCharges());

                                    nagpurFreightBillDto.setCgst(null);
                                    nagpurFreightBillDto.setSgst(null);
                                    nagpurFreightBillDto.setTotalBillValue(null);

                                    nagpurFreightBillDto.setPlyWoodCharges(null);
                                    nagpurFreightBillDto.setCollectionCharges(null);
                                    lorryReceipt.getExtraCharges()
                                            .forEach(extraCharges -> {
                                                if(extraCharges.getChargesHeads().equals("COLLECTION_CHARGES")){
                                                    nagpurFreightBillDto.setCollectionCharges(extraCharges.getChargesAmount());
                                                }else if(extraCharges.getChargesHeads().equals("UNLOADING_CHARGES")){
                                                    nagpurFreightBillDto.setUnloadingCharges(extraCharges.getChargesAmount());
                                                }else if(extraCharges.getChargesHeads().equals("PLYWOOD_CHARGES")){
                                                    nagpurFreightBillDto.setPlyWoodCharges(extraCharges.getChargesAmount());
                                                }else if(extraCharges.getChargesHeads().equals("LR_CHARGES")){
                                                    nagpurFreightBillDto.setLrCharges(extraCharges.getChargesAmount());
                                                }
                                                else {
                                                    nagpurFreightBillDto.setCollectionCharges(null);
                                                    nagpurFreightBillDto.setUnloadingCharges(null);
                                                    nagpurFreightBillDto.setPlyWoodCharges(null);
                                                    nagpurFreightBillDto.setLrCharges(null);
                                                }
                                            });
                                    nagpurFreightBillDtos.add(nagpurFreightBillDto);

                                });
                    });

            NagpurFreightBillDtoHelper nagpurFreightBillDtoHelper=new NagpurFreightBillDtoHelper();
            nagpurFreightBillDtoHelper.setNagpurFreightBillDtos(nagpurFreightBillDtos);

            LorryReceipt lorryReceipt=lorryReceiptRepository.findByBillNoAndMemoStatusAndRouteName(billNo,routeName).stream().findFirst().get();
            if(lorryReceipt.getWhoPay().equals("Consignor")){
                CommonFreightBillDataDto commonFreightBillDataDto=new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignor().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignor().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignor().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignor().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignor().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                nagpurFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);

            } else if (lorryReceipt.getWhoPay().equals("Consignee")) {
                CommonFreightBillDataDto commonFreightBillDataDto=new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignee().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignee().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignee().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignee().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignee().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                nagpurFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);
            }else {
                nagpurFreightBillDtoHelper.setCommonFreightBillDataDto(null);
            }

            return nagpurFreightBillDtoHelper;
        }catch (Exception e){
            throw new ResourceNotFoundException("Freight bills are not found with this bill no");
        }
    }

    @Override
    public NagpurBillReport saveNagpurFreightBill(NagpurBillReport nagpurBillReport) {
        return nagpurFreightBillRepository.save(nagpurBillReport);
    }

    @Override
    public NagpurBillReport updateNagpurFreightBill(NagpurBillReport nagpurBillReport) throws ResourceNotFoundException {
        NagpurBillReport existedNagpurBillReport = nagpurFreightBillRepository.findById(nagpurBillReport.getFreightBillReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Freight Bill Not Found"));

        existedNagpurBillReport.setIsVerified(nagpurBillReport.getIsVerified());
        existedNagpurBillReport.setMl(nagpurBillReport.getMl());
        existedNagpurBillReport.setSac(nagpurBillReport.getSac());
        existedNagpurBillReport.setCodeNo(nagpurBillReport.getCodeNo());
        existedNagpurBillReport.setPartyName(nagpurBillReport.getPartyName());
        existedNagpurBillReport.setAddress(nagpurBillReport.getAddress());
        existedNagpurBillReport.setDistrict(nagpurBillReport.getDistrict());
        existedNagpurBillReport.setStateCode(nagpurBillReport.getStateCode());
        existedNagpurBillReport.setGstNo(nagpurBillReport.getGstNo());
        existedNagpurBillReport.setRouteName(nagpurBillReport.getRouteName());
        existedNagpurBillReport.setTelephoneNo(nagpurBillReport.getTelephoneNo());

        return nagpurFreightBillRepository.save(existedNagpurBillReport);

    }

    @Override
    public ApiResponse<?> deleteNagpurFreightBill(Long freightBillReportId) {
        Optional<NagpurBillReport> optionalNagpurBillReport = nagpurFreightBillRepository.findById(freightBillReportId);

        if(optionalNagpurBillReport.isPresent()){
            nagpurFreightBillRepository.delete(optionalNagpurBillReport.get());
            return new ApiResponse<>(true,"Report deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Report not deleted",null, HttpStatus.NOT_FOUND);
        }
    }


}
