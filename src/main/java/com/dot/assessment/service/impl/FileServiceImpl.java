package com.dot.assessment.service.impl;

import com.dot.assessment.data.entities.UserAccessLog;
import com.dot.assessment.service.FileService;
import com.dot.assessment.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Override
    public List<UserAccessLog> loadRecords(String filePath) {
        List<UserAccessLog> userAccessLogs = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                String [] fields = line.split("\\|");
                UserAccessLog userAccessLog = new UserAccessLog();
                userAccessLog.setDate(DateUtils.getDateTime(fields[0]));
                userAccessLog.setIp(fields[1]);
                userAccessLog.setRequest(fields[2]);
                userAccessLog.setStatus(fields[3]);
                userAccessLog.setUserAgent(fields[4]);;
                userAccessLogs.add(userAccessLog);
            }
        } catch (FileNotFoundException e) {
           log.error(" Error occurred ::: [{}] ", e.getMessage());
        } catch (IOException e) {
           log.error(" Generic Error occurred ::: [{}]", e.getMessage());
        }
        return userAccessLogs;
    }



}
