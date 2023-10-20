package com.roomreservation.management.services;

import com.roomreservation.management.model.IpInfo;

public interface IpInfoService {

    IpInfo getIpInfo(String ipAddress);
}
