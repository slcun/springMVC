/**
 * 
 */
package com.rfid.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rfid.socket.ScoketClient;

/**
 * 
 * @author Dolphin-PC
 * 2017年8月7日
 */
@Controller
@RequestMapping("/user")	//@RequestMapping注解用在类上时,方法上的就会映射为相对于类级别的请求
public class HelloController {
	
	@Resource
	ScoketClient scoketClient;
	
	@RequestMapping("/hello") //对应类上的注解就是这个请求.../user/hello
	public String hello(Model model){
		model.addAttribute("message", "Hello SpringMVC");
		return "hello";
	}

	@RequestMapping("/reader")
	public String reader(){
		return "reader";
	}
	
	@RequestMapping("/lop")
	public String lop(){
		scoketClient.sendLopCommand("asdada");
		
		return "reader";
	}
	
	@RequestMapping("/getmap")
	public String getmap(){
		System.out.println(ScoketClient.getMap());
		return "reader";
	}
	
	
	
	
	
	
}
