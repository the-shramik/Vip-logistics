package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryReceipt;
import com.viplogistics.entity.transaction.dto.ItemListDto;
import com.viplogistics.entity.transaction.dto.MisReportDto;
import com.viplogistics.entity.transaction.dto.RegisterDto;
import com.viplogistics.entity.transaction.helper.Bill;
import com.viplogistics.entity.transaction.helper.LorryReceiptItem;
import com.viplogistics.entity.transaction.helper.Memo;
import com.viplogistics.entity.transaction.helper.MemoDto;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IBillRepository;
import com.viplogistics.repository.ILorryReceiptItemRepository;
import com.viplogistics.repository.ILorryReceiptRepository;
import com.viplogistics.repository.IMemoRepository;
import com.viplogistics.service.ILorryReceiptService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LorryReceiptServiceImpl implements ILorryReceiptService {

    private final ILorryReceiptRepository lorryReceiptRepository;

    private final IMemoRepository memoRepository;

    private final IBillRepository billRepository;

    private final ILorryReceiptItemRepository lorryReceiptItemRepository;

    @Override
    public LorryReceipt addLorryReceipt(LorryReceipt lorryReceipt) {

//        if (lorryReceipt.getLrNo() == null || lorryReceipt.getLrNo().isEmpty()) {
//            String lastLrNo = lorryReceiptRepository.findTopByOrderByLrNoDesc();
//            String newLrNo = generateNewLrNo(lastLrNo).get("newLrNo");
//            lorryReceipt.setLrNo(newLrNo);
//        }

        Optional<Memo> optionalMemo = memoRepository.findByMemoNo(lorryReceipt.getMemo().getMemoNo());

        if(optionalMemo.isPresent()){
            lorryReceipt.setMemo(optionalMemo.get());
        }else {
            lorryReceipt.getMemo().setMemoStatus(false);
            memoRepository.save(lorryReceipt.getMemo());
        }

        lorryReceipt.getLorryReceiptItems().forEach(lorryReceiptItem -> {
            lorryReceiptItem.setLorryReceipt(lorryReceipt);
        });

        lorryReceipt.getExtraCharges().forEach(extraCharge->{
            extraCharge.setLorryReceipt(lorryReceipt);
        });

        lorryReceipt.setBill(null);
        return lorryReceiptRepository.save(lorryReceipt);
    }

    private Map<String, String> generateNewLrNo(String lastLrNo) {
        Map<String, String> result = new HashMap<>();

        int nextLrNo = 1;

        if (lastLrNo != null && !lastLrNo.isEmpty()) {
            try {
                int lastLr = Integer.parseInt(lastLrNo);
                nextLrNo = (lastLr >= 1000000) ? 1 : lastLr + 1;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid LR number format: " + lastLrNo);
            }
        }

        result.put("newLrNo", String.valueOf(nextLrNo));
        return result;
    }



    @Override
    public List<LorryReceipt> getAllLorryReceipts() {
        return lorryReceiptRepository.findAll();
    }

    @Override
    public LorryReceipt updateLorryReceipt(LorryReceipt lorryReceipt) throws ResourceNotFoundException {
        LorryReceipt existedLorryReceipt=lorryReceiptRepository.findById(lorryReceipt.getLrId())
                .orElseThrow(()->new ResourceNotFoundException("Lorry Receipt not found with lorry number "+lorryReceipt.getLrNo()));

        existedLorryReceipt.setLrNo(lorryReceipt.getLrNo());
        existedLorryReceipt.setBranch(lorryReceipt.getBranch());
        existedLorryReceipt.setRoute(lorryReceipt.getRoute());
        existedLorryReceipt.setConsignor(lorryReceipt.getConsignor());
        existedLorryReceipt.setConsignee(lorryReceipt.getConsignee());
        existedLorryReceipt.setRemark(lorryReceipt.getRemark());
        existedLorryReceipt.setWhoItemList(lorryReceipt.getWhoItemList());

        if(lorryReceipt.getMemo()!=null){
            Memo existedMemo=memoRepository.findByMemoNo(lorryReceipt.getMemo().getMemoNo())
                    .orElseThrow(()->new ResourceNotFoundException(("Memo not found with memo no"+lorryReceipt.getMemo().getMemoNo())));

            existedMemo.setMemoNo(lorryReceipt.getMemo().getMemoNo());
            existedMemo.setMemoDate(lorryReceipt.getMemo().getMemoDate());
            existedMemo.setMemoStatus(false);

            memoRepository.save(existedMemo);
            existedLorryReceipt.setMemo(existedMemo);
        }

        existedLorryReceipt.setOctBill(lorryReceipt.getOctBill());


        existedLorryReceipt.setLrDate(lorryReceipt.getLrDate());
        existedLorryReceipt.setWhoPay(lorryReceipt.getWhoPay());
        existedLorryReceipt.setOctroiPay(lorryReceipt.getOctroiPay());
        existedLorryReceipt.setRefTruckNo(lorryReceipt.getRefTruckNo());
        existedLorryReceipt.setDeliveryAt(lorryReceipt.getDeliveryAt());
        existedLorryReceipt.setLrNote(lorryReceipt.getLrNote());
        existedLorryReceipt.setStCharges(lorryReceipt.getStCharges());
        existedLorryReceipt.setLrStatus(lorryReceipt.getLrStatus());
        existedLorryReceipt.setGrandTotal(lorryReceipt.getGrandTotal());
        existedLorryReceipt.getLorryReceiptItems().clear();
        lorryReceipt.getLorryReceiptItems().forEach(item->{
            item.setLorryReceipt(existedLorryReceipt);
            existedLorryReceipt.getLorryReceiptItems().add(item);
        });

        existedLorryReceipt.getExtraCharges().clear();

        lorryReceipt.getExtraCharges().forEach(extraCharge -> {
            extraCharge.setLorryReceipt(existedLorryReceipt);
            existedLorryReceipt.getExtraCharges().add(extraCharge);
        });

        return lorryReceiptRepository.save(existedLorryReceipt);

    }

    @Override
    public ApiResponse<?> deleteLorryReceipt(Long lrId) throws ResourceNotFoundException {
        Optional<LorryReceipt> optionalLorry = lorryReceiptRepository.findById(lrId);

        if(optionalLorry.isPresent()){
            LorryReceipt lorryReceipt=optionalLorry.get();
            lorryReceipt.getLorryReceiptItems().clear();
            lorryReceipt.getExtraCharges().clear();

            lorryReceiptRepository.delete(lorryReceipt);
            return new ApiResponse<>(true,"Lorry Receipt Deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Lorry Receipt Not Deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Map<String, String> getLatestLrNo() {
        String lastLrNo = lorryReceiptRepository.findTopByOrderByLrNoDesc();

        return generateNewLrNo(lastLrNo);
    }

    @Transactional
    @Override
    public LorryReceipt updateLorryStatus(Long lrId, Boolean status) throws ResourceNotFoundException {

        LorryReceipt existingLorryReceipt=lorryReceiptRepository.findById(lrId)
                .orElseThrow(()->new ResourceNotFoundException("Lorry Receipt not found with LR no: "+lrId));

        existingLorryReceipt.setLrStatus(status);

        return lorryReceiptRepository.save(existingLorryReceipt);
    }

    @Override
    public MemoDto checkMemoExists(String memoNo) throws ResourceNotFoundException {
        Memo memo=memoRepository.findByMemoNo(memoNo)
                .orElseThrow(()->new ResourceNotFoundException("Memo not exists!"));

        LorryReceipt lorryReceipt=memo.getLorryReceipts().stream().findFirst().get();

        MemoDto memoDto=new MemoDto();
        memoDto.setMemoExists(memoDto.isMemoExists());
        memoDto.setRemark(lorryReceipt.getRemark());
        memoDto.setWhoPay(lorryReceipt.getWhoPay());
        memoDto.setOctroiPay(lorryReceipt.getOctroiPay());
        memoDto.setWhoItemList(lorryReceipt.getWhoItemList());
        memoDto.setRefTruckNo(lorryReceipt.getRefTruckNo());
        return memoDto;
    }

    @Override
    public Map<String, String> getLastMemoNo() {
        String lastMemoNo = memoRepository.findLastMemoNo();

        Map<String, String> result = new HashMap<>();

        result.put("lastMemoNo",lastMemoNo);

        return result;
    }

    @Override
    public List<MisReportDto> getLrSummary(String startDate, String endDate) {

        List<MisReportDto> misReports=new ArrayList<>();

         lorryReceiptRepository.findByLrDateBetween(startDate,endDate)
                .forEach(lorryReceipt -> {
                    lorryReceipt.getLorryReceiptItems()
                            .forEach(lorryReceiptItem -> {
                                MisReportDto misReport=new MisReportDto();

                                misReport.setLrId(lorryReceipt.getLrId());
                                misReport.setAsnNo(lorryReceiptItem.getAsnNo());
                                if(lorryReceipt.getBill()!=null){
                                    misReport.setBillNo(lorryReceipt.getBill().getBillNo());
                                    misReport.setBillDate(lorryReceipt.getBill().getBillDate());
                                    misReport.setUnloadDate(lorryReceipt.getBill().getUnloadDate());
                                }
                                misReport.setLcvFtl(lorryReceiptItem.getLcvFtl());
                                misReport.setChalanNo(lorryReceiptItem.getChalanNo());

                                misReport.setLrNo(lorryReceipt.getLrNo());
                                misReport.setMemoNo(lorryReceipt.getMemo().getMemoNo());
                                misReport.setChalanDate(lorryReceiptItem.getChalanDate());
                                misReport.setPackType(lorryReceiptItem.getPackType());
                                misReport.setQuantity(lorryReceiptItem.getQuantity());
                                if(lorryReceiptItem.getItem()!=null){
                                    misReport.setPu(lorryReceiptItem.getItem().getPu());
                                    misReport.setPartNo(lorryReceiptItem.getItem().getPartNo());
                                    misReport.setPartName(lorryReceiptItem.getItem().getItemName());
                                    misReport.setVendorName(lorryReceiptItem.getItem().getParty().getPartyName());
                                    misReport.setVendorCode(lorryReceiptItem.getItem().getVendorCode());
                                }
                                misReport.setLrDate(lorryReceipt.getLrDate());
                                misReport.setTotalFreight(lorryReceiptItem.getTotalFreight());
                                misReport.setTotalWight(lorryReceiptItem.getTotalWeight());
                                misReport.setValueOnChalan(lorryReceiptItem.getValueOnChalan());
                                misReport.setVehicleNo(lorryReceipt.getRefTruckNo());
                                misReport.setCgst(lorryReceiptItem.getCgst());
                                misReport.setSgst(lorryReceiptItem.getSgst());
                                misReport.setIgst(lorryReceiptItem.getIgst());
                                misReport.setGrandTotal(lorryReceipt.getGrandTotal());

                                misReports.add(misReport);
                            });
                });

         return misReports;
    }

    @Override
    public ApiResponse<?> updateBillDetails(Bill bill, String lrNo, String lrDate) {

        try {

            Bill savedBill = billRepository.save(bill);
            lorryReceiptRepository.findByLrNoAndLrDate(lrNo, lrDate)
                    .forEach(lorryReceipt -> {

                        LorryReceipt existedLr = lorryReceiptRepository.findById(lorryReceipt.getLrId()).get();
                        existedLr.setBill(savedBill);
                        lorryReceiptRepository.save(existedLr);
                    });

            return new ApiResponse<>(true,"Bill updated",null,HttpStatus.OK);
        }catch (Exception e){
            return new ApiResponse<>(false,"Bill not updated",null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LorryReceipt getLrByMemoNoLrNo(String lrNo, String memoNo) throws ResourceNotFoundException {

        return lorryReceiptRepository.findByMemo_MemoNoAndLrNo(memoNo, lrNo)
                .stream()
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("Lorry Receipt not found with these details!"));
    }

    @Override
    public List<LorryReceipt> getLrsByLrNoLrDate(String lrNo, String lrStartDate, String lrEndDate) {

        return lorryReceiptRepository.findByLrNoAndLrDateBetween(lrNo,lrStartDate,lrEndDate);
    }

    @Override
    public List<LorryReceipt> getLrsByMemoNo(String memoNo) {
        return lorryReceiptRepository.findByMemoNo(memoNo);
    }

    @Override
    public ApiResponse<?> updateMemoDateLrDate(String lrNo, String memoNo, String lrDate, String memoDate) throws ResourceNotFoundException {
        Memo memo = memoRepository.findByMemoNo(memoNo)
                        .orElseThrow(()->new ResourceNotFoundException("Memo not found with this memo no"));

        memo.setMemoDate(memoDate);
        memoRepository.save(memo);

        try {
            lorryReceiptRepository.findByLrNoAndMemoNo(lrNo, memoNo)
                    .forEach(lorryReceipt -> {
                        lorryReceipt.setLrDate(lrDate);

                        lorryReceiptRepository.save(lorryReceipt);
                    });

            return new ApiResponse<>(true,"LR's Date Updated",null,HttpStatus.OK);
        }catch (Exception e){
            return new ApiResponse<>(false,"LR's Date Not Updated",null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<LorryReceipt> getLrsByLrNo(String lrNo) {
       return lorryReceiptRepository.getAllByLrNo(lrNo);
    }

    //topay=value on chalan

    @Override
    public List<RegisterDto> getLrsByBranchStartDateAndEndDate(String branchNo, String startDate, String endDate) {
        List<RegisterDto> registerDtos=new ArrayList<>();

        lorryReceiptRepository.findByLrDateBetweenAndBranchNo(branchNo,startDate,endDate)
                .forEach(lorryReceipt -> {
                      lorryReceipt.getLorryReceiptItems().forEach(lorryReceiptItem -> {
                      RegisterDto registerDto=new RegisterDto();
                      registerDto.setId(lorryReceipt.getLrId());
                      registerDto.setBilled("-");
                      registerDto.setPaid(0.0);
                      registerDto.setConsignee(lorryReceipt.getConsignee().getPartyName());
                      registerDto.setConsignor(lorryReceipt.getConsignor().getPartyName());

                      if(lorryReceipt.getBill()!=null){
                          registerDto.setBillNo(lorryReceipt.getBill().getBillNo());
                      }
                      registerDto.setQuantity(lorryReceiptItem.getQuantity());

                      registerDto.setWeight(lorryReceiptItem.getTotalWeight());
                      registerDto.setLcvFtl(lorryReceiptItem.getLcvFtl());
                      registerDto.setChalanNo(lorryReceiptItem.getChalanNo());
                      registerDto.setToPay(lorryReceiptItem.getValueOnChalan());
                      registerDto.setRefTruckNo(lorryReceipt.getRefTruckNo());
                      registerDto.setLrNo(lorryReceipt.getLrNo());

                      if(lorryReceipt.getMemo()!=null) {
                          registerDto.setMemoDate(lorryReceipt.getMemo().getMemoDate());
                          registerDto.setMemoNo(lorryReceipt.getMemo().getMemoNo());
                      }
                      registerDtos.add(registerDto);
                    });
                });

        return registerDtos;
    }

    @Override
    public ItemListDto getLorryReceiptItem(String chalanNo) {


       LorryReceiptItem lorryReceiptItem= lorryReceiptItemRepository.findByChalanNo(chalanNo);

        if (lorryReceiptItem == null) {
            return null; // or throw an exception
        }

       ItemListDto itemListDto=new ItemListDto(
               lorryReceiptItem.getLorryReceiptItemId(),
               lorryReceiptItem.getChalanNo(),
               lorryReceiptItem.getChalanDate(),
               lorryReceiptItem.getItem(),
               lorryReceiptItem.getQuantity(),
               lorryReceiptItem.getLcvFtl(),
               lorryReceiptItem.getCalcOn(),
               lorryReceiptItem.getAsnNo(),
               lorryReceiptItem.getPackType(),
               lorryReceiptItem.getValueOnChalan(),
               lorryReceiptItem.getValueRs(),
               lorryReceiptItem.getAmount(),
               lorryReceiptItem.getTotalFreight(),
               lorryReceiptItem.getCgst(),
               lorryReceiptItem.getSgst(),
               lorryReceiptItem.getIgst()
       );

        return itemListDto;

    }

    @Override
    public Map<String, Long> getLrCount() {
        HashMap<String,Long> result=new HashMap<>();
        result.put("totalLrs",lorryReceiptRepository.count());
        return result;
    }
}
