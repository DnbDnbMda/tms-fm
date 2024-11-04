package com.reroute.tmsfm.dto;

import com.reroute.tmsfm.enums.EventType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class EventDto {

    private EventType eventType;
    private UUID uuid;
    private LocalDateTime localDateTime;
}
