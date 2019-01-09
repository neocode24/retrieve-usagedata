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

import com.garage.usagedata.bean.Data_UsePtrn3monsRetv;
import com.garage.usagedata.bean.PK_UsePtrn3monsRetv;
import com.garage.usagedata.repository.UsePtrn3monsRetvRepository;

@RestController
@RequestMapping("/usage/usePtrn3monsRetv")
public class UsePtrn3monsRetvController {
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UsePtrn3monsRetvRepository repository;
	
	@Value("${target.service.name}")
    private String targetServiceName;
	
	@GetMapping("/{svcContId}/{retvYm}")
	public ResponseEntity<Data_UsePtrn3monsRetv> getData(@PathVariable("svcContId") String svcContId, @PathVariable("retvYm") String retvYm) {
		
		System.out.println("Get Data with ID = " + svcContId + ", " + retvYm + "...");
		Optional<Data_UsePtrn3monsRetv> data = repository.findById(new PK_UsePtrn3monsRetv(svcContId, retvYm));
		
		// db에 있는 경우
		if (data.isPresent()) {
			
			// redisKey는 서비스명 + 호출 PK
			String redisKey = "usePtrn3monsRetv" + "-" + svcContId + "-" + retvYm;
			
			String url = "http://" + targetServiceName + "/cache/create/" + redisKey;
			
			System.out.println("url:" + url);
			
			// 결과를 Cache Manager로 전달. cache 반영 처리 위임.
			ResponseEntity<String> cacheResult = restTemplate.postForEntity(url, data.get(), String.class);
			System.out.println("cache result : " + cacheResult.getBody());
			
			return new ResponseEntity<>(data.get(), HttpStatus.OK);
		} 
		// db에 없는 경우. 
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/create")
	public Data_UsePtrn3monsRetv createData(@Valid @RequestBody Data_UsePtrn3monsRetv data) {
		System.out.println("Create Data : " + data.toString() + "...");

		return repository.save(data);
	}
	
	@PutMapping("/{svcContId}/{retvYm}")
	public ResponseEntity<Data_UsePtrn3monsRetv> updateData(@PathVariable("svcContId") String svcContId, @PathVariable("retvYm") String retvYm, @RequestBody Data_UsePtrn3monsRetv data) {
		System.out.println("Update Data with ID = " + svcContId + ", " + retvYm + "...");

		Optional<Data_UsePtrn3monsRetv> findedData = repository.findById(new PK_UsePtrn3monsRetv(svcContId, retvYm));
		if (findedData.isPresent()) {
			Data_UsePtrn3monsRetv savedData = findedData.get();
			
			savedData.setVcTlkTotQant(data.getVcTlkTotQant());
			savedData.setNtnlVcTlkTotQnt(data.getNtnlVcTlkTotQnt());
			savedData.setIntlVcTlkTotQnt(data.getIntlVcTlkTotQnt());
			savedData.setNtnlInnetVcTlkQnt(data.getNtnlInnetVcTlkQnt());
			savedData.setNtnlOutnetVcTlkQnt(data.getNtnlOutnetVcTlkQnt());
			savedData.setDataPacktQnt(data.getDataPacktQnt());
			savedData.setSmsUseQnt(data.getSmsUseQnt());			
			
			Data_UsePtrn3monsRetv updatedData = repository.save(savedData);
			return new ResponseEntity<>(updatedData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{svcContId}/{retvYm}")
	public ResponseEntity<String> deleteData(@PathVariable("svcContId") String svcContId, @PathVariable("retvYm") String retvYm) {
		System.out.println("Delete Data with ID = " + svcContId + ", " + retvYm + "...");

		try {
			repository.deleteById(new PK_UsePtrn3monsRetv(svcContId, retvYm));
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("Data has been deleted!", HttpStatus.OK);
	}
	
}
