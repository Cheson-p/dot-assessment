package com.dot.assessment.service;

import com.dot.assessment.data.entities.UserAccessLog;

import java.util.List;

public interface UserAccessService {
     List<UserAccessLog> saveUserAccessLogs(List<UserAccessLog> userAccessLogs);
     List<UserAccessLog> getAllUserAccessLogs();

     List<UserAccessLog> getAllIpsThatPassedRequestLimit(String start, String duration, String limit);

}
