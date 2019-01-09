package com.garage.manage.repository;

import org.springframework.data.repository.CrudRepository;

import com.garage.manage.bean.UsageData;

public interface UsageDataRedisRepository extends CrudRepository<UsageData, String> {

}
