package com.spring.coffee.dailyboard.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("coffee/board")
public class DailyBoardController {
	
	private final static String viewPath = "/WEB-INF/views/board/";
	
	@Value("${upload.directory}")
	private String uploadPath;
	
	@RequestMapping("dailyBoardList")
	public @ResponseBody ModelAndView dailyBoardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("dailyBoardList 메소드");
		ModelAndView mav = new ModelAndView();
		
		
		mav.addObject("center", viewPath + "/dailyBoardList.jsp");
		mav.setViewName("main");
		
		return mav;
	}
}
