package com.dot.assessment.service.impl;

import com.dot.assessment.data.entities.BlockedIpTable;
import com.dot.assessment.data.entities.UserAccessLog;
import com.dot.assessment.service.BlockedIpTableService;
import com.dot.assessment.service.FileService;
import com.dot.assessment.service.IPFilterService;
import com.dot.assessment.service.UserAccessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IPFilterServiceImpl implements IPFilterService {
    @Autowired
    private UserAccessService userAccessService;
    @Autowired
    private FileService fileService;
    @Autowired
    private BlockedIpTableService blockedIpTableService;

    @Override
    public List<BlockedIpTable> filterAndBlockIPs(String fullAccessFilePath, String startDateTime, String duration, String limit) {

        List<UserAccessLog> userAccessLogs = new ArrayList();
        List<BlockedIpTable> blockedIpTables = new ArrayList<>();

        if(StringUtils.isNotBlank(fullAccessFilePath) &&
                StringUtils.isNotBlank(startDateTime) &&
                StringUtils.isNotBlank(duration) &&
                StringUtils.isNotBlank(limit) ){
            // read, load file records to UserAccessLog and save to database.
            userAccessLogs = fileService.loadRecords(fullAccessFilePath);
            userAccessService.saveUserAccessLogs(userAccessLogs);

            // get all IPs that has passed it's request limit and group by IP and its request count
            List<UserAccessLog> userAccessLogList = userAccessService.getAllIpsThatPassedRequestLimit(startDateTime, duration, limit);
            Map<String, Long> userAccessLogMap = userAccessLogList.stream().collect(Collectors.groupingByConcurrent(UserAccessLog::getIp, Collectors.counting()));
            userAccessLogMap.forEach((ip, occurrence) -> {
                log.error(" Meessage ::: This IP [{}] sent more request(s) [{}] then its limit, hence it will blocked", ip, occurrence);
                BlockedIpTable blockedIpTable = new BlockedIpTable();
                blockedIpTable.setComment("This IP sent more request(s) then its limit, hence it will be blocked");
                blockedIpTable.setIp(ip);
                blockedIpTable.setRequestNumber(Long.toString(occurrence));
                blockedIpTables.add(blockedIpTable);
           });

            // save the mapped records to BlockedIpTable table
            List<BlockedIpTable> savedBlockedIpTables  = blockedIpTableService.saveBlockedIps(blockedIpTables);
            log.info(" Total record saved [{}]", savedBlockedIpTables.size());
        }else{
            log.info(" Parameter cannot be null, please ensure all parameters [ accessFile, start, duration and limit ] are set ");
        }
        return blockedIpTables;
    }
}
