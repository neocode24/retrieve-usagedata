package com.garage.manage.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garage.manage.bean.UsageData;
import com.garage.manage.remote.bean.Data_DataSvcDrctlyUseQntList;
import com.garage.manage.remote.bean.Data_UsePtrn3monsRetv;
import com.garage.manage.repository.UsageDataRedisRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/cache")
public class DataController {

	@Autowired
	UsageDataRedisRepository redisRepository;
	
	@PostMapping("/create/{key}")
	public ResponseEntity<String> updateCache(@PathVariable("key") String key, @Valid @RequestBody Map requestMap) {
		
		log.debug("Cache Create. key:[" + key + "]");
		
		String subSet = key.substring(0, key.indexOf("-"));
		
		UsageData usageData = null;
		switch (subSet) {
		case "usePtrn3monsRetv":
			usageData = new UsageData(
					key, 
					new ObjectMapper().convertValue(requestMap, Data_UsePtrn3monsRetv.class), 
					null,
					LocalDateTime.now());
			break;
			
		case "dataSvcDrctlyUseQntList" :
			usageData = new UsageData(
					key, 
					null,
					new ObjectMapper().convertValue(requestMap, Data_DataSvcDrctlyUseQntList.class),
					LocalDateTime.now());
			break;

		default:
			break;
		}
		
		
		Optional<UsageData> savedData = Optional.ofNullable(redisRepository.save(usageData));
		if ( savedData.isPresent() ) {
			return new ResponseEntity<>("Create", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Fail", HttpStatus.EXPECTATION_FAILED);
		}
		
	}
	
}
