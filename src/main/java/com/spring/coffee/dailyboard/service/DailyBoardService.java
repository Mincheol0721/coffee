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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.coffee.dailyboard.vo.DailyBoardVO;
import com.spring.coffee.mapper.DailyBoardMapper;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

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
		int no = mapper.getDailyBoardNo();
		
		Enumeration enu = request.getParameterNames();
		
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			
			String value = request.getParameter(key);
			
			map.put(key, value);
			log.info(key + ": " + value);
		}
		
		mapper.addDailyBoard(map);
		map.put("no", no);
		updateImg(map);
		
	}

	public void uploadImg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		List<String> fileNames = new ArrayList<String>();
				
		// TODO Auto-generated method stub
		int no = mapper.getDailyBoardNo() + 1;
		//File Information
		//파일정보         
		String sFileInfo = "";
		//파일명을 받는다 - 일반 원본파일명         
		String sFileName = request.getHeader("file-name");
		//파일 확장자         
		String filename_ext = sFileName.substring(sFileName.lastIndexOf(".") + 1);
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
		fileName = today+UUID.randomUUID().toString() + sFileName.substring(sFileName.lastIndexOf("."));
		String rlFileNm = filePath + fileName;
		
		///////////////// 서버에 파일쓰기 /////////////////          
		InputStream is = request.getInputStream();
		OutputStream os = new FileOutputStream(rlFileNm);
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
		sFileInfo += "&sFileName="+ sFileName;
		sFileInfo += "&sFileURL=/dailyboard/" + no + "/" + fileName;
		
		System.out.println("sfileInfo: " + sFileInfo);
		
		PrintWriter print = response.getWriter();
		
		print.print(sFileInfo);
		print.flush();
		print.close();
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
		
		updateImg(map);
		
		return Integer.parseInt(map.get("no").toString());
	}

	public void delDailyBoard(int no) {
		mapper.delDailyBoard(no);
	}
	
	public void updateImg(Map<String, Object> map) {
		List<String> fileNames = new ArrayList<String>();
		Map<String, Object> fileMap = new HashMap();
		
		//HTML 파싱 및 조작을 위한 Jsoup 라이브러리 의존주입
		Document doc = Jsoup.parse(map.get("content").toString());
		log.info("doc: " + doc.html());
		
		//img 태그 선택
		Elements imgs = doc.select("img");
//		log.info("imgs: " + imgs.html());
		for(Element img : imgs) {
			log.info("img: " + img);
			String src = img.attr("src");
			src = src.substring(src.lastIndexOf('/')+1);
			log.info("src: " + src);
			fileNames.add(src);
		}
		fileMap.put("fileNames", fileNames);
		fileMap.put("no", Integer.parseInt(map.get("no").toString()));
		log.info("img no: " + Integer.parseInt(map.get("no").toString()));
		
		mapper.addFile(fileMap);
	}
	
	public void thumbnail(@RequestParam("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//사진을 내려받기 위한 출력 스트림 통로 객체 생성
		OutputStream os = response.getOutputStream();
		
		//다운로드할 파일위치의 파일경로 생성 
		String imgPath = uploadPath + "/dailyboard/" + no;
		
		//이미지 파일을 접근해서 파일을 조작, 정보보기 등을 할 수 있는 파일 객체 생성
		File imgs = new File(imgPath);
		//파일경로에 존재하는 이미지 파일들을 담을 파일배열 생성
		File[] files = imgs.listFiles();
		
		String imgName = "";
		
		//만약 경로내에 파일이 존재한다면 첫번째 파일의 경로 반환
		if(files != null && files.length > 0) {
			imgName = files[0].getAbsolutePath();
		} else {
			imgName = uploadPath + "/images/logo.png";
		}
		File img = new File(imgName);
		
		Thumbnails.of(imgName).size(220, 220).toOutputStream(os);
		
		byte[] b = new byte[10 * 1024];
		os.write(b);
		
	}
	
	
}
