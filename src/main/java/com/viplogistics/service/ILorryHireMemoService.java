package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.transaction.LorryHireMemo;
import com.viplogistics.exception.MemoLockedException;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;

public interface ILorryHireMemoService {

    LorryHireMemo addLorryHireMemo(LorryHireMemo lorryHireMemo) throws ResourceNotFoundException;

    List<LorryHireMemo> getAllLorryHireMemos();

    LorryHireMemo updateLorryHireMemo(LorryHireMemo lorryHireMemo) throws ResourceNotFoundException;

    ApiResponse<?> deleteLorryHireMemo(Long hireMemoId);
}
