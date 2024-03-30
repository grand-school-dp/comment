package com.school.commentmanager.storage;

import com.school.commentmanager.model.RequestPerson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class IPStorage {

//    private static final Map<String, RequestPerson> ipMap = new ConcurrentHashMap<>();
    private static final List<RequestPerson> ipList = Collections.synchronizedList(new ArrayList<>());

    public void incrementRequestCount(String ipAddress) {
        ipList.add(new RequestPerson(ipAddress, Instant.now()));
        log.info("ipList: {}", ipList);
//        ipMap.putIfAbsent(ipAddress, new RequestPerson(0, Instant.now()));
//        ipMap.computeIfPresent(ipAddress, (key, value) -> value.incrementCount());
    }

    public int getRequestCountByTime(String ipAddress) {
        return (int) ipList.stream()
                .filter(requestPerson -> requestPerson.getIpAddress().equals(ipAddress))
                .filter(requestPerson -> requestPerson.getLastRequestTime().isAfter(Instant.now().minusSeconds(2)))
                .count();
    }

    public int getRequestCount(String ipAddress) {
        return (int) ipList.stream()
                .filter(requestPerson -> requestPerson.getIpAddress().equals(ipAddress))
                .count();
    }

    public void resetRequestCounts() {
        ipList.clear();
    }
}