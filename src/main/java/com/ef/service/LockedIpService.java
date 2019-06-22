package com.ef.service;

import com.ef.entity.LockedIp;
import com.ef.repository.LockedIpRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class LockedIpService {

    @Inject
    private LockedIpRepository lockedIpRepository;

    public void saveLockedIps(List<LockedIp> lockedIps) {
        lockedIpRepository.persistLockedIps(lockedIps);
    }
}
