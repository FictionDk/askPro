package org.fictio.askPro;


import org.fictio.askPro.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

/**
 * http://docs.spring.io/spring-boot/docs/current/reference/
 * 	html/boot-features-testing.html
 * 	#boot-features-configfileapplicationcontextinitializer-test-utility
 * @author dk
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class AskProApplicationTests {
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	public void contextLoads() {
		String url = "http://localhost:"+port+"/hello";
		String result = restTemplate.getForObject(url, String.class);
		System.out.println(result);
	}
	@Test
	public void jsonTest(){
		String url = "http://localhost:"+port+"/user/getRegisteCode";
		Gson gson = new Gson();
		User u = new User();
		u.setUserName("大牛");
		u.setUserMobile("123");
		String p = "json="+gson.toJson(u);
		
		String r = restTemplate.postForObject(url, p, String.class);
		System.out.println(r);
	}

}
