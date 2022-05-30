package com.dot.assessment;

import com.dot.assessment.data.entities.BlockedIpTable;
import com.dot.assessment.data.entities.UserAccessLog;
import com.dot.assessment.service.BlockedIpTableService;
import com.dot.assessment.service.FileService;
import com.dot.assessment.service.UserAccessService;
import com.dot.assessment.service.impl.FileServiceImpl;
import com.dot.assessment.service.impl.IPFilterServiceImpl;
import com.dot.assessment.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class AssessmentApplicationTests {

	@Mock
	private FileService fileService;
	@Mock
	private UserAccessService userAccessService;
	@InjectMocks
	private IPFilterServiceImpl underTest;
	@InjectMocks
	private FileServiceImpl fileServiceImpl;


	@Test
	void contextLoads() {
	}

	@Test
   public void testReadFile(){
		String fullPathName = null ;
		try {
			fullPathName = Paths.get(this.getClass().getClassLoader().getResource("user_access_test.txt").toURI()).toFile().getAbsolutePath();
		} catch (URISyntaxException e) {
			log.error(" Error Occurred [{}]", e.getMessage());
		}
		List<UserAccessLog> userAccessLogList = fileServiceImpl.loadRecords(fullPathName);
		int expectedRecordCount = 8;
		int returnedRecordCount = userAccessLogList.size();
		assertEquals(expectedRecordCount, returnedRecordCount);
   }


	@Test
	public void testBlockedIpFilter(){

		String fullPathName = "/user_access_test.txt";
		String start = "2022-01-01 00:00:11.201";
		String duration = "hourly";
		String limit = "2";

		List<UserAccessLog> userAccessLogs = new ArrayList<>();
		UserAccessLog firstRecord = new UserAccessLog();
		firstRecord.setIp("192.168.88.0");
		firstRecord.setDate(DateUtils.getDateTime("2022-01-01 00:00:11.201"));
		firstRecord.setStatus("200");
		firstRecord.setRequest("GET / HTTP/1.1");
		firstRecord.setUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; EIE10;ENUSWOL)");

		UserAccessLog secondRecord = new UserAccessLog();
		secondRecord.setIp("192.168.88.0");
		secondRecord.setDate(DateUtils.getDateTime("2022-01-01 00:20:11.201"));
		secondRecord.setStatus("200");
		secondRecord.setRequest("GET / HTTP/1.1");
		secondRecord.setUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; EIE10;ENUSWOL)");

		UserAccessLog thirdRecord = new UserAccessLog();
		thirdRecord.setIp("192.168.88.0");
		thirdRecord.setDate(DateUtils.getDateTime("2022-01-01 01:00:00.201"));
		thirdRecord.setStatus("200");
		thirdRecord.setRequest("GET / HTTP/1.1");
		thirdRecord.setUserAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0; EIE10;ENUSWOL)");

		userAccessLogs.add(firstRecord);
		userAccessLogs.add(secondRecord);
		userAccessLogs.add(thirdRecord);


		when(fileService.loadRecords(fullPathName)).thenReturn(userAccessLogs);
		when(userAccessService.saveUserAccessLogs(userAccessLogs)).thenReturn(userAccessLogs);
		when(userAccessService.getAllIpsThatPassedRequestLimit(start, duration, limit)).thenReturn(userAccessLogs);
		when(fileService.loadRecords(fullPathName)).thenReturn(userAccessLogs);

		List<BlockedIpTable> blockedIpTables = underTest.filterAndBlockIPs(fullPathName, start, duration, limit);

		assertEquals(1, blockedIpTables.size());

	}


}
