package com.spring.coffee.member.controller;

import java.net.URLEncoder;

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
		ModelAndView mav = new ModelAndView();
		member = memberService.login(memberVo);

		//id와 비밀번호로 조회 해온 정보가 존재 시 로그인 처리
		if(member != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			session.setAttribute("isLogOn", true);
			session.setAttribute("isOwnMember", true);

			mav.setViewName("redirect:/main");
		//id 비밀번호로 조회한 정보가 존재하지 않을경우
		} else {
			rAttr.addFlashAttribute("result", "loginFailed");
			mav.setViewName("redirect:/member/loginForm");
		}
		return mav;
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
	public ModelAndView insertMemberInfo(@ModelAttribute MemberVO memberVo, @RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception {
		return memberService.insertMemberInfo(memberVo, file, request, rAttr);
	}

	@RequestMapping(value = "insertKakaoMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertKakaoMember(@ModelAttribute MemberVO memberVo, @RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("controller id: {}", memberVo.getId());
		return memberService.insertKakaoMember(memberVo, file, request, response);
	}

	@RequestMapping(value = "insertGoogleMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertGoogleMember(@ModelAttribute MemberVO memberVo, @RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.insertGoogleMember(memberVo, file, request, response);
	}

	@RequestMapping(value = "insertNaverMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView insertNaverMember(@ModelAttribute MemberVO memberVo, @RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		return memberService.insertNaverMember(memberVo, file, request, response);
	}

	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.invalidate();
		mav.setViewName("redirect:/main");
		mav.addObject("logout", "logout");

		return mav;
	}

	@RequestMapping("kakaoLogout")
	public ModelAndView kakaoLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		HttpSession session = request.getSession();
		session.invalidate();

		mav.setViewName("redirect:/main");

		return mav;
	}

	@RequestMapping("googleLogout")
	public ModelAndView googleLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();

		return new ModelAndView("redirect:/main");
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
	public ModelAndView updateMemberInfo(@ModelAttribute MemberVO memberVo, @RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception {
		return memberService.updateMemberInfo(memberVo, file, request, rAttr);
	}

	@RequestMapping("download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		memberService.download(request, response);
	}

	@RequestMapping("nickValidate")
	public String nickValidate(@RequestParam("nickname") String nickname, HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("닉네임 유효성 검사: " + nickname);

		int validatedNickname = memberDao.nickValidate(nickname);
		//System.out.println("닉네임: " + validatedNickname);

		if(validatedNickname == 0) {
			return "사용가능";
		} else {
			return "사용불가";
		}

	}

	@RequestMapping("idValidate")
	public String idValidate(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("아이디 유효성 검사: " + id);

		int validatedId = memberDao.idValidate(id);

		if(validatedId == 0) {
			return "사용가능";
		} else {
			return "사용불가";
		}

	}

	@RequestMapping("delMember")
	public void delMember(@ModelAttribute MemberVO memberVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		memberVo = (MemberVO) request.getSession().getAttribute("member");
		memberService.deleteMember(memberVo);
		request.getSession().invalidate();
	}


}
