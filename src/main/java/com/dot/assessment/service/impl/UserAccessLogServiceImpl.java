package com.dot.assessment.service.impl;

import com.dot.assessment.data.entities.UserAccessLog;
import com.dot.assessment.data.repositories.UserAccessRepository;
import com.dot.assessment.service.UserAccessService;
import com.dot.assessment.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserAccessLogServiceImpl implements UserAccessService {
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Override
    public List<UserAccessLog> saveUserAccessLogs(List<UserAccessLog> userAccessLogs) {
        return userAccessRepository.saveAll(userAccessLogs);
    }

    @Override
    public List<UserAccessLog> getAllUserAccessLogs() {
        return userAccessRepository.findAll();
    }

    @Override
    public List<UserAccessLog> getAllIpsThatPassedRequestLimit(String start, String duration , String limit) {
        LocalDateTime end = null;
        if(duration.equalsIgnoreCase("daily") ){
             end = DateUtils.getDateTime(start).plusDays(1);
        }else if(duration.equalsIgnoreCase("hourly") ){
             end = DateUtils.getDateTime(start).plusHours(1);
        }
        return userAccessRepository.findAllThatHavePassedLimit(start, end.toString(), Integer.parseInt(limit));
    }
}
