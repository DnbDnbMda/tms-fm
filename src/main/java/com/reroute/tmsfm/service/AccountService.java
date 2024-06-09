package com.reroute.tmsfm.service;

import com.reroute.tmsfm.entity.Account;

import java.util.UUID;

public interface AccountService {
    Account findAccountByID(UUID id);
}
