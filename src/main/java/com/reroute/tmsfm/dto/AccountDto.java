package com.reroute.tmsfm.dto;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {
    private UUID id;
}
