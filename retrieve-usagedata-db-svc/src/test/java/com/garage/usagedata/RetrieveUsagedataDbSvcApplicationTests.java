package com.garage.usagedata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garage.usagedata.bean.Data_DataSvcDrctlyUseQntList;
import com.garage.usagedata.bean.Data_UsePtrn3monsRetv;
import com.garage.usagedata.bean.PK_DataSvcDrctlyUseQntList;
import com.garage.usagedata.bean.PK_UsePtrn3monsRetv;
import com.garage.usagedata.repository.DataSvcDrctlyUseQntListRepository;
import com.garage.usagedata.repository.UsePtrn3monsRetvRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RetrieveUsagedataDbSvcApplicationTests {
	
	@Autowired
    private DataSvcDrctlyUseQntListRepository dataSvcDrctlyUseQntListRepository;
	
	@Autowired
	UsePtrn3monsRetvRepository usePtrn3monsRetvRepositoryRepository;

    @After
    public void tearDown() throws Exception {
//    	repsitory.deleteAll();
    }


//	@Test
	public void getData_datasvcdrctlyuseqntlistRepositoryRepository() {
		String svcContId = "580095369";
		String retvDt = "20190108";
		
		Data_DataSvcDrctlyUseQntList data = dataSvcDrctlyUseQntListRepository.findById(new PK_DataSvcDrctlyUseQntList(svcContId, retvDt)).get();
		
		System.out.println(data.toString());
		System.out.println(data.getProdNm());
        
        
        assertThat(data.getSvcContId()).isEqualTo("580095369");
        assertThat(data.getRetvDt()).isEqualTo("20190108");
	}
	
	@Test
	public void getData_useptrn3monsretvRepositoryRepository() {
		String svcContId = "680350947";
		String retvYm = "201811";
		
		Data_UsePtrn3monsRetv data = usePtrn3monsRetvRepositoryRepository.findById(new PK_UsePtrn3monsRetv(svcContId, retvYm)).get();
		
		System.out.println(data.toString());
		System.out.println(data.getNtnlVcTlkTotQnt());
		
		
		assertThat(data.getSvcContId()).isEqualTo("680350947");
        assertThat(data.getRetvYm()).isEqualTo("201811");
	}
}

