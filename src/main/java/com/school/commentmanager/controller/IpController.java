package com.school.commentmanager.controller;

import com.school.commentmanager.storage.IPStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IpController {

    private final IPStorage ipStorage;

    public IpController(IPStorage ipStorage) {
        this.ipStorage = ipStorage;
    }

    @GetMapping("/reset/admin")
    public void resetIps() {
        ipStorage.resetRequestCounts();
    }

    @GetMapping("/admin")
    public void getIps() {
        log.info("Admin successfully logged in!");
    }
}
