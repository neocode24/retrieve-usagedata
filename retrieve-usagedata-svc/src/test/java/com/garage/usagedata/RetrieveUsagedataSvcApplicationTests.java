package com.garage.usagedata;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garage.usagedata.bean.UsageData;
import com.garage.usagedata.remote.bean.Data_UsePtrn3monsRetv;
import com.garage.usagedata.repository.UsageDataRedisRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetrieveUsagedataSvcApplicationTests {

	@Autowired
    private UsageDataRedisRepository usageDataRedisRepository;

    @After
    public void tearDown() throws Exception {
    	usageDataRedisRepository.deleteAll();
    }

    @Test
    public void 기본_등록_조회기능() {
        //given
        String key = "usePtrn3monsRetv-680350947-201812";
        LocalDateTime refreshTime = LocalDateTime.now();
        
        UsageData usageData = UsageData.builder()
        	.key(key)
        	.usePtrn3monsRetv(new Data_UsePtrn3monsRetv("680350947", "201812"))
        	.refreshTime(refreshTime)
        	.build();
        
        //when
        usageDataRedisRepository.save(usageData);

        //then
        UsageData savedUsageData = usageDataRedisRepository.findById(key).get();
        
        assertThat(savedUsageData.getRefreshTime()).isEqualTo(refreshTime);
    }

}

