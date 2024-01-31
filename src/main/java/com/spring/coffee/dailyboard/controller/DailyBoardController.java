package com.spring.coffee.dailyboard.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.coffee.dailyboard.service.DailyBoardService;
import com.spring.coffee.dailyboard.vo.DailyBoardVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("coffee/board")
public class DailyBoardController {
	
	private final static String viewPath = "/WEB-INF/views/board/";
	
	@Value("${upload.directory}")
	private String uploadPath;
	
	@Autowired
	private DailyBoardVO vo;
	
	@Autowired
	private DailyBoardService service;
	
	@RequestMapping("dailyBoardList")
	public @ResponseBody ModelAndView dailyBoardList(@RequestParam(value = "pageNum", required = false) String pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map = service.listDailyBoard(pageNum);
		
		mav.addObject("isBoard", true);
		mav.addObject("list", map);
		mav.addObject("center", viewPath + "dailyBoardList.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("dailyBoardForm")
	public ModelAndView dailyBoardForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("isBoard", true);
		mav.addObject("center", viewPath + "boardForm.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("addDailyBoard")
	public ModelAndView addDailyBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		ModelAndView mav = new ModelAndView();
		
		service.insertDailyBoard(request, response);
//		insertDailyBoardImg(request, response);
		
		mav.setViewName("redirect:/coffee/board/dailyBoardList");
		
		return mav;
	} 
	
	@RequestMapping("uploadImg")
	public void uploadImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		log.info("controller filename: " + request.getHeader("file-name"));
		service.uploadImg(request, response);
	}
	
}
