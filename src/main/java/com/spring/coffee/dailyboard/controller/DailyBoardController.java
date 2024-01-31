package com.spring.coffee.dailyboard.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
	public @ResponseBody ModelAndView dailyBoardList(String pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	public ModelAndView addDailyBoard(
			/* @RequestParam("file") List<MultipartFile> files, */MultipartHttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		request.setCharacterEncoding("UTF-8");
		String absPath = uploadPath + "/dailyBoard/";
		
		Map map = new HashMap();
		
		Enumeration enu = request.getParameterNames();
		
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			
			String value = request.getParameter(key);
			
			map.put(key, value);
			log.info(key + ": " + value);
		}
		
		
		
		
		
		return mav;
	}
}
