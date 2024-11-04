package com.reroute.tmsfm.service;

import com.reroute.tmsfm.entity.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AccountService {
    Account findAccountByID(UUID id);
}
