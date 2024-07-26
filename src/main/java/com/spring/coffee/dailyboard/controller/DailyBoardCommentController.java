package com.spring.coffee.dailyboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.coffee.dailyboard.service.DailyBoardCommentService;
import com.spring.coffee.dailyboard.vo.DailyBoardCommentVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("coffee/dailyboardComment")
public class DailyBoardCommentController {

	private final static String viewPath = "/WEB-INF/views/dailyboard/";

	@Value("${upload.directory}")
	private String uploadPath;

	@Autowired
	private DailyBoardCommentVO vo;

	@Autowired
	private DailyBoardCommentService service;

	@RequestMapping("regComment")
	public ModelAndView insertComment(@ModelAttribute("comment") DailyBoardCommentVO comment, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

//		log.info("boardNo: " + comment.getBoardNo());
//		log.info("nickname: " + comment.getNickname());
//		log.info("content: " + comment.getContent());
//		log.info("no: " + comment.getNo());
//		log.info("parentNo: " + comment.getParentNo());

//		service.insertComment(comment);
		mav.setViewName("redirect:/coffee/dailyboard/dailyBoardDetail?no=" + comment.getBoardNo());

		return mav;
	}

	@RequestMapping(value = "modComment", method = RequestMethod.POST, produces = "application/text; charset=utf-8")
	public @ResponseBody String modComment(@ModelAttribute("comment") DailyBoardCommentVO comment, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		vo = service.updateComment(comment);

		return vo.getContent();
	}

	@RequestMapping("delComment")
	public ModelAndView delComment(@RequestParam("no") int no, @RequestParam("boardNo") int boardNo) throws Exception {
		ModelAndView mav = new ModelAndView();
//		service.delComment(no, boardNo);

		mav.setViewName("redirect:/coffee/dailyboard/dailyBoardDetail?no="+boardNo);

		return mav;
	}


}
