package com.garage.usagedata.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.garage.usagedata.bean.Data_DataSvcDrctlyUseQntList;
import com.garage.usagedata.bean.PK_DataSvcDrctlyUseQntList;
import com.garage.usagedata.repository.DataSvcDrctlyUseQntListRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usage/dataSvcDrctlyUseQntList")
public class DataSvcDrctlyUseQntListController {
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DataSvcDrctlyUseQntListRepository repository;
	
	@Value("${target.service.name}")
    private String targetServiceName;
	
	@GetMapping("/{svcContId}/{retvDt}")
	public ResponseEntity<Data_DataSvcDrctlyUseQntList> getData(@PathVariable("svcContId") String svcContId, @PathVariable("retvDt") String retvDt) {
		
		log.debug("Get Data with ID = " + svcContId + ", " + retvDt + "...");
		Optional<Data_DataSvcDrctlyUseQntList> data = repository.findById(new PK_DataSvcDrctlyUseQntList(svcContId, retvDt));
		
		// db에 있는 경우
		if (data.isPresent()) {
			
			// redisKey는 서비스명 + 호출 PK
			String redisKey = "dataSvcDrctlyUseQntList" + "-" + svcContId + "-" + retvDt;
			
			String url = "http://" + targetServiceName + "/cache/create/" + redisKey;
			
			// 결과를 Cache Manager로 전달. cache 반영 처리 위임.
			restTemplate.postForEntity(url, data.get(), Data_DataSvcDrctlyUseQntList.class);
						
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} 
		// db에 없는 경우. 
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create")
	public Data_DataSvcDrctlyUseQntList createData(@Valid @RequestBody Data_DataSvcDrctlyUseQntList data) {
		log.debug("Create Data : " + data.toString() + "...");

		return repository.save(data);
	}
	
	@PutMapping("/{svcContId}/{retvDt}")
	public ResponseEntity<Data_DataSvcDrctlyUseQntList> updateData(@PathVariable("svcContId") String svcContId, @PathVariable("retvDt") String retvDt, @RequestBody Data_DataSvcDrctlyUseQntList data) {
		log.debug("Update Data with ID = " + svcContId + ", " + retvDt + "...");

		Optional<Data_DataSvcDrctlyUseQntList> findedData = repository.findById(new PK_DataSvcDrctlyUseQntList(svcContId, retvDt));
		if (findedData.isPresent()) {
			Data_DataSvcDrctlyUseQntList savedData = findedData.get();
			
			savedData.setProdEfctStDate(data.getProdEfctStDate());
			savedData.setProdEfctFnsDate(data.getProdEfctFnsDate());
			savedData.setProdId(data.getProdId());
			savedData.setProdNm(data.getProdNm());
			savedData.setNtnlOutnetVcTlkQnt(data.getNtnlOutnetVcTlkQnt());
			savedData.setBillMidCtgNm(data.getBillMidCtgNm());
			savedData.setBillMidCtgCd(data.getBillMidCtgCd());
			savedData.setTotUseQnt(data.getTotUseQnt());
			savedData.setNnRatUseQnt(data.getNnRatUseQnt());
			savedData.setRatUseQnt(data.getRatUseQnt());
			savedData.setRatAmt(data.getRatAmt());
			savedData.setSvcTarif(data.getSvcTarif());
			savedData.setBigiDiv(data.getBigiDiv());			
			
			Data_DataSvcDrctlyUseQntList updatedData = repository.save(savedData);
			return new ResponseEntity<>(updatedData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{svcContId}/{retvDt}")
	public ResponseEntity<String> deleteData(@PathVariable("svcContId") String svcContId, @PathVariable("retvDt") String retvDt) {
		log.debug("Delete Data with ID = " + svcContId + ", " + retvDt + "...");

		try {
			repository.deleteById(new PK_DataSvcDrctlyUseQntList(svcContId, retvDt));
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("Data has been deleted!", HttpStatus.OK);
	}
	
}
