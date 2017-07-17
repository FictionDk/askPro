package org.fictio.askPro.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
	@RequestMapping("/register")
	public String register(){
		return "register";
	}
	@RequestMapping("/userIndex")
	public String ucenterIndex(){
		return "ucenterIndex";
	}
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	@RequestMapping("/questEdit")
	public String questEdit(){
		return "questionEdit";
	}
	@RequestMapping("/myQuest")
	public String myQuestion(){
		return "myQuestion";
	}
}
