package org.fictio.askPro.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("hello")
	public String hello(){
		return "hello world";
	}
	
}
