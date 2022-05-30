package com.dot.assessment.service.impl;

import com.dot.assessment.data.entities.BlockedIpTable;
import com.dot.assessment.data.repositories.BlockedIpTableRepository;
import com.dot.assessment.service.BlockedIpTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedIpTableServiceImpl implements BlockedIpTableService{
    @Autowired
    private BlockedIpTableRepository blockedIpTableRepository;
    @Override
    public List<BlockedIpTable> saveBlockedIps(List<BlockedIpTable> blockedIpTables) {
        return blockedIpTableRepository.saveAll(blockedIpTables);
    }
}
