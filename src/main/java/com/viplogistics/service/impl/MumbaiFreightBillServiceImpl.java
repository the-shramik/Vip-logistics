package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.MumbaiBillReport;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.MumbaiFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.MumbaiFreightBillDtoHelper;
import com.viplogistics.entity.transaction.helper.ExtraCharges;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.repository.IMumbaiFreightBillRepository;
import com.viplogistics.service.IMumbaiFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MumbaiFreightBillServiceImpl implements IMumbaiFreightBillService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final IMumbaiFreightBillRepository mumbaiFreightBillRepository;

    @Override
    public MumbaiFreightBillDtoHelper getMumbaiFreightBill(String billNo,String routeName) throws ResourceNotFoundException {

        try{

            List<MumbaiFreightBillDto> mumbaiFreightBillDtos=new ArrayList<>();

            lorryReceiptRepository.findByBillNoAndMemoStatusAndRouteName(billNo,routeName)
                    .forEach(lorryReceipt -> {
                        lorryReceipt.getLorryReceiptItems().forEach(lorryReceiptItem -> {
                            MumbaiFreightBillDto mumbaiFreightBillDto=new MumbaiFreightBillDto();

                            mumbaiFreightBillDto.setLrId(lorryReceipt.getLrId());
                            mumbaiFreightBillDto.setLrNo(lorryReceipt.getLrNo());
                            mumbaiFreightBillDto.setLrDate(lorryReceipt.getLrDate());

                            mumbaiFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                            mumbaiFreightBillDto.setFrom(lorryReceipt.getRoute().getRouteFrom());
                            mumbaiFreightBillDto.setTo(lorryReceipt.getRoute().getRouteTo());
                            mumbaiFreightBillDto.setInvoiceNo(lorryReceiptItem.getChalanNo());
                            mumbaiFreightBillDto.setVendorName(lorryReceiptItem.getItem().getParty().getPartyName());
                            mumbaiFreightBillDto.setQuantity(lorryReceiptItem.getQuantity());
                            mumbaiFreightBillDto.setVehicleNo(lorryReceipt.getRefTruckNo());
                            mumbaiFreightBillDto.setRate(lorryReceiptItem.getLcvFtl());
                            mumbaiFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());


                            mumbaiFreightBillDto.setStCharges(lorryReceipt.getStCharges());
                            double totalExtraCharges = lorryReceipt.getExtraCharges().stream()
                                    .mapToDouble(ExtraCharges::getChargesAmount)
                                    .sum();

                            double subTotal = lorryReceiptItem.getTotalFreight() + totalExtraCharges;
                            mumbaiFreightBillDto.setSubTotal(subTotal);


                            mumbaiFreightBillDto.setCgst(lorryReceiptItem.getCgst());
                            mumbaiFreightBillDto.setSgst(lorryReceiptItem.getSgst());
                            mumbaiFreightBillDto.setTotalBillValue(null);


                            lorryReceipt.getExtraCharges()
                                    .forEach(extraCharges -> {
                                        if(extraCharges.getChargesHeads().equals("LOADING_CHARGES")){
                                            mumbaiFreightBillDto.setLoadingCharges(extraCharges.getChargesAmount());
                                        }else if(extraCharges.getChargesHeads().equals("UNLOADING_CHARGES")){
                                            mumbaiFreightBillDto.setUnloadingCharges(extraCharges.getChargesAmount());
                                        }else if(extraCharges.getChargesHeads().equals("PLYWOOD_CHARGES")){
                                            mumbaiFreightBillDto.setPlyWoodCharges(extraCharges.getChargesAmount());
                                        } else if (extraCharges.getChargesHeads().equals("DETENTION_CHARGES")) {
                                            mumbaiFreightBillDto.setDetentionCharges(extraCharges.getChargesAmount());
                                        }
                                        else {
                                            mumbaiFreightBillDto.setLoadingCharges(null);
                                            mumbaiFreightBillDto.setUnloadingCharges(null);
                                            mumbaiFreightBillDto.setPlyWoodCharges(null);
                                            mumbaiFreightBillDto.setDetentionCharges(null);
                                        }
                                    });
                            mumbaiFreightBillDtos.add(mumbaiFreightBillDto);
                        });


                    });

            MumbaiFreightBillDtoHelper mumbaiFreightBillDtoHelper=new MumbaiFreightBillDtoHelper();
            mumbaiFreightBillDtoHelper.setMumbaiFreightBillDtos(mumbaiFreightBillDtos);

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
                mumbaiFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);

            } else if (lorryReceipt.getWhoPay().equals("Consignee")) {
                CommonFreightBillDataDto commonFreightBillDataDto=new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignee().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignee().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignee().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignee().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignee().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                mumbaiFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);

            }else {
                mumbaiFreightBillDtoHelper.setCommonFreightBillDataDto(null);
            }
            return mumbaiFreightBillDtoHelper;
        }catch (Exception e){
            throw new ResourceNotFoundException("Freight bills are not found with this bill no");
        }
    }

    @Override
    public MumbaiBillReport saveMumbaiFreightBill(MumbaiBillReport mumbaiBillReport) {
        return mumbaiFreightBillRepository.save(mumbaiBillReport);
    }

    @Override
    public MumbaiBillReport updateMumbaiFreightBill(MumbaiBillReport mumbaiBillReport) throws ResourceNotFoundException {
        MumbaiBillReport existedMumbaiBillReport = mumbaiFreightBillRepository.findById(mumbaiBillReport.getFreightBillReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Freight Bill Not Found"));

        existedMumbaiBillReport.setIsVerified(mumbaiBillReport.getIsVerified());
        existedMumbaiBillReport.setMl(mumbaiBillReport.getMl());
        existedMumbaiBillReport.setSac(mumbaiBillReport.getSac());
        existedMumbaiBillReport.setCodeNo(mumbaiBillReport.getCodeNo());
        existedMumbaiBillReport.setBillNo(mumbaiBillReport.getBillNo());
        existedMumbaiBillReport.setPartyName(mumbaiBillReport.getPartyName());
        existedMumbaiBillReport.setAddress(mumbaiBillReport.getAddress());
        existedMumbaiBillReport.setDistrict(mumbaiBillReport.getDistrict());
        existedMumbaiBillReport.setStateCode(mumbaiBillReport.getStateCode());
        existedMumbaiBillReport.setGstNo(mumbaiBillReport.getGstNo());
        existedMumbaiBillReport.setRouteName(mumbaiBillReport.getRouteName());
        existedMumbaiBillReport.setTelephoneNo(mumbaiBillReport.getTelephoneNo());

        return mumbaiFreightBillRepository.save(existedMumbaiBillReport);

    }

    @Override
    public ApiResponse<?> deleteMumbaiFreightBill(Long freightBillReportId) {
        Optional<MumbaiBillReport> optionalMumbaiBillReport = mumbaiFreightBillRepository.findById(freightBillReportId);

        if(optionalMumbaiBillReport.isPresent()){
            mumbaiFreightBillRepository.delete(optionalMumbaiBillReport.get());
            return new ApiResponse<>(true,"Report deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Report not deleted",null, HttpStatus.NOT_FOUND);
        }
    }
}
