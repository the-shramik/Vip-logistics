package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.RudrapurBillReport;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.RudrapurFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.RudrapurFreightBillDtoHelper;
import com.viplogistics.entity.transaction.dto.helper.extracharges.RudrapurExtraCharges;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.repository.IRudrapurFreightBillRepository;
import com.viplogistics.service.IRudrapurFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RudrapurFreightBillServiceImpl implements IRudrapurFreightBillService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final IRudrapurFreightBillRepository rudrapurFreightBillRepository;

    @Override
    public RudrapurFreightBillDtoHelper getRudrapurFreightBill(String billNo,String routeName) throws ResourceNotFoundException {
        try{
            RudrapurFreightBillDtoHelper rudrapurFreightBillDtoHelper=new RudrapurFreightBillDtoHelper();

            List<RudrapurFreightBillDto> rudrapurFreightBillDtos=new ArrayList<>();

            List<RudrapurExtraCharges> rudrapurExtraChargesList=new ArrayList<>();

            lorryReceiptRepository.findByBillNoAndRouteName(billNo,routeName)
                    .forEach(lorryReceipt -> {

                        lorryReceipt.getLorryReceiptItems()
                                .forEach(lorryReceiptItem -> {
                                    RudrapurFreightBillDto rudrapurFreightBillDto=new RudrapurFreightBillDto();

                                    rudrapurFreightBillDto.setLrId(lorryReceipt.getLrId());
                                    rudrapurFreightBillDto.setSac(null);
                                    rudrapurFreightBillDto.setLrNo(lorryReceipt.getLrNo());
                                    rudrapurFreightBillDto.setLrDate(lorryReceipt.getLrDate());
                                    rudrapurFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                    rudrapurFreightBillDto.setFrom(lorryReceipt.getRoute().getRouteFrom());
                                    rudrapurFreightBillDto.setTo(lorryReceipt.getRoute().getRouteTo());
                                    rudrapurFreightBillDto.setInvoiceNo(lorryReceiptItem.getChalanNo());
                                    rudrapurFreightBillDto.setSupplierName(lorryReceiptItem.getItem().getParty().getPartyName());
                                    rudrapurFreightBillDto.setWeight(lorryReceiptItem.getTotalWeight());
                                    rudrapurFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                    rudrapurFreightBillDto.setGrandTotal(lorryReceipt.getGrandTotal());

                                    rudrapurFreightBillDto.setTaxableAmt(null);
                                    rudrapurFreightBillDto.setIgst(lorryReceiptItem.getIgst());
                                    rudrapurFreightBillDto.setRoundOff(null);
                                    rudrapurFreightBillDto.setTotalBillValue(null);

                                    rudrapurFreightBillDtos.add(rudrapurFreightBillDto);

                                });

                        RudrapurExtraCharges rudrapurExtraCharges=new RudrapurExtraCharges();
                        rudrapurExtraCharges.setLrNo(lorryReceipt.getLrNo());
                        rudrapurExtraCharges.setStCharges(lorryReceipt.getStCharges());
                        lorryReceipt.getExtraCharges().forEach(extraCharges -> {
                            if(extraCharges.getChargesHeads().equals("DETENTION_CHARGES")){
                                rudrapurExtraCharges.setDetentionCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("LOADING_CHARGES")){
                                rudrapurExtraCharges.setLoadingCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("LR_CHARGES")){
                                rudrapurExtraCharges.setLrCharges(extraCharges.getChargesAmount());
                            }

                            if(rudrapurExtraChargesList.isEmpty()){
                                rudrapurExtraChargesList.add(rudrapurExtraCharges);
                            }
                            else if(!rudrapurExtraChargesList.get(rudrapurExtraChargesList.size()-1).getLrNo().equals(lorryReceipt.getLrNo())){
                                rudrapurExtraChargesList.add(rudrapurExtraCharges);
                            }
                        });

                    });

            rudrapurFreightBillDtoHelper.setRudrapurExtraCharges(rudrapurExtraChargesList);
            rudrapurFreightBillDtoHelper.setRudrapurFreightBillDtos(rudrapurFreightBillDtos);

            LorryReceipt lorryReceipt=lorryReceiptRepository.findByBillNoAndRouteName(billNo,routeName).stream().findFirst().get();

            if(lorryReceipt.getWhoPay().equals("Consignor")){
                CommonFreightBillDataDto commonFreightBillDataDto=new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignor().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignor().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignor().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignor().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignor().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                rudrapurFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);

            } else if (lorryReceipt.getWhoPay().equals("Consignee")) {
                CommonFreightBillDataDto commonFreightBillDataDto=new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignee().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignee().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignee().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignee().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignee().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                rudrapurFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);
            }else {
                rudrapurFreightBillDtoHelper.setCommonFreightBillDataDto(null);
            }

            return rudrapurFreightBillDtoHelper;
        }catch (Exception e){
            throw new ResourceNotFoundException("Freight bills are not found with this bill no");
        }
    }

    @Override
    public RudrapurBillReport saveRudrapurFreightBill(RudrapurBillReport rudrapurBillReport) throws BillAlreadySavedException {
        if(!rudrapurFreightBillRepository.existsByBillNo(rudrapurBillReport.getBillNo())) {
            return rudrapurFreightBillRepository.save(rudrapurBillReport);
        }else{
            throw new BillAlreadySavedException("Bill already saved");
        }
    }

    @Override
    public RudrapurBillReport updateRudrapurFreightBill(RudrapurBillReport rudrapurBillReport) throws ResourceNotFoundException {
        RudrapurBillReport existedRudrapurBillReport = rudrapurFreightBillRepository.findById(rudrapurBillReport.getFreightBillReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Freight Bill Not Found"));

        existedRudrapurBillReport.setIsVerified(rudrapurBillReport.getIsVerified());
        existedRudrapurBillReport.setMl(rudrapurBillReport.getMl());
        existedRudrapurBillReport.setSac(rudrapurBillReport.getSac());
        existedRudrapurBillReport.setCodeNo(rudrapurBillReport.getCodeNo());
        existedRudrapurBillReport.setPartyName(rudrapurBillReport.getPartyName());
        existedRudrapurBillReport.setAddress(rudrapurBillReport.getAddress());
        existedRudrapurBillReport.setDistrict(rudrapurBillReport.getDistrict());
        existedRudrapurBillReport.setStateCode(rudrapurBillReport.getStateCode());
        existedRudrapurBillReport.setGstNo(rudrapurBillReport.getGstNo());
        existedRudrapurBillReport.setRouteName(rudrapurBillReport.getRouteName());
        existedRudrapurBillReport.setTelephoneNo(rudrapurBillReport.getTelephoneNo());

        return rudrapurFreightBillRepository.save(rudrapurBillReport);

    }

    @Override
    public ApiResponse<?> deleteRudrapurFreightBill(Long freightBillReportId) {
        Optional<RudrapurBillReport> optionalRudrapurBillReport = rudrapurFreightBillRepository.findById(freightBillReportId);

        if(optionalRudrapurBillReport.isPresent()){
            rudrapurFreightBillRepository.delete(optionalRudrapurBillReport.get());
            return new ApiResponse<>(true,"Report deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Report not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<RudrapurBillReport> getAllRudrapurFreightBills() {
        return rudrapurFreightBillRepository.findAll();
    }

}
