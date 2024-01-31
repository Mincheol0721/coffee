package com.spring.coffee.dailyboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.coffee.dailyboard.vo.DailyBoardVO;
import com.spring.coffee.mapper.DailyBoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DailyBoardService {
	
	@Autowired
	private DailyBoardMapper mapper;
	
	@Autowired
	private DailyBoardVO vo;
	
	public Map<String, Object> listDailyBoard(String pageNum) throws Exception {
		int pageSize = 10;
		int pageBlock = 5;
		int count = 0;
		int no = 0;
		
		if(pageNum == null) pageNum = "1";
		
		int currentPage = Integer.parseInt(pageNum);
		count = mapper.getDailyBoardCount();
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<DailyBoardVO> vo = new ArrayList<>();
		
		if(count > 0) {
			vo = mapper.getDailyBoardList((startRow-1), endRow);
			no = count - (currentPage - 1) * pageSize;
		}
		
		map.put("pageSize", pageSize);
		map.put("pageBlock", pageBlock);
		map.put("count", count);
		map.put("no", no);
		map.put("currentPage", currentPage);
		map.put("vo", vo);
		
		return map;		
	}
		
	
}
