package com.garage.usagedata.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.garage.usagedata.bean.UsageData;
import com.garage.usagedata.remote.bean.Data_DataSvcDrctlyUseQntList;
import com.garage.usagedata.remote.bean.Data_UsePtrn3monsRetv;
import com.garage.usagedata.repository.UsageDataRedisRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usage")
public class UsageDataController {
	
	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
	UsageDataRedisRepository usageDataRedisRepository;
	
	@Value("${target.service.name}")
    private String targetServiceName;


	
	@GetMapping("/usePtrn3monsRetv/{svcContId}/{retvYm}")
	public ResponseEntity<Data_UsePtrn3monsRetv> getUsePtrn3monsRetv(@PathVariable("svcContId") String svcContId, @PathVariable("retvYm") String retvYm) {

		log.debug("Start [usePtrn3monsRetv] service...");
		
		// redisKey는 서비스명 + 호출 PK
		String redisKey = "usePtrn3monsRetv" + "-" + svcContId + "-" + retvYm;
		
		// redis cache 에는 wrapped class (UsageData)로 거래 함. key는 svcContId + retvYm
		Optional<UsageData> usageData = usageDataRedisRepository.findById(redisKey);
		
		// cache에 있는 경우. wrapped class 안에 있는 실제 객체(Data_UsePtrn3monsRetv)로 결과 제공함.
		if (usageData.isPresent()) {
			log.debug("Return usage data from Redis");
			return new ResponseEntity<>(usageData.get().getUsePtrn3monsRetv(), HttpStatus.OK);
		} 
		// cache에 없는 경우. 
		else {
			
			String url = "http://" + targetServiceName + "/usage/usePtrn3monsRetv/" + svcContId + "/" + retvYm;
			
			log.debug("url:" + url);
			
			// 원격서비스에는 실제 객체(Data_UsePtrn3monsRetv)로 수신함.
			ResponseEntity<Data_UsePtrn3monsRetv> response = restTemplate.exchange(
		            url,
		            HttpMethod.GET,
		            null,
		            new ParameterizedTypeReference<Data_UsePtrn3monsRetv>(){});
			
			Optional<Data_UsePtrn3monsRetv> dbUsageData = Optional.ofNullable(response.getBody());
			if ( dbUsageData.isPresent() ) {
				log.debug("Return usage data from DB");
				return new ResponseEntity<>(dbUsageData.get(), HttpStatus.OK);
			} else {
				log.debug("No data found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		}
	}
	
	
	@GetMapping("/dataSvcDrctlyUseQntList/{svcContId}/{retvDt}")
	public ResponseEntity<Data_DataSvcDrctlyUseQntList> getDataSvcDrctlyUseQntList(@PathVariable("svcContId") String svcContId, @PathVariable("retvDt") String retvDt) {

		log.debug("Start [dataSvcDrctlyUseQntList] service...");
		
		// redisKey는 서비스명 + 호출 PK
		String redisKey = "dataSvcDrctlyUseQntList" + "-" + svcContId + "-" + retvDt;
		
		// redis cache 에는 wrapped class (UsageData)로 거래 함. key는 svcContId + retvYm
		Optional<UsageData> usageData = usageDataRedisRepository.findById(redisKey);
		
		// cache에 있는 경우. wrapped class 안에 있는 실제 객체(Data_DataSvcDrctlyUseQntList)로 결과 제공함.
		if (usageData.isPresent()) {
			log.debug("Return usage data from Redis");
			return new ResponseEntity<>(usageData.get().getDataSvcDrctlyUseQntList(), HttpStatus.OK);
		} 
		// cache에 없는 경우. 
		else {
			
			String url = "http://" + targetServiceName + "/usage/dataSvcDrctlyUseQntList/" + svcContId + "/" + retvDt;
			
			log.debug("url:" + url);
			
			// 원격서비스에는 실제 객체(Data_DataSvcDrctlyUseQntList)로 수신함.
			ResponseEntity<Data_DataSvcDrctlyUseQntList> response = restTemplate.exchange(
		            url,
		            HttpMethod.GET,
		            null,
		            new ParameterizedTypeReference<Data_DataSvcDrctlyUseQntList>(){});
			
			Optional<Data_DataSvcDrctlyUseQntList> dbUsageData = Optional.ofNullable(response.getBody());
			if ( dbUsageData.isPresent() ) {
				log.debug("Return usage data from DB");
				return new ResponseEntity<>(dbUsageData.get(), HttpStatus.OK);
			} else {
				log.debug("No data found");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		}
	}
}
