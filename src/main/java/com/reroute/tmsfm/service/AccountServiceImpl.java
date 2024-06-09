package com.reroute.tmsfm.service;

import com.reroute.tmsfm.entity.Account;
import com.reroute.tmsfm.repository.AccountRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Data
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Account findAccountByID(UUID id) {
        return accountRepository.findById(id).orElse(null);
    }
}
