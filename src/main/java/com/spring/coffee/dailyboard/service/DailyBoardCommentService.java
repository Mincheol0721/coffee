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

	public List<DailyBoardCommentVO> getCommentList(int no) {
		return mapper.getCommentList(no);
	}
	
	public void insertComment(DailyBoardCommentVO comment) {
		mapper.insertComment(comment);
	}

	public DailyBoardCommentVO updateComment(DailyBoardCommentVO comment) {
		mapper.updateComment(comment);
		
		return mapper.getComment(comment.getBoardNo(), comment.getNo());
	}

	public void delComment(int no, int boardNo) {
		mapper.delComment(no, boardNo);
	}

}
