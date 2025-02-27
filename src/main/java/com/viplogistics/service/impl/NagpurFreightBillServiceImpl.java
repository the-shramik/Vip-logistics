package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.ChakanBillReport;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.NagpurBillReport;
import com.viplogistics.entity.transaction.dto.CommonFreightBillDataDto;
import com.viplogistics.entity.transaction.dto.NagpurFreightBillDto;
import com.viplogistics.entity.transaction.dto.helper.NagpurFreightBillDtoHelper;
import com.viplogistics.entity.transaction.dto.helper.extracharges.NagpurExtraCharges;
import com.viplogistics.exception.BillAlreadySavedException;
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
            NagpurFreightBillDtoHelper nagpurFreightBillDtoHelper=new NagpurFreightBillDtoHelper();

            List<NagpurFreightBillDto> nagpurFreightBillDtos=new ArrayList<>();

            List<NagpurExtraCharges> nagpurExtraChargesList=new ArrayList<>();

            lorryReceiptRepository.findByBillNoAndRouteName(billNo,routeName)
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
                                    nagpurFreightBillDto.setVehicleType(lorryReceiptItem.getLcvFtl());
                                    nagpurFreightBillDto.setRate(lorryReceiptItem.getLcvFtl());
                                    nagpurFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                    nagpurFreightBillDto.setGrandTotal(lorryReceipt.getGrandTotal());

                                    nagpurFreightBillDto.setCgst(null);
                                    nagpurFreightBillDto.setSgst(null);
                                    nagpurFreightBillDto.setTotalBillValue(null);

                                    nagpurFreightBillDtos.add(nagpurFreightBillDto);

                                });

                        NagpurExtraCharges nagpurExtraCharges=new NagpurExtraCharges();
                        nagpurExtraCharges.setLrNo(lorryReceipt.getLrNo());
                        nagpurExtraCharges.setStCharges(lorryReceipt.getStCharges());
                        lorryReceipt.getExtraCharges().forEach(extraCharges -> {
                            if(extraCharges.getChargesHeads().equals("COLLECTION_CHARGES")){
                                nagpurExtraCharges.setCollectionCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("UNLOADING_CHARGES")){
                                nagpurExtraCharges.setUnloadingCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("PLYWOOD_CHARGES")){
                                nagpurExtraCharges.setPlyWoodCharges(extraCharges.getChargesAmount());
                            }else if(extraCharges.getChargesHeads().equals("LR_CHARGES")){
                                nagpurExtraCharges.setLrCharges(extraCharges.getChargesAmount());
                            }
                        });

                        if(nagpurExtraChargesList.isEmpty()){
                            nagpurExtraChargesList.add(nagpurExtraCharges);
                        }
                        else if(!nagpurExtraChargesList.get(nagpurExtraChargesList.size()-1).getLrNo().equals(lorryReceipt.getLrNo())){
                            nagpurExtraChargesList.add(nagpurExtraCharges);
                        }
                    });
            nagpurFreightBillDtoHelper.setNagpurExtraCharges(nagpurExtraChargesList);
            nagpurFreightBillDtoHelper.setNagpurFreightBillDtos(nagpurFreightBillDtos);

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
    public NagpurBillReport saveNagpurFreightBill(NagpurBillReport nagpurBillReport) throws BillAlreadySavedException {
        if(!nagpurFreightBillRepository.existsByBillNo(nagpurBillReport.getBillNo())) {
            return nagpurFreightBillRepository.save(nagpurBillReport);
        }else{
            throw new BillAlreadySavedException("Bill already saved");
        }
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
        existedNagpurBillReport.setRequestedBy(nagpurBillReport.getRequestedBy());
        existedNagpurBillReport.setBillDate(nagpurBillReport.getBillDate());

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

    @Override
    public List<NagpurBillReport> getAllNagpurFreightBills() {
        return nagpurFreightBillRepository.findAll();
    }

    @Override
    public NagpurBillReport getNagpurFreightByBillNo(String billNo) throws ResourceNotFoundException {
        return nagpurFreightBillRepository.findByBillNo(billNo)
                .orElseThrow(()->new ResourceNotFoundException("Freight not found"));
    }

    @Override
    public List<NagpurBillReport> getAllNagpurRequestedFreightBills() {
        return nagpurFreightBillRepository.findByIsVerifiedFalse();
    }


}
