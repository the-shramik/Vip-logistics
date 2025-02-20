package com.viplogistics.repository;

import com.viplogistics.entity.master.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> getByAccountNo(String accountNo);
}
