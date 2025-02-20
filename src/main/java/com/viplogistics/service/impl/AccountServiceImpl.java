package com.viplogistics.service.impl;

import com.viplogistics.entity.ApiResponse;
import com.viplogistics.entity.master.Account;
import com.viplogistics.exception.ResourceNotFoundException;
import com.viplogistics.repository.IAccountRepository;
import com.viplogistics.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IAccountRepository accountRepository;

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account) throws ResourceNotFoundException {
        Account existedAccount=accountRepository.findById(account.getAccountId())
                .orElseThrow(()->new ResourceNotFoundException("Account not with id "+account.getAccountId()));

        existedAccount.setAccountNo(account.getAccountNo());
        existedAccount.setAccountName(account.getAccountName());
        existedAccount.setAccountType(account.getAccountType());
        existedAccount.setBalanceMark(account.getBalanceMark());
        existedAccount.setOpeningBalance(account.getOpeningBalance());
        existedAccount.setUnderGroup(account.getUnderGroup());

        return accountRepository.save(existedAccount);
    }

    @Override
    public ApiResponse<?> deleteAccount(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if(optionalAccount.isPresent()){
            Account account=optionalAccount.get();
            accountRepository.delete(account);
            return new ApiResponse<>(true,"Account Deleted",null, HttpStatus.OK);
        }else{
            return new ApiResponse<>(false,"Account Not Deleted",null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) throws ResourceNotFoundException {

        return accountRepository.getByAccountNo(accountNumber)
                .orElseThrow(()->new ResourceNotFoundException("Account not with account number "+accountNumber));
    }

    @Override
    public Map<String, Long> getAccountCount() {
        HashMap<String,Long> result=new HashMap<>();

        result.put("accountsCount",accountRepository.count());

        return result;
    }
}
