package com.garage.usagedata.bean;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash("usePtrn3monsRetvRedis")
public class UsePtrn3monsRetvRedis {
	
	@Id
	private String key;
	
	private String data;
	
    private LocalDateTime refreshTime;

    public UsePtrn3monsRetvRedis(String key, String data, LocalDateTime refreshTime) {
        this.key = key;
        this.data = data;
        this.refreshTime = refreshTime;
    }
    
    
    public void refresh(String data, LocalDateTime refreshTime){
        if(refreshTime.isAfter(this.refreshTime)){ // 저장된 데이터보다 최신 데이터일 경우
        	this.data = data;
            this.refreshTime = refreshTime;
        }
    }

	public String getKey() {
		return key;
	}

	public String getData() {
		return data;
	}
	
	public LocalDateTime getRefreshTime() {
		return refreshTime;
	}  
    
}
