package com.spring.coffee.dailyboard.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.coffee.dailyboard.dao.DailyBoardDaoImpl;
import com.spring.coffee.dailyboard.vo.DailyBoardFilesInfoVo;
import com.spring.coffee.dailyboard.vo.DailyBoardVO;
import com.spring.coffee.member.dao.MemberDaoImpl;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DailyBoardServiceImpl implements DailyBoardService {

	private final static String viewPath = "/WEB-INF/views/dailyboard/";

	@Value("${upload.directory}")
	private String uploadPath;

	@Autowired
	private DailyBoardDaoImpl dailyBoardDao;

	@Autowired
	private DailyBoardVO dailyBoardVO;

	@Autowired
	private MemberDaoImpl memberDao;

	private int insCnt,
				updCnt,
				delCnt;

	@Override
	public Map<String, Object> selectDailyBoardList(Map<String, Object> paramMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int pageSize = 10;  //페이지 당 컨텐츠 개수
		int pageBlock = 5;  //한 블럭 당 보여질 페이지 개수
		int count = 0;  //총 컨텐츠 개수
		int no = 0;  //컨텐츠 번호

		String pageNum = (String)paramMap.get("pageNum");
		String keyword = (String)paramMap.get("keyword");
		String keyField = (String)paramMap.get("keyField");

		Map<String, Object> countMap = new HashMap<String, Object>();
		countMap.put("keyField", keyField);
		countMap.put("keyword", keyword);

		if(pageNum == null) pageNum = "1";

		int currentPage = Integer.parseInt(pageNum);  //현재 페이지
		int startRow = (currentPage - 1) * pageSize + 1;  //페이지 내 컨텐츠 시작 번호
		int endRow = currentPage * pageSize;  //페이지 내 컨텐츠 끝 번호

		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);

		Map<String, Object> map = new HashMap<String, Object>();
		List<DailyBoardVO> vo = new ArrayList<>();

		vo = dailyBoardDao.selectDailyBoardInfoList(paramMap);
		count = dailyBoardDao.selectDailyBoardCountInfo(countMap);

		no = count - (currentPage - 1) * pageSize;

		map.put("pageSize", pageSize);
		map.put("pageBlock", pageBlock);
		map.put("count", count);
		map.put("no", no);
		map.put("currentPage", currentPage);
		map.put("vo", vo);

		return map;
	}

	@Override
	public int insertDailyBoard(DailyBoardVO dailyBoardVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String absPath = uploadPath + "/dailyBoard/";
		Map map = new HashMap();
		int no = 0;

		setBoardVoFileName(dailyBoardVo);

		dailyBoardDao.insertDailyBoardInfoRow(dailyBoardVo);

//		log.info("newBoardNo: " + no);
		no = dailyBoardDao.selectDailyBoardCountInfo(dailyBoardVo.getId());
		dailyBoardVO.setNo(no);
		dailyBoardVo = dailyBoardDao.selectDailyBoardInfoRow(dailyBoardVO);

		updateImg(dailyBoardVo);

		return no;
	}

	private void setBoardVoFileName(DailyBoardVO dailyBoardVo) {
		//HTML 파싱 및 조작을 위한 Jsoup 라이브러리 의존주입
		Document doc = Jsoup.parse(dailyBoardVo.getContent());
		StringBuilder sb = new StringBuilder();

		//img 태그 선택
		Elements imgs = doc.select("img");
		for(Element img : imgs) {
			String src = img.attr("src");
			src = src.substring(src.lastIndexOf('/')+1);
//					log.info("** src: {}", src);
			if (!src.trim().isEmpty() ) {
				if(sb.length() > 0) {
					sb.append(", ");
				}
				sb.append(src);
			}
		}
		dailyBoardVo.setFileName(sb.toString());
	}

	@Override
	public void uploadImg(Integer no, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();
	    MemberVO memberVo = (MemberVO) session.getAttribute("member");
	    List<String> fileNames = new ArrayList<String>();

	    if (no == null) {
	        no = dailyBoardDao.selectMaxNo() + 1;
	    }

	    // 저장된 파일명을 가져와 해당 파일 명이 존재하지 않으면 삭제, 존재하면 유지하도록 내용 추가
	    // TO DO

	    // 파일 정보를 수집
	    String sFileInfo = "";
	    String sFileName = request.getHeader("file-name");
	    if (sFileName == null || sFileName.isEmpty()) {
	        // 파일 이름이 없는 경우, 아무 것도 하지 않음
	        return;
	    }
	    log.info("** 받아온 파일 명: {}", sFileName);
	    // 파일 확장자
	    String filename_ext = sFileName.substring(sFileName.lastIndexOf(".") + 1).toLowerCase();

	    // 파일 기본 경로
	    String dftFilePath = uploadPath + "/dailyBoard/";
	    String filePath = dftFilePath + no + "/";
	    File fileDir = new File(filePath);

	    // 새로운 파일명 생성
	    String fileName = "";
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	    String today = formatter.format(new java.util.Date());
	    fileName = today + UUID.randomUUID().toString() + sFileName.substring(sFileName.lastIndexOf("."));
	    String rlFileNm = filePath + fileName;

	    if (!fileDir.exists()) {
	        fileDir.mkdirs();
	    } else {
	        // 폴더 내 모든 파일 삭제
	        File[] files = fileDir.listFiles();
	        if (files != null) {
	            for (File file : files) {
	                if (!file.isDirectory()) {
	                	String fileNm = file.getName().substring(0,8);

	                	if(!fileNm.equals(today.substring(0,8))) {
	                		file.delete();
	                	}
	                }
	            }
	        }
	    }

	    // 서버에 파일 쓰기
	    try (InputStream is = request.getInputStream();
	         OutputStream os = new FileOutputStream(rlFileNm)) {
	        byte[] b = new byte[Integer.parseInt(request.getHeader("file-size"))];
	        int numRead;
	        while ((numRead = is.read(b, 0, b.length)) != -1) {
	            os.write(b, 0, numRead);
	        }
	    }

	    // 정보 출력
	    sFileInfo += "&bNewLine=true";
	    sFileInfo += "&sFileName=" + sFileName;
	    sFileInfo += "&sFileURL=/dailyboard/" + no + "/" + fileName;

	    try (PrintWriter print = response.getWriter()) {
	        print.print(sFileInfo);
	        print.flush();
	    }
	}

	@Override
	public DailyBoardVO selectDailyBoardDetail(int no, HttpServletRequest request) throws Exception {
		dailyBoardVO.setNo(no);
		return dailyBoardDao.selectDailyBoardInfoRow(dailyBoardVO);
	}

	@Override
	public MemberVO selectMemberDetail(String id) {
		MemberVO vo = new MemberVO();
		vo.setId(id);
		return memberDao.selectMemberInfoRow(vo);
	}

	@Override
	public void updateReadCount(int no) throws Exception {
		dailyBoardDao.updateReadCount(no);
	}

	@Override
	public int updateDailyBoard(DailyBoardVO dailyBoardVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int updCnt = dailyBoardDao.updateDailyBoardInfoRow(dailyBoardVo);
		// 일상 게시판 테이블에 파일이름을 , 로 구분하여 값을 전달하기 위한 메소드
		setBoardVoFileName(dailyBoardVo);
		// 일상 게시판 파일 정보 테이블에 파일 정보를 전달하기 위한 메소드
		updateImg(dailyBoardVo);

		return dailyBoardVo.getNo();
	}

	@Override
	public void delDailyBoard(int no) throws Exception {
		int delCnt = 0;
		delCnt += dailyBoardDao.deleteDailyBoardInfoRow(dailyBoardVO);
	}

	@Override
	public void updateImg(DailyBoardVO dailyBoardVo) throws Exception {
//		log.info("*".repeat(90));
		List<String> fileNames = new ArrayList<String>();
		Map<String, Object> fileMap = new HashMap();

		//HTML 파싱 및 조작을 위한 Jsoup 라이브러리 의존주입
		Document doc = Jsoup.parse(dailyBoardVo.getContent());
		DailyBoardFilesInfoVo dailyBoardFilesInfoVo = new DailyBoardFilesInfoVo();
		dailyBoardFilesInfoVo.setBoardNo(dailyBoardVo.getNo());

		List<DailyBoardFilesInfoVo> oldDataList = dailyBoardDao.selectFileInfo(dailyBoardFilesInfoVo);
		log.info("** boardNo: {}", dailyBoardFilesInfoVo.getBoardNo());
		if(oldDataList != null) {
			delCnt += dailyBoardDao.deleteFileInfo(dailyBoardFilesInfoVo);
		}
		//img 태그 선택
		Elements imgs = doc.select("img");
		for(Element img : imgs) {
			String src = img.attr("src");
			src = src.substring(src.lastIndexOf('/')+1);
			fileNames.add(src);
			dailyBoardFilesInfoVo.setFileName(src);
			insCnt += dailyBoardDao.insertFileInfo(dailyBoardFilesInfoVo);
		}
//		log.info("** 파일 등록: {}건 (누적)등록", insCnt);
//		log.info("*".repeat(90));
	}

	@Override
	public void thumbnail(int no, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//사진을 내려받기 위한 출력 스트림 통로 객체 생성
		OutputStream os = response.getOutputStream();

		//다운로드할 파일위치의 파일경로 생성
		String imgPath = uploadPath + "/dailyboard/" + no;
//		log.info("** 이미지 경로: {}", imgPath);

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

		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + URLEncoder.encode(imgName, "UTF-8"));

		//사진을 입력하기 위한 입력스트림 통로 객체 생성
		FileInputStream in = new FileInputStream(img);

		//이미지 파일을 담아 출력할 바이트 배열 생성
		byte[] buffer = new byte[10 * 1024];

		while (true) {
			int count = in.read(buffer);

			if(count == -1) {
				break;
			}
			os.write(buffer, 0, count);
		}

		os.close();
	}
}
