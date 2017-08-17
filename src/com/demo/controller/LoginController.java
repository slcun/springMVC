/**
 * 
 */
package com.demo.controller;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Dolphin-PC
 * 2017年8月16日
 */
@Controller
public class LoginController {

	
	private final Logger log = Logger.getLogger(LoginController.class);
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(){
		log.info("转跳到login页面");
		return "login";
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam String username, @RequestParam String password ,Model model){
		
		log.info("username = " + username + ", password = " + password);
		
		return null;
	}
}
