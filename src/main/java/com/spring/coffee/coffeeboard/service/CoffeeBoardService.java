package com.spring.coffee.coffeeboard.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.coffee.coffeeboard.vo.CoffeeBoardVO;
import com.spring.coffee.mapper.CoffeeBoardMapper;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Service
@Slf4j
public class CoffeeBoardService {

	@Value("${upload.directory}")
	private String uploadPath;
	
	@Autowired
	private CoffeeBoardMapper mapper;
	
	@Autowired
	private CoffeeBoardVO vo;

	public Map<String, Object> listCoffeeBoard(Map<String, Object> paramMap, HttpServletRequest request,
			HttpServletResponse response) {
		int pageSize = 10;  //페이지 당 컨텐츠 개수 
		int pageBlock = 5;  //한 블럭 당 보여질 페이지 개수
		int count = 0;  //총 컨텐츠 개수
		int no = 0;  //컨텐츠 번호
		
		String pageNum = (String)paramMap.get("pageNum");
		String keyword = (String)paramMap.get("keyword");
		String keyField = (String)paramMap.get("keyField");
		
//		log.info("pageNum " + pageNum);
//		log.info("keyword: " + keyword);
//		log.info("keyField: " + keyField);
		
		Map<String, Object> countMap = new HashMap<String, Object>();
		countMap.put("keyField", keyField);
		countMap.put("keyword", keyword);
		
		if(pageNum == null) pageNum = "1";
		
		int currentPage = Integer.parseInt(pageNum);  //현재 페이지
		int startRow = (currentPage - 1) * pageSize + 1;  //페이지 내 컨텐츠 시작 번호
		int endRow = currentPage * pageSize;  //페이지 내 컨텐츠 끝 번호
		
		paramMap.put("startRow", (startRow-1));
		paramMap.put("endRow", endRow);
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<CoffeeBoardVO> vo = new ArrayList<>();
		
		if(keyword == null || keyword.length() == 0) {
			System.out.println("If");
			vo = mapper.getCoffeeBoardList(paramMap);
			count = mapper.getCoffeeBoardCount(); 
		} else if(keyword != null && keyword.length() > 0) {
			System.out.println("Else If");
			vo = mapper.searchCoffeeBoardList(paramMap);
			count = mapper.getSearchCoffeeBoardCount(countMap); 
		}
		no = count - (currentPage - 1) * pageSize;
//		log.info("count: " + count);
		
//		log.info("pageSize: " + pageSize);
//		log.info("pageBlock: " + pageBlock);
//		log.info("pageCount: " + (int)Math.ceil(count/pageSize));
		
		map.put("pageSize", pageSize);
		map.put("pageBlock", pageBlock);
		map.put("count", count);
		map.put("no", no);
		map.put("currentPage", currentPage);
		map.put("vo", vo);
		
		return map;		
	}

	public void thumbnail(@RequestParam("no") int no, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//사진을 내려받기 위한 출력 스트림 통로 객체 생성
		OutputStream os = response.getOutputStream();
		
		//다운로드할 파일위치의 파일경로 생성 
		String imgPath = uploadPath + "/coffeeboard/" + no;
		
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

	public void readCountUpdate(int no) throws Exception {
		mapper.readCountUpdate(no);
	}

	public CoffeeBoardVO getCoffeeBoardDetail(int no) throws Exception {
		return mapper.getCoffeeBoardDetail(no);
	}

	public MemberVO getOwnerDetail(String nickname) throws Exception {
		return mapper.getOwnerDetail(nickname); 
	}

	public ModelAndView insertCoffeeBoard(List<MultipartFile> files, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		ModelAndView mav = new ModelAndView();
		
		Map map = new HashMap();
		
		Enumeration enu = request.getParameterNames();
		
		while(enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}
		List<String> fileNames = new ArrayList<String>();
		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			fileNames.add(fileName);
		}
		map.put("fileList", fileNames);
		
		mapper.insertCoffeeBoard(map);
		int no = mapper.getNewBoardNo();
		String saveFolderPath = uploadPath + "/coffeeboard/" + no;
		
		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			
			InputStream in = file.getInputStream();
			
			Path saveFolder = Path.of(saveFolderPath);
			
			if(!Files.exists(saveFolder)) {
				Files.createDirectories(saveFolder);
			}
			
			Path savePath = saveFolder.resolve(fileName);
			
			Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
			
//			File fileDir = new File(uploadPath + "/coffeeboard/" + no);
//			fileDir.mkdirs();
//			
//			String filePath = uploadPath + "/coffeeboard/" + no + "/" + fileName;
//			File dest = new File(filePath);
//			
//			file.transferTo(dest);
			
			fileNames.add(fileName);
		}
		
		mav.addObject("map", map);
		mav.setViewName("redirect:/coffee/coffeeboard/coffeeBoardDetail/" + no);
		
		return mav;
	}

	public void download(int no, String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OutputStream out = response.getOutputStream();
		
//		log.info("fileName: " + fileName);
		
		String resourcePath = uploadPath + "/coffeeboard/" + no;
		String filePath = resourcePath + "/" + fileName;
		
		File file = new File(filePath);
		
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8"));
		
		FileInputStream in = new FileInputStream(file);
		
		byte[] b = new byte[1024 * 8];
		
		while(true) {
			int count = in.read(b);
			
			if(count == -1) break;
			
			out.write(b, 0, count);
		}
		
		in.close();
		out.close();
		
	}

	public int getNewBoardNo() {
		return mapper.getNewBoardNo();
	}
	
	
	
}
