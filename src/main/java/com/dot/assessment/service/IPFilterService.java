package com.dot.assessment.service;

import com.dot.assessment.data.entities.BlockedIpTable;

import java.util.List;

public interface IPFilterService {

    List<BlockedIpTable> filterAndBlockIPs(String fullAccessFilePath, String start, String duration, String limit);

}
