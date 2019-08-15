package com.  ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZoneOffset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.  .qnaservice.entity.ProfileResponse;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdaptorTest extends BaseActivityServiceApplicationTests {
	
/*	
	@Autowired
	ProfileAdaptor adaptor;*/
	@Test
	public void userProfileTest(){
		
	//	ProfileResponse response= adaptor.fetchProfileDetails("626199", ProfileResponse.class);
		//("Received profile service resonse ", response);
	//	assertEquals("User Profile Details are retrived sucessfully", response.getProfile().getParentSpecialtyId(), new Integer(21));
		
		
	}
	
	public static void main(String str[]){
		
		ZoneOffset ofset= ZoneOffset.UTC;
		
		System.out.println(ZoneOffset.ofHours(6));
	}

}
