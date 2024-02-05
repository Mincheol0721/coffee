package com.spring.coffee.dailyboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.coffee.dailyboard.vo.DailyBoardCommentVO;
import com.spring.coffee.mapper.DailyBoardCommentMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class DailyBoardCommentService {

	@Autowired
	private DailyBoardCommentMapper mapper;
	/*
	public void insertComment(DailyBoardCommentVO comment, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		mapper.insertComment(comment);
	}
	*/
	public List<DailyBoardCommentVO> getCommentList(int no) {
		return mapper.getCommentList(no);
	}

}
