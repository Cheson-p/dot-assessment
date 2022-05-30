package com.dot.assessment.service;

import com.dot.assessment.data.entities.UserAccessLog;

import java.util.List;

public interface FileService {
    List<UserAccessLog> loadRecords(String filePath);
}
