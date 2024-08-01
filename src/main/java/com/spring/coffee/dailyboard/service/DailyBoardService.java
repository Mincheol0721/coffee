package com.spring.coffee.dailyboard.service;

import java.util.Map;

import com.spring.coffee.dailyboard.vo.DailyBoardVO;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface DailyBoardService {
	Map<String, Object> selectDailyBoardList(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception;
	int insertDailyBoard(DailyBoardVO dailyBoardVo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	void uploadImg(Integer no, HttpServletRequest request, HttpServletResponse response) throws Exception;
	DailyBoardVO selectDailyBoardDetail(int no, HttpServletRequest request) throws Exception;
	MemberVO selectMemberDetail(String nickname);
	void updateReadCount(int no) throws Exception;
	int updateDailyBoard(DailyBoardVO dailyBoardVo, HttpServletRequest request, HttpServletResponse response) throws Exception;
	void delDailyBoard(int no) throws Exception;
	void updateImg(DailyBoardVO dailyBoardVo) throws Exception;
	void thumbnail(int no, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
