package com.spring.coffee.main.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
	
	@RequestMapping("main")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("center", "/WEB-INF/views/common/index.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
}
