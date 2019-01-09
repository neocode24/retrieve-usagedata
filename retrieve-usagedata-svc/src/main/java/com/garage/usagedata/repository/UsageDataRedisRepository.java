package com.garage.usagedata.repository;

import org.springframework.data.repository.CrudRepository;

import com.garage.usagedata.bean.UsageData;

public interface UsageDataRedisRepository extends CrudRepository<UsageData, String> {

}
