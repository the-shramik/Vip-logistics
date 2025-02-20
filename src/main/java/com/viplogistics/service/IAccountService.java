package com.viplogistics.service;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Account;
import com.viplogistics.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface IAccountService {

    Account addAccount(Account account);

    List<Account> getAllAccounts();

    Account updateAccount(Account account) throws ResourceNotFoundException;

    ApiResponse<?> deleteAccount(Long accountId);

    Account getAccountByAccountNumber(String accountNumber) throws ResourceNotFoundException;

    Map<String,Long> getAccountCount();
}
