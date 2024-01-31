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

	public void uploadImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		List<String> fileList = new ArrayList<String>();
		
		// TODO Auto-generated method stub
		int no = mapper.getDailyBoardNo() + 1;
		//File Information
		String sFileInfo = "";
		//Original File Name
		String sFileName = request.getHeader("file-name");
		log.info("sfilename: " + sFileName);
		//File Extension
		String sFileNameExt = sFileName.substring(sFileName.lastIndexOf(".") + 1);
		log.info("sFileNameExt: " + sFileNameExt);
		//toLowerCase
		sFileNameExt = sFileNameExt.toLowerCase();
		//PrintWriter
		PrintWriter pw = response.getWriter();
				
		//Img validation variables
		String[] allowFileArr = {"jpg", "png", "bmp", "gif"};
		
		//Extension Validate
		int nCnt = 0;
		for(int i=0; i<allowFileArr.length; i++) {
			if(sFileNameExt.equals(allowFileArr[i])) {
				nCnt++;
			}
		}
		
		//if not img
		if(nCnt == 0) {
			pw.print("NOTALLOW_" + sFileName);
			pw.flush();
			pw.close();
		} else {
			//Directory setting and Upload
			String filePath =  uploadPath + "/dailyBoard/" + no + "/";
			File file = new File(filePath);
			
			if(!file.exists()) {
				file.mkdirs();
			}
			
			String sRealFileName = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String today = sdf.format(new java.util.Date());
			sRealFileName = today + UUID.randomUUID().toString() + sFileName.substring(sFileName.lastIndexOf("."));
			fileList.add(sRealFileName);
			log.info("sRealFileName: " + sRealFileName);
			
			String rlFileName = filePath + sRealFileName;
			
			log.info("rlFileName: " + rlFileName);
			
			//Input File To Server
			InputStream in = request.getInputStream();
			OutputStream out = new FileOutputStream(rlFileName);
			int numRead;
			byte[] bytes = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while ( (numRead = in.read(bytes, 0, bytes.length)) != -1 ) {
				out.write(bytes, 0, numRead);
			}
			if(in != null) {
				in.close();
			}
			out.flush();
			out.close();
			//IMG
			//Print Info
			sFileInfo += "&bNewLine=true";
			//title = original file name processing
			sFileInfo += "&sFileName=" + sFileName;
			sFileInfo += "&sFileURL=" + filePath + sRealFileName;

			pw.print(sFileInfo);
			pw.flush();
			pw.close();
			
		}
	}
		
	
}
