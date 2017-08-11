/**
 * 
 */
package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Dolphin-PC
 * 2017年8月7日
 */
@Controller
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello(Model model){
		model.addAttribute("message", "Hello SpringMVC");
		return "hello";
	}

}
