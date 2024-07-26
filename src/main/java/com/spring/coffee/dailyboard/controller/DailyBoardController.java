//package com.spring.coffee.dailyboard.controller;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.spring.coffee.dailyboard.service.DailyBoardCommentService;
//import com.spring.coffee.dailyboard.service.DailyBoardService;
//import com.spring.coffee.dailyboard.vo.DailyBoardCommentVO;
//import com.spring.coffee.dailyboard.vo.DailyBoardVO;
//import com.spring.coffee.member.vo.MemberVO;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@Slf4j
//@RequestMapping("coffee/dailyboard")
//public class DailyBoardController {
//
//	private final static String viewPath = "/WEB-INF/views/dailyboard/";
//
//	@Value("${upload.directory}")
//	private String uploadPath;
//
//	@Autowired
//	private DailyBoardVO vo;
//
//	@Autowired
//	private DailyBoardService service;
//
//	@Autowired
//	private DailyBoardCommentService commentService;
//
//	@RequestMapping("dailyBoardList")
//	public @ResponseBody ModelAndView dailyBoardList(@RequestParam(value = "pageNum", required = false) String pageNum,
//													@RequestParam(value = "keyword", required = false) String keyword,
//													@RequestParam(value = "keyField", required = false) String keyField,
//													HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mav = new ModelAndView();
//
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("pageNum", pageNum);
//		paramMap.put("keyword", keyword);
//		paramMap.put("keyField", keyField);
//		Map<String, Object> map = service.listDailyBoard(paramMap, request, response);
//
//		mav.addObject("isBoard", true);
//		mav.addObject("searching", paramMap);
//		mav.addObject("list", map);
//		mav.addObject("center", viewPath + "dailyBoardList.jsp");
//		mav.setViewName("main");
//
//		return mav;
//	}
//
//	@RequestMapping("dailyBoardForm")
//	public ModelAndView dailyBoardForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mav = new ModelAndView();
//
//		mav.addObject("isBoard", true);
//		mav.addObject("center", viewPath + "boardForm.jsp");
//		mav.setViewName("main");
//
//		return mav;
//	}
//
//	@RequestMapping("addDailyBoard")
//	public ModelAndView addDailyBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("UTF-8");
//
//		ModelAndView mav = new ModelAndView();
//
//		service.insertDailyBoard(request, response);
//
//		mav.setViewName("redirect:/coffee/dailyboard/dailyBoardList");
//
//		return mav;
//	}
//
//	@RequestMapping("seImgUploader")
//	public void seImgUploader(HttpServletRequest request, HttpServletResponse response) throws Exception {
////		log.info("controller filename: " + request.getHeader("file-name"));
//		service.uploadImg(request, response);
//	}
//
//	@RequestMapping("dailyBoardDetail")
//	public ModelAndView getDailyBoardDetail(@RequestParam("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mav = new ModelAndView();
//
//		service.readCountUpdate(no);
//		vo = service.getDailyBoardDetail(no);
//		String nickname = vo.getNickname();
////		MemberVO owner = service.getOwnerDetail(nickname);
//
////		List<DailyBoardCommentVO> commentList = commentService.getCommentList(no);
////		log.info(commentList.toString());
//
////		mav.addObject("commentList", commentList);
////		mav.addObject("owner", owner);
//		mav.addObject("vo", vo);
//		mav.addObject("center", viewPath + "dailyBoardDetail.jsp");
//		mav.setViewName("main");
//
//		return mav;
//	}
//
//	@RequestMapping("modDailyBoardForm")
//	public ModelAndView modDailyBoardForm(@RequestParam("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mav = new ModelAndView();
//
//		vo = service.getDailyBoardDetail(no);
//		mav.addObject("vo", vo);
//		mav.addObject("center", viewPath + "modDailyBoardForm.jsp");
//		mav.setViewName("main");
//
//		return mav;
//	}
//
//	@RequestMapping("modDailyBoard")
//	public ModelAndView modDailyBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("UTF-8");
//
//		ModelAndView mav = new ModelAndView();
//
//		int no = service.updateDailyBoard(request, response);
//
//		mav.setViewName("redirect:/coffee/dailyboard/dailyBoardDetail?no="+no);
//
//		return mav;
//	}
//
//	@RequestMapping("delDailyBoard")
//	public ModelAndView delDailyBoard(@RequestParam("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		service.delDailyBoard(no);
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("redirect:/coffee/dailyboard/dailyBoardList");
//		return mav;
//	}
//
//	@RequestMapping("/thumbnail")
//	public void thumbnail(@RequestParam("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		service.thumbnail(no, request, response);
//	}
//
//}
