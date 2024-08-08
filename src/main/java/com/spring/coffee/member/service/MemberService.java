package com.spring.coffee.member.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

	MemberVO login(MemberVO memberVo) throws Exception;
	ModelAndView kakaoLogin(String code, HttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView googleLogin(String code, HttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView naverLogin(String code, String state, HttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView insertMemberInfo(MemberVO memberVo, MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception;
	ModelAndView insertKakaoMember(MemberVO memberVo, MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView insertGoogleMember(MemberVO memberVo, MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView insertNaverMember(MemberVO memberVo, MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView naverLogout(HttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView detailMember(HttpServletRequest request, HttpServletResponse response) throws Exception;
	ModelAndView modPassword(String curPassword, String password, String id) throws Exception;
	ModelAndView updateMemberInfo(MemberVO memberVo, MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception;
	void download(HttpServletRequest request, HttpServletResponse response) throws Exception;
	int deleteMember(MemberVO memberVo) throws Exception;
	void recoveryMemberInfo(MemberVO memberVo) throws Exception;
	void updateLoginInfo(MemberVO memberVo) throws Exception;
	MemberVO findMemberPassword(String id, String email) throws Exception;
	void updateTempPassword(String id, String randomPassword) throws Exception;
}
