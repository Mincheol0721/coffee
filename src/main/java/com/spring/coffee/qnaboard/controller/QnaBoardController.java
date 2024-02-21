package com.spring.coffee.qnaboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController("coffee/qnaboard")
@Slf4j
public class QnaBoardController {
	
	@RequestMapping("qnaBoardList")
	public @ResponseBody ModelAndView qnaBoardList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		log.info("qnaBoardList");
		
		mav.addObject("center", "/WEB-INF/views/qnaboard/qnaBoarList.jsp");
		mav.setViewName("main"); 
		
		return mav;
	}
	
}
