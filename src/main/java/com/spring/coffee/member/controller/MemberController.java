package com.spring.coffee.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.coffee.member.dao.MemberDaoImpl;
import com.spring.coffee.member.service.MemberServiceImpl;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {

	private final static String viewPath = "/WEB-INF/views/member/";

	@Value("${upload.directory}")
	private String uploadPath;

	@Autowired
	MemberServiceImpl memberService;

	@Autowired(required = false)
	MemberVO member;

	@Autowired
	MemberDaoImpl memberDao;

	@RequestMapping("loginForm")
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("center", viewPath + "loginForm.jsp");
		mav.setViewName("main");

		return mav;
	}

	@RequestMapping("login")
	public ModelAndView login(@ModelAttribute MemberVO memberVo, RedirectAttributes rAttr, HttpServletRequest request) throws Exception {
		return memberService.login(memberVo, rAttr, request);
	}

	@RequestMapping(value="kakaoLogin", produces = "application/json;charset=UTF-8", method=RequestMethod.GET)
	public @ResponseBody ModelAndView kakaoLogin(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.kakaoLogin(code, request, response);
	}

	@RequestMapping("oauth2google")
	public @ResponseBody ModelAndView googleLogin(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.googleLogin(code, request, response);
	}

	@RequestMapping("naverLogin")
	public @ResponseBody ModelAndView naverLogin(String code,String state, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.naverLogin(code, state, request, response);
	}

	@RequestMapping("regForm")
	public ModelAndView regForm(@ModelAttribute MemberVO memberVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("center", viewPath + "regForm.jsp");
		mav.setViewName("main");

		return mav;
	}

	@RequestMapping(value="insertMemberInfo", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertMemberInfo(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception {
		return memberService.insertMemberInfo(file, request, rAttr);
	}

	@RequestMapping(value = "insertKakaoMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertKakaoMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		return insertKakaoMember(file, request, response);
	}

	@RequestMapping(value = "insertGoogleMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertGoogleMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.insertGoogleMember(file, request, response);
	}

	@RequestMapping(value = "insertNaverMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertNaverMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.insertNaverMember(file, request, response);
	}

	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.invalidate();
		mav.setViewName("redirect:/coffee/main");
		mav.addObject("logout", "logout");

		return mav;
	}

	@RequestMapping("kakaoLogout")
	public ModelAndView kakaoLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		session.invalidate();

		mav.setViewName("redirect:/coffee/main");

		return mav;
	}

	@RequestMapping("googleLogout")
	public ModelAndView googleLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();

		return new ModelAndView("redirect:/coffee/main");
	}

	@RequestMapping("naverLogout")
	public ModelAndView naverLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.naverLogout(request, response);
	}

	@RequestMapping("memberDetail")
	public ModelAndView detailMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.detailMember(request, response);
	}

	@RequestMapping("modForm")
	public ModelAndView modForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		mav.addObject("center", viewPath + "modMemberForm.jsp");
		mav.setViewName("main");

		return mav;
	}

	@RequestMapping("modPasswordForm")
	public ModelAndView modPasswordForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		mav.addObject("center", viewPath + "modPasswordForm.jsp");
		mav.setViewName("main");

		return mav;
	}

	@RequestMapping("modPassword")
	public ModelAndView modPassword(@RequestParam("curPassword") String curPassword, @RequestParam("password") String password, @RequestParam("id") String id) throws Exception {
		return memberService.modPassword(curPassword, password, id);
	}

	@RequestMapping(value="modMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView modMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception {
		return memberService.modMember(file, request, rAttr);
	}

	@RequestMapping("download")
	public void download(@RequestParam("nickname") String nickname, HttpServletRequest request, HttpServletResponse response) throws Exception {
		memberService.download(nickname, request, response);
	}

//	@RequestMapping("nickValidate")
//	public String nickValidate(@RequestParam("nickname") String nickname, HttpServletRequest request, HttpServletResponse response) {
//		//System.out.println("닉네임 유효성 검사: " + nickname);
//
//		String validatedNickname = memberMapper.nickValidate(nickname);
//		//System.out.println("닉네임: " + validatedNickname);
//
//		if(validatedNickname == null) {
//			return "사용가능";
//		} else {
//			return "사용불가";
//		}
//
//	}
//
//	@RequestMapping("idValidate")
//	public String idValidate(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
//		//System.out.println("아이디 유효성 검사: " + id);
//
//		String validatedId = memberMapper.idValidate(id);
//		//System.out.println("닉네임: " + validatedId);
//
//		if(validatedId == null) {
//			return "사용가능";
//		} else {
//			return "사용불가";
//		}
//
//	}

	@RequestMapping("delMember")
	public void delMember(@ModelAttribute MemberVO memberVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		memberService.deleteMember(memberVo);
		request.getSession().invalidate();
	}


}
