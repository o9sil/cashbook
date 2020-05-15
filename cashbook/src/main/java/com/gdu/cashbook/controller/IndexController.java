package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	@GetMapping({"/", "/index"})
	public String index(@RequestParam(value = "msg", required = false) String msg, Model model) {		
		model.addAttribute("msg", msg);
		return "index";
	}
	
	@GetMapping("/home")
	public String home(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/login";
		}
		
		return "home";
	}
	
}
