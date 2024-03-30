package com.school.commentmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
public class RequestPerson {
    private String ipAddress;
    private Instant lastRequestTime;
}
