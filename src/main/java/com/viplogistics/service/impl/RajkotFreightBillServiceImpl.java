package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.RajkotBillReport;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.RajkotFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.RajkotFreightBillDtoHelper;
import com.viplogistics.entity.transaction.dto.helper.extracharges.RajkotExtraCharges;
import com.viplogistics.entity.transaction.helper.ExtraCharges;
import com.viplogistics.exception.BillAlreadySavedException;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.repository.IRajkotFreightBillRepository;
import com.viplogistics.service.IRajkotFreightBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RajkotFreightBillServiceImpl implements IRajkotFreightBillService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final IRajkotFreightBillRepository rajkotFreightBillRepository;

    @Override
    public RajkotFreightBillDtoHelper getRajkotFreightBill(String billNo,String routeName) throws ResourceNotFoundException {

        try {
            RajkotFreightBillDtoHelper rajkotFreightBillDtoHelper=new RajkotFreightBillDtoHelper();

            List<RajkotFreightBillDto> rajkotFreightBillDtos=new ArrayList<>();

            List<RajkotExtraCharges> rajkotExtraChargesList=new ArrayList<>();
            lorryReceiptRepository.findByBillNoAndMemoStatusAndRouteName(billNo,routeName)
                    .forEach(lorryReceipt -> {

                        lorryReceipt.getLorryReceiptItems()
                                .forEach(lorryReceiptItem -> {
                                    RajkotFreightBillDto rajkotFreightBillDto=new RajkotFreightBillDto();

                                    rajkotFreightBillDto.setLrId(lorryReceipt.getLrId());
                                    rajkotFreightBillDto.setLrNo(lorryReceipt.getLrNo());
                                    rajkotFreightBillDto.setLrDate(lorryReceipt.getLrDate());
                                    rajkotFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                    rajkotFreightBillDto.setFrom(lorryReceipt.getRoute().getRouteFrom());
                                    rajkotFreightBillDto.setTo(lorryReceipt.getRoute().getRouteTo());
                                    rajkotFreightBillDto.setInvoiceNo(lorryReceiptItem.getChalanNo());
                                    rajkotFreightBillDto.setVendorName(lorryReceiptItem.getItem().getParty().getPartyName());
                                    rajkotFreightBillDto.setQuantity(lorryReceiptItem.getQuantity());
                                    rajkotFreightBillDto.setWeight(lorryReceiptItem.getTotalWeight());
                                    rajkotFreightBillDto.setVehicleNo(lorryReceipt.getRefTruckNo());
                                    rajkotFreightBillDto.setRate(lorryReceiptItem.getLcvFtl());
                                    rajkotFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                    rajkotFreightBillDto.setGrandTotal(lorryReceipt.getGrandTotal());

                                    double totalExtraCharges = lorryReceipt.getExtraCharges().stream()
                                            .mapToDouble(ExtraCharges::getChargesAmount)
                                            .sum();

                                    double subTotal = lorryReceiptItem.getTotalFreight() + totalExtraCharges;
                                    rajkotFreightBillDto.setSubTotal(subTotal);

                                    rajkotFreightBillDto.setIgst(lorryReceiptItem.getIgst());

                                    rajkotFreightBillDto.setTotalBillValue(null);


                                    rajkotFreightBillDtos.add(rajkotFreightBillDto);
                                });
                        RajkotExtraCharges rajkotExtraCharges =new RajkotExtraCharges();
                        rajkotExtraCharges.setLrNo(lorryReceipt.getLrNo());
                        rajkotExtraCharges.setStCharges(lorryReceipt.getStCharges());
                        lorryReceipt.getExtraCharges().forEach(extraCharges -> {
                            if(extraCharges.getChargesHeads().equals("DETENTION_CHARGES")){
                                rajkotExtraCharges.setDetentionCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("LOADING_CHARGES")){
                                rajkotExtraCharges.setLoadingCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("PLYWOOD_CHARGES")){
                                rajkotExtraCharges.setPlyWoodCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("UNLOADING_CHARGES")){
                                rajkotExtraCharges.setUnloadingCharges(extraCharges.getChargesAmount());
                            }

                            if(rajkotExtraChargesList.isEmpty()){
                                rajkotExtraChargesList.add(rajkotExtraCharges);
                            }
                            else if(!rajkotExtraChargesList.get(rajkotExtraChargesList.size()-1).getLrNo().equals(lorryReceipt.getLrNo())){
                                rajkotExtraChargesList.add(rajkotExtraCharges);
                            }
                        });
                    });

            rajkotFreightBillDtoHelper.setRajkotExtraCharges(rajkotExtraChargesList);
            rajkotFreightBillDtoHelper.setRajkotFreightBillDtos(rajkotFreightBillDtos);

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
                rajkotFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);

            } else if (lorryReceipt.getWhoPay().equals("Consignee")) {
                CommonFreightBillDataDto commonFreightBillDataDto=new CommonFreightBillDataDto();
                commonFreightBillDataDto.setPartyName(lorryReceipt.getConsignee().getPartyName());
                commonFreightBillDataDto.setDistrict(lorryReceipt.getConsignee().getDistrict());
                commonFreightBillDataDto.setAddress(lorryReceipt.getConsignee().getAddress());
                commonFreightBillDataDto.setGstNo(lorryReceipt.getConsignee().getGstNumber());
                commonFreightBillDataDto.setStateCode(lorryReceipt.getConsignee().getStateCode());
                commonFreightBillDataDto.setBillNo(lorryReceipt.getBill().getBillNo());
                commonFreightBillDataDto.setBillDate(lorryReceipt.getBill().getBillDate());
                rajkotFreightBillDtoHelper.setCommonFreightBillDataDto(commonFreightBillDataDto);
            }else {
                rajkotFreightBillDtoHelper.setCommonFreightBillDataDto(null);
            }


            return rajkotFreightBillDtoHelper;
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResourceNotFoundException("Freight bills are not found with this bill no");
        }
    }

    @Override
    public RajkotBillReport saveRajkotFreightBill(RajkotBillReport rajkotBillReport) throws BillAlreadySavedException {
        if(!rajkotFreightBillRepository.existsByBillNo(rajkotBillReport.getBillNo())) {
            return rajkotFreightBillRepository.save(rajkotBillReport);
        }else{
            throw new BillAlreadySavedException("Bill already saved");
        }
    }

    @Override
    public RajkotBillReport updateRajkotFreightBill(RajkotBillReport rajkotBillReport) throws ResourceNotFoundException {
        RajkotBillReport existedRajkotBillReport = rajkotFreightBillRepository.findById(rajkotBillReport.getFreightBillReportId())
                .orElseThrow(() -> new ResourceNotFoundException("Freight Bill Not Found"));

        existedRajkotBillReport.setIsVerified(rajkotBillReport.getIsVerified());
        existedRajkotBillReport.setMl(rajkotBillReport.getMl());
        existedRajkotBillReport.setSac(rajkotBillReport.getSac());
        existedRajkotBillReport.setCodeNo(rajkotBillReport.getCodeNo());
        existedRajkotBillReport.setPartyName(rajkotBillReport.getPartyName());
        existedRajkotBillReport.setAddress(rajkotBillReport.getAddress());
        existedRajkotBillReport.setDistrict(rajkotBillReport.getDistrict());
        existedRajkotBillReport.setStateCode(rajkotBillReport.getStateCode());
        existedRajkotBillReport.setGstNo(rajkotBillReport.getGstNo());
        existedRajkotBillReport.setRouteName(rajkotBillReport.getRouteName());
        existedRajkotBillReport.setTelephoneNo(rajkotBillReport.getTelephoneNo());

        return rajkotFreightBillRepository.save(existedRajkotBillReport);

    }

    @Override
    public ApiResponse<?> deleteRajkotFreightBill(Long freightBillReportId) {
        Optional<RajkotBillReport> optionalRajkotBillReport = rajkotFreightBillRepository.findById(freightBillReportId);

        if(optionalRajkotBillReport.isPresent()){
            rajkotFreightBillRepository.delete(optionalRajkotBillReport.get());
            return new ApiResponse<>(true,"Report deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Report not deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<RajkotBillReport> getAllRajkotFreightBills() {
        return rajkotFreightBillRepository.findAll();
    }
}
