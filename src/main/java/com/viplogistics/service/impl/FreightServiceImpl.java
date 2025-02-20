package com.viplogistics.service.impl;

import com.viplogistics.entity.transaction.FreightBillReport;
import com.viplogistics.entity.transaction.dto.*;
import com.viplogistics.entity.transaction.helper.ExtraCharges;
import com.viplogistics.repository.IFreightBillReportRepository;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.service.IFreightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreightServiceImpl implements IFreightService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final IFreightBillReportRepository freightBillReportRepository;

    @Override
    public List<MumbaiFreightBillDto> getMumbaiFreightBill(String billNo) {

        List<MumbaiFreightBillDto> mumbaiFreightBillDtos=new ArrayList<>();

        lorryReceiptRepository.findByBillNo(billNo)
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
                        mumbaiFreightBillDto.setRate(null);
                        mumbaiFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                        mumbaiFreightBillDto.setLoadingCharges(null);
                        mumbaiFreightBillDto.setUnloadingCharges(null);
                        mumbaiFreightBillDto.setPlyWoodCharges(null);
                        mumbaiFreightBillDto.setDetentionCharges(null);

                        mumbaiFreightBillDto.setStCharges(lorryReceipt.getStCharges());
                        double totalExtraCharges = lorryReceipt.getExtraCharges().stream()
                                .mapToDouble(ExtraCharges::getChargesAmount)
                                .sum();

                        double subTotal = lorryReceiptItem.getTotalFreight() + totalExtraCharges;
                        mumbaiFreightBillDto.setSubTotal(subTotal);


                        mumbaiFreightBillDto.setCgst(lorryReceiptItem.getCgst());
                        mumbaiFreightBillDto.setSgst(lorryReceiptItem.getSgst());
                        mumbaiFreightBillDto.setTotalBillValue(null);

                        mumbaiFreightBillDtos.add(mumbaiFreightBillDto);
                    });
                });

        return mumbaiFreightBillDtos;
    }

    @Override
    public List<RajkotFreightBillDto> getRajkotFreightBill(String billNo) {

        List<RajkotFreightBillDto> rajkotFreightBillDtos=new ArrayList<>();
        lorryReceiptRepository.findByBillNo(billNo)
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
                                rajkotFreightBillDto.setRate(null);
                                rajkotFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                rajkotFreightBillDto.setLoadingCharges(null);
                                rajkotFreightBillDto.setUnloadingCharges(null);
                                rajkotFreightBillDto.setPlyWoodCharges(null);
                                rajkotFreightBillDto.setDetentionCharges(null);
                                rajkotFreightBillDto.setStCharges(lorryReceipt.getStCharges());

                                double totalExtraCharges = lorryReceipt.getExtraCharges().stream()
                                        .mapToDouble(ExtraCharges::getChargesAmount)
                                        .sum();

                                double subTotal = lorryReceiptItem.getTotalFreight() + totalExtraCharges;
                                rajkotFreightBillDto.setSubTotal(subTotal);

                                rajkotFreightBillDto.setIgst(lorryReceiptItem.getIgst());

                                rajkotFreightBillDto.setTotalBillValue(null);

                                rajkotFreightBillDtos.add(rajkotFreightBillDto);
                            });
                });

        return rajkotFreightBillDtos;
    }

    @Override
    public List<NagpurPickupFreightBillDto> getNagpurPickupFreightBill(String billNo) {

        List<NagpurPickupFreightBillDto> nagpurPickupFreightBillDtos=new ArrayList<>();

        lorryReceiptRepository.findByBillNo(billNo)
                .forEach(lorryReceipt -> {
                    lorryReceipt.getLorryReceiptItems()
                            .forEach(lorryReceiptItem -> {
                                NagpurPickupFreightBillDto nagpurPickupFreightBillDto=new NagpurPickupFreightBillDto();

                                nagpurPickupFreightBillDto.setLrId(lorryReceipt.getLrId());
                                nagpurPickupFreightBillDto.setLrNo(lorryReceipt.getLrNo());
                                nagpurPickupFreightBillDto.setLrDate(lorryReceipt.getLrDate());
                                nagpurPickupFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                nagpurPickupFreightBillDto.setVendorName(lorryReceiptItem.getItem().getParty().getPartyName());
                                nagpurPickupFreightBillDto.setWeight(lorryReceiptItem.getTotalWeight());
                                nagpurPickupFreightBillDto.setVehicleNo(lorryReceipt.getRefTruckNo());
                                nagpurPickupFreightBillDto.setVehicleType(null);
                                nagpurPickupFreightBillDto.setRate(null);
                                nagpurPickupFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                nagpurPickupFreightBillDto.setLoadingCharges(null);
                                nagpurPickupFreightBillDto.setPlyWoodCharges(null);
                                nagpurPickupFreightBillDto.setCollectionCharges(null);
                                nagpurPickupFreightBillDto.setStCharges(lorryReceipt.getStCharges());
                                nagpurPickupFreightBillDto.setCgst(null);
                                nagpurPickupFreightBillDto.setSgst(null);
                                nagpurPickupFreightBillDto.setTotalBillValue(null);
                                nagpurPickupFreightBillDtos.add(nagpurPickupFreightBillDto);

                            });
                });

        return nagpurPickupFreightBillDtos;
    }

    @Override
    public List<ChakanFreightBillDto> getChakanFreightBill(String billNo) {

        List<ChakanFreightBillDto> chakanFreightBillDtos=new ArrayList<>();

        lorryReceiptRepository.findByBillNo(billNo)
                .forEach(lorryReceipt -> {
                    lorryReceipt.getLorryReceiptItems()
                            .forEach(lorryReceiptItem -> {
                                ChakanFreightBillDto chakanFreightBillDto=new ChakanFreightBillDto();

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
                                chakanFreightBillDto.setVehicleType(null);
                                chakanFreightBillDto.setTripNo(null);
                                chakanFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                chakanFreightBillDto.setUnloadingCharges(null);
                                chakanFreightBillDto.setLrCharges(null);
                                chakanFreightBillDto.setCgst(lorryReceiptItem.getCgst());
                                chakanFreightBillDto.setSgst(lorryReceiptItem.getSgst());
                                chakanFreightBillDto.setTotalBillValue(null);
                                chakanFreightBillDtos.add(chakanFreightBillDto);

                            });
                });

        return chakanFreightBillDtos;
    }

    @Override
    public List<RudrapurFreightBillDto> getRudrapurFreightBill(String billNo) {
        List<RudrapurFreightBillDto> rudrapurFreightBillDtos=new ArrayList<>();

        lorryReceiptRepository.findByBillNo(billNo)
                .forEach(lorryReceipt -> {
                    lorryReceipt.getLorryReceiptItems()
                            .forEach(lorryReceiptItem -> {
                                RudrapurFreightBillDto rudrapurFreightBillDto=new RudrapurFreightBillDto();

                                rudrapurFreightBillDto.setLrId(lorryReceipt.getLrId());
                                rudrapurFreightBillDto.setSac(null);
                                rudrapurFreightBillDto.setLrDate(lorryReceipt.getLrDate());
                                rudrapurFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                rudrapurFreightBillDto.setFrom(lorryReceipt.getRoute().getRouteFrom());
                                rudrapurFreightBillDto.setTo(lorryReceipt.getRoute().getRouteTo());
                                rudrapurFreightBillDto.setInvoiceNo(lorryReceiptItem.getChalanNo());
                                rudrapurFreightBillDto.setSupplierName(null);
                                rudrapurFreightBillDto.setWeight(lorryReceiptItem.getTotalWeight());
                                rudrapurFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                rudrapurFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                rudrapurFreightBillDto.setLoadingCharges(null);
                                rudrapurFreightBillDto.setDetentionCharges(null);
                                rudrapurFreightBillDto.setStCharges(lorryReceipt.getStCharges());
                                rudrapurFreightBillDto.setTaxableAmt(null);
                                rudrapurFreightBillDto.setIgst(lorryReceiptItem.getIgst());
                                rudrapurFreightBillDto.setRoundOff(null);
                                rudrapurFreightBillDto.setTotalBillValue(null);
                                rudrapurFreightBillDtos.add(rudrapurFreightBillDto);

                            });
                });

        return rudrapurFreightBillDtos;
    }

    @Override
    public List<NagpurFreightBillDto> getNagpurFreightBill(String billNo) {
        List<NagpurFreightBillDto> nagpurFreightBillDtos=new ArrayList<>();

        lorryReceiptRepository.findByBillNo(billNo)
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
                                nagpurFreightBillDto.setRate(null);
                                nagpurFreightBillDto.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                nagpurFreightBillDto.setUnloadingDate(lorryReceipt.getBill().getUnloadDate());
                                nagpurFreightBillDto.setPlyWoodCharges(null);
                                nagpurFreightBillDto.setStCharges(lorryReceipt.getStCharges());
                                nagpurFreightBillDto.setCollectionCharges(null);
                                nagpurFreightBillDto.setBillAmt(lorryReceiptItem.getAmount());
                                nagpurFreightBillDto.setCgst(null);
                                nagpurFreightBillDto.setSgst(null);
                                nagpurFreightBillDto.setTotalBillValue(null);
                                nagpurFreightBillDtos.add(nagpurFreightBillDto);

                            });
                });

        return nagpurFreightBillDtos;
    }

    @Override
    public FreightBillReport saveFreightBill(FreightBillReport freightBillReport) {
        return freightBillReportRepository.save(freightBillReport);
    }
}
