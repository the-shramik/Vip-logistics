package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryHireMemo;
import com.viplogistics.entity.transaction.helper.Memo;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.ILorryHireMemoRepository;
import com.viplogistics.repository.IMemoRepository;
import com.viplogistics.service.ILorryHireMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LorryHireMemoServiceImpl implements ILorryHireMemoService {

    private final ILorryHireMemoRepository lorryHireMemoRepository;

    private final IMemoRepository memoRepository;

    @Override
    public LorryHireMemo addLorryHireMemo(LorryHireMemo lorryHireMemo) throws ResourceNotFoundException {
        Memo memo =memoRepository.findByMemoNo(lorryHireMemo.getMemo().getMemoNo())
                .orElseThrow(()->new ResourceNotFoundException("Lorry Hire Memo Not Found!"));


        memo.getLorryReceipts().forEach(lorryReceipt -> {
            System.out.println(lorryReceipt.getLrNote());
        });
        lorryHireMemo.setMemo(memo);

        memo.setMemoStatus(true);
        memoRepository.save(memo);

        return lorryHireMemoRepository.save(lorryHireMemo);
    }

    @Override
    public List<LorryHireMemo> getAllLorryHireMemos() {
        return lorryHireMemoRepository.findAll();
    }

    @Override
    public LorryHireMemo updateLorryHireMemo(LorryHireMemo lorryHireMemo) throws ResourceNotFoundException {

        LorryHireMemo existingLorryHireMemo = lorryHireMemoRepository.findById(lorryHireMemo.getLorryHireMemoId())
                .orElseThrow(() -> new ResourceNotFoundException("Lorry hire memo not found!"));

        existingLorryHireMemo.setBranch(lorryHireMemo.getBranch());
        existingLorryHireMemo.setVehicle(lorryHireMemo.getVehicle());
        existingLorryHireMemo.setOwner(lorryHireMemo.getOwner());
        existingLorryHireMemo.setDriver(lorryHireMemo.getDriver());
        existingLorryHireMemo.setPermitNo(lorryHireMemo.getPermitNo());
        existingLorryHireMemo.setLicenceNo(lorryHireMemo.getLicenceNo());
        existingLorryHireMemo.setTotalHire(lorryHireMemo.getTotalHire());
        existingLorryHireMemo.setAdvance(lorryHireMemo.getAdvance());
        existingLorryHireMemo.setExtraCollection(lorryHireMemo.getExtraCollection());
        existingLorryHireMemo.setCommission(lorryHireMemo.getCommission());
        existingLorryHireMemo.setHamali(lorryHireMemo.getHamali());
        existingLorryHireMemo.setMisc(lorryHireMemo.getMisc());
        existingLorryHireMemo.setTotal(lorryHireMemo.getTotal());
        existingLorryHireMemo.setBalance(lorryHireMemo.getBalance());
        existingLorryHireMemo.setAdvancePaymentType(lorryHireMemo.getAdvancePaymentType());
        existingLorryHireMemo.setAdvanceCashBankAcNo(lorryHireMemo.getAdvanceCashBankAcNo());
        existingLorryHireMemo.setAdvanceCheDdNo(lorryHireMemo.getAdvanceCheDdNo());
        existingLorryHireMemo.setAdvanceCheDdDate(lorryHireMemo.getAdvanceCheDdDate());
        existingLorryHireMemo.setFinalPaymentType(lorryHireMemo.getFinalPaymentType());
        existingLorryHireMemo.setFinalCashBankAcNo(lorryHireMemo.getFinalCashBankAcNo());
        existingLorryHireMemo.setFinalCheNo(lorryHireMemo.getFinalCheNo());
        existingLorryHireMemo.setFinalCheDate(lorryHireMemo.getFinalCheDate());

        return lorryHireMemoRepository.save(existingLorryHireMemo);
    }

    @Override
    public ApiResponse<?> deleteLorryHireMemo(Long hireMemoId) {

        Optional<LorryHireMemo> optionalLorryHireMemo = lorryHireMemoRepository.findById(hireMemoId);

        if (optionalLorryHireMemo.isPresent()) {


            LorryHireMemo lorryHireMemo = optionalLorryHireMemo.get();

            if (lorryHireMemo.getMemo() != null) {
                Memo memo=lorryHireMemo.getMemo();
                memo.setLorryHireMemo(null);
                memo.setMemoStatus(false);
                memoRepository.save(lorryHireMemo.getMemo());
            }

            lorryHireMemoRepository.deleteById(lorryHireMemo.getLorryHireMemoId());
            return new ApiResponse<>(true, "Lorry hire memo deleted successfully!",null,HttpStatus.OK);
        } else {
            return new ApiResponse<>(false, "Lorry hire memo not found!",null,HttpStatus.NOT_FOUND);
        }
    }
}
