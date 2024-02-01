package com.spring.coffee.dailyboard.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.coffee.dailyboard.vo.DailyBoardVO;
import com.spring.coffee.mapper.DailyBoardMapper;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DailyBoardService {
	
	private final static String viewPath = "/WEB-INF/views/board/";
	
	@Value("${upload.directory}")
	private String uploadPath;
	
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

	public void insertDailyBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String absPath = uploadPath + "/dailyBoard/";
		Map map = new HashMap();
		
		Enumeration enu = request.getParameterNames();
		
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			
			String value = request.getParameter(key);
			
			map.put(key, value);
			log.info(key + ": " + value);
		}
		
		mapper.addDailyBoard(map);
	}

	public List<String> uploadImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		List<String> fileNames = new ArrayList<String>();
				
		// TODO Auto-generated method stub
		int no = mapper.getDailyBoardNo() + 1;
		//File Information
		//파일정보         
		String sFileInfo = "";
		//파일명을 받는다 - 일반 원본파일명         
		String hFilename = request.getHeader("file-name");
		//파일 확장자         
		String filename_ext = hFilename.substring(hFilename.lastIndexOf(".") + 1);
		//확장자를소문자로 변경         
		filename_ext = filename_ext.toLowerCase();
		//파일 기본경로
		String dftFilePath = uploadPath + "/dailyBoard/";
		//파일 기본경로 _ 상세경로         
		String filePath = dftFilePath + no + "/";
		File file = new File(filePath);

		if(!file.exists()) {
			file.mkdirs();
		}
		String fileName = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today= formatter.format(new java.util.Date());
		fileName = today+UUID.randomUUID().toString() + hFilename.substring(hFilename.lastIndexOf("."));
		String rlFileNm = filePath + fileName;
		
		///////////////// 서버에 파일쓰기 /////////////////          
		InputStream is = request.getInputStream();
		OutputStream os=new FileOutputStream(rlFileNm);
		int numRead;
		byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];

		while((numRead = is.read(b,0,b.length)) != -1) {
			os.write(b,0,numRead);
		}
		if(is != null) {
			is.close();
		}
		os.flush();
		os.close();
		
		///////////////// 서버에 파일쓰기 /////////////////         
		// 정보 출력         
		sFileInfo += "&bNewLine=true";
		// img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함         
		sFileInfo += "&sFileName="+ hFilename;
		sFileInfo += "&sFileURL=/dailyboard/" + no + "/" + fileName;
		
		System.out.println("sfileInfo: " + sFileInfo);
		log.info("realFileName: " + fileName);
		fileNames.add(fileName);
		
		PrintWriter print = response.getWriter();
		
		print.print(sFileInfo);
		print.flush();
		print.close();
		
		mapper.addFile(fileNames, no);
		
		return fileNames;
	}

	public DailyBoardVO getDailyBoardDetail(int no) {
		return mapper.getDailyBoardDetail(no);
	}

	public void readCountUpdate(int no) {
		mapper.readCountUpdate(no);
	}

	public MemberVO getOwnerDetail(String nickname) {
		return mapper.getOwnerDetail(nickname);
	}

	public int updateDailyBoard(HttpServletRequest request, HttpServletResponse response) {

		String absPath = uploadPath + "/dailyBoard/";
		Map map = new HashMap();
		
		Enumeration enu = request.getParameterNames();
		
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			
			String value = request.getParameter(key);
			
			map.put(key, value);
			log.info(key + ": " + value);
		}
		
		mapper.updateDailyBoard(map);
		
		return Integer.parseInt(map.get("no").toString());
	}

	public void delDailyBoard(int no) {
		mapper.delDailyBoard(no);
	}
		
	
}
