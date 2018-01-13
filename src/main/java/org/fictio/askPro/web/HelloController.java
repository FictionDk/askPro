package org.fictio.askPro.web;

import org.fictio.askPro.pojo.RequestData;
import org.fictio.askPro.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("hello")
	public String hello(@RequestBody RequestData<User> req){
		System.out.println(req.getCode());
		return "hello world";
	}
	
}
