package com.dot.assessment.service;

import com.dot.assessment.data.entities.BlockedIpTable;
import com.dot.assessment.data.entities.UserAccessLog;

import java.util.List;

public interface BlockedIpTableService {
    List<BlockedIpTable> saveBlockedIps(List<BlockedIpTable> blockedIpTables);
}
