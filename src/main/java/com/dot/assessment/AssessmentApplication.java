package com.dot.assessment;

import com.dot.assessment.data.entities.BlockedIpTable;
import com.dot.assessment.data.entities.UserAccessLog;
import com.dot.assessment.service.BlockedIpTableService;
import com.dot.assessment.service.FileService;
import com.dot.assessment.service.IPFilterService;
import com.dot.assessment.service.UserAccessService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class AssessmentApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(AssessmentApplication.class, args);
		List<UserAccessLog> userAccessLogs = new ArrayList<>();
		UserAccessService userAccessService = context.getBean(UserAccessService.class);
		FileService fileService = context.getBean(FileService.class);
		IPFilterService ipFilterService = context.getBean(IPFilterService.class);

		String fullAccessFilePath = args[0];
		String startDateTime = args[1];
		String duration = args[2];
		String limit = args[3];

		if(StringUtils.isNotBlank(fullAccessFilePath) &&
		   StringUtils.isNotBlank(startDateTime) &&
		   StringUtils.isNotBlank(duration) &&
		   StringUtils.isNotBlank(limit) ){

			// read, load file records to UserAccessLog and save to database.
			userAccessLogs = fileService.loadRecords(fullAccessFilePath);
			userAccessService.saveUserAccessLogs(userAccessLogs);

			// perform filter operation based on the parameters
			ipFilterService.filterAndBlockIPs(fullAccessFilePath, startDateTime, duration , limit);
		}else{
			log.info(" Parameter cannot be null, please ensure all parameters [ accessFile, start, duration and limit ] are set ");
		}
	}

}
