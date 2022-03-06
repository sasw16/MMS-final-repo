package com.nrifintech.mms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LogoutController {
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//ModelAndView m = new ModelAndView("index");
		session.removeAttribute("user");
		
		System.out.println(session.getAttribute("user"));
		return "redirect:/";
	}
}
