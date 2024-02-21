package com.spring.coffee.coffeeboard.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.coffee.coffeeboard.service.CoffeeBoardService;
import com.spring.coffee.coffeeboard.vo.CoffeeBoardVO;
import com.spring.coffee.dailyboard.vo.DailyBoardCommentVO;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("coffee/coffeeboard")
public class CoffeeBoardController {

	private final static String viewPath = "/WEB-INF/views/coffeeboard/";
	
	@Value("${upload.directory}")
	private String uploadPath;
	
	@Autowired
	private CoffeeBoardVO vo;
	
	@Autowired
	private CoffeeBoardService service;
	
	@RequestMapping("coffeeBoardList")
	public @ResponseBody ModelAndView coffeeBoardList(@RequestParam(value = "pageNum", required = false) String pageNum, 
													@RequestParam(value = "keyword", required = false) String keyword, 
													@RequestParam(value = "keyField", required = false) String keyField, 
													HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pageNum", pageNum);
		paramMap.put("keyword", keyword);
		paramMap.put("keyField", keyField);
		Map<String, Object> map = service.listCoffeeBoard(paramMap, request, response);
		
		mav.addObject("isBoard", true);
		mav.addObject("searching", paramMap);
		mav.addObject("list", map);
		
		mav.addObject("center", viewPath + "coffeeBoardList.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("coffeeBoardForm")
	public ModelAndView coffeeBoardForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("isBoard", true);
		mav.addObject("center", viewPath + "boardForm.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("addCoffeeBoard")
	public ModelAndView insertCoffeeBoard(@RequestParam("files") List<MultipartFile> files, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		ModelAndView mav = new ModelAndView();
		
		service.insertCoffeeBoard(files, request, response);
		int no = service.getNewBoardNo();
		
		mav.setViewName("redirect:/coffee/coffeeboard/coffeeBoardDetail/" + no);
		
		return mav;
	}
	
	@RequestMapping("coffeeBoardDetail/{no}")
	public ModelAndView getCoffeeBoardDetail(@PathVariable("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		 
		service.readCountUpdate(no);
		vo = service.getCoffeeBoardDetail(no);
		String nickname = vo.getNickname();
		MemberVO owner = service.getOwnerDetail(nickname);
		
//		List<DailyBoardCommentVO> commentList = commentService.getCommentList(no);
//		log.info(commentList.toString());
		
//		mav.addObject("commentList", commentList);
		mav.addObject("owner", owner);
		mav.addObject("vo", vo);
		mav.addObject("center", viewPath + "coffeeBoardDetail.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("imageList/{no}/{fileName}")
	public void download(@PathVariable("no") int no, @PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		service.download(no, fileName, request, response);
	}
	
	
	@RequestMapping("thumbnail")
	public void thumbnail(@RequestParam("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
		service.thumbnail(no, request, response);
	}
	
}
