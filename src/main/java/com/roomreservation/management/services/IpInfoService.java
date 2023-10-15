package com.roomreservation.management.services;

import com.roomreservation.management.model.IpInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpInfoService {

    private static final String IP_API_URL = "https://ip-api.com/json/";

    private final RestTemplate restTemplate;

    public IpInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IpInfo fetchIpInfo(String ipAddress) {
        String apiUrl = IP_API_URL + ipAddress;

        try {
            // Make an HTTP request to https://ip-api.com/ and retrieve the IP information
            IpInfo ipInfo = restTemplate.getForObject(apiUrl, IpInfo.class);

            if (ipInfo != null) {
                return ipInfo;
            } else {
                throw new RuntimeException("Unable to fetch IP information");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching IP information: " + e.getMessage());
        }
    }
}

