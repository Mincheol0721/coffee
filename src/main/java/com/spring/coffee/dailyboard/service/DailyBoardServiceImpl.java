package com.spring.coffee.dailyboard.service;

import java.io.BufferedOutputStream;
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
import org.springframework.web.multipart.MultipartFile;

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

	private final static String viewPath = "/WEB-INF/views/dailyBoard/";

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
	public int insertDailyBoard(MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		// request의 파라미터들을 꺼내와 Enumeration 배열에 저장 후 배열 반환
		Enumeration<String> enu = request.getParameterNames();
		// Enumeration 배열에서 key와 value를 가져와 저장할 Map 생성
		Map<String, String> map = new HashMap<>();
		// insert를 위해 dailyBoardVo 객체 초기화
		dailyBoardVO = new DailyBoardVO();
		// Enumeration 배열의 요소들이 존재하는 동안 반복
		while(enu.hasMoreElements()) {
			// Enumeration 배열의 요소(request에서 받아온 form요소 하위 요소들의 name속성)
			String key = (String)enu.nextElement();
			// form요소 하위 요소들의 해당 name속성의 값
			String value = request.getParameter(key);
//			log.info("** name 속성: {} / 속성 값: {}", key, value);
			// 각 name속성 및 해당 속성 값을 가져와 map에 저장
			map.put(key, value);
		}

		// 파일명을 저장하기 위한 문자열
		String fileName = "";
		// MultipartFile 배열에서 파일 이름만 따로 추출하여 저장할 ArrayList 생성
		List<String> fileNames = new ArrayList<>();
		// MultipartFile 배열만큼 반복
		for (MultipartFile file : files) {
			// 업로드 할 파일들의 이름을 뽑아 저장
			String orginFileNm = file.getOriginalFilename();

			// 파일 확장자 저장
			String fileName_ext = orginFileNm.substring(orginFileNm.lastIndexOf(".")).toLowerCase();
//			log.info("** 파일 확장자: {}", fileName_ext);
			// 파일명을 yyyyMMddHHmmss+랜덤문자열.확장자로 지정
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String today = formatter.format(new java.util.Date());
			fileName = today + UUID.randomUUID().toString() + fileName_ext;
//			log.info("** 랜덤 파일명: {}", fileName);

			// 저장한 파일들의 이름을 ArrayList에 다시 저장
			fileNames.add(fileName);
		}
		// map에서 각 key에 해당하는 value들을 가져와 daliyBoardVo객체에 저장
		dailyBoardVO.setTitle(map.get("title"));
		dailyBoardVO.setContent(map.get("content"));
		dailyBoardVO.setId(map.get("id"));
		// 파일들의 이름이 저장된 ArrayList를 String화 하여 dailyBoardVo객체에 저장
		dailyBoardVO.setFileName(fileNames.toString().replace("[", "").replace("]", ""));
//		log.info("** VO title: {} / VO content: {} / VO id: {}", dailyBoardVO.getTitle(), dailyBoardVO.getContent(), dailyBoardVO.getId());
//		log.info("** VO fileName: {}", dailyBoardVO.getFileName());

		// DB에 VO 객체 저장
		insCnt = dailyBoardDao.insertDailyBoardInfoRow(dailyBoardVO);
		// 저장 후 DB에서 VO에 저장한 ID로 작성된 글의 가장 최신 글번호 가져오기
		int boardNo = dailyBoardDao.selectDailyBoardCountInfo(dailyBoardVO.getId());

		// 글번호를 폴더로 하는 경로 문자열로 저장
		String noDir = uploadPath + "/" + boardNo;
		// 글번호를 폴더로 하는 경로가 없을 경우 폴더 생성
		File boardDir = new File(noDir);
		boardDir.mkdirs();

		int i = 0;
		// 파일 개수만큼 반복
		for (MultipartFile file : files) {
			// 랜덤으로 생성한 파일명 가져오기
			fileName = fileNames.get(i++);
			// 글번호 경로 + 랜덤파일명
			String filePath = noDir + "/" + fileName;
			// filePath를 경로로 하는 파일 생성
			File dest = new File(filePath);

			// dest 파일로 해당 파일 이관
			file.transferTo(dest);
		}

		return boardNo;
	}
		/*
	 * public int insertDailyBoard(DailyBoardVO dailyBoardVo, MultipartFile[] files,
	 * HttpServletRequest request, HttpServletResponse response) throws Exception {
	 * String absPath = uploadPath + "/dailyBoard/"; Map map = new HashMap(); int no
	 * = 0; String filePath = absPath + "/temp+" + dailyBoardVo.getId() + "/";
	 *
	 * for (MultipartFile file : files) { log.info("**files : {}",
	 * file.getOriginalFilename()); String filename = file.getOriginalFilename();
	 * String filename_ext =
	 * filename.substring(filename.indexOf('.')).toLowerCase(); // 파일명을
	 * yyyyMMddHHmmss+랜덤문자열.확장자로 지정 SimpleDateFormat formatter = new
	 * SimpleDateFormat("yyyyMMddHHmmss"); String today = formatter.format(new
	 * java.util.Date()); filename = today + UUID.randomUUID().toString() +
	 * filename_ext; String rlFileNm = filePath + filename; }
	 *
	 * setBoardVoFileName(dailyBoardVo);
	 *
	 * dailyBoardDao.insertDailyBoardInfoRow(dailyBoardVo);
	 *
	 * // log.info("newBoardNo: " + no); no =
	 * dailyBoardDao.selectDailyBoardCountInfo(dailyBoardVo.getId());
	 * dailyBoardVO.setNo(no); dailyBoardVo =
	 * dailyBoardDao.selectDailyBoardInfoRow(dailyBoardVO);
	 *
	 * updateImg(dailyBoardVo);
	 *
	 * return no; }
	 */

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
	    // 파일 정보를 수집
	    String sFileInfo = "";
	    String sFileName = request.getHeader("file-name");
	    if (sFileName == null || sFileName.isEmpty()) {
	        // 파일 이름이 없는 경우, 아무 것도 하지 않음
	        return;
	    }
	    log.info("** 받아온 파일 명: {}", sFileName);
	    // .을 포함한 파일 확장자 (.png, .jpg, s...)
	    String filename_ext = sFileName.substring(sFileName.lastIndexOf(".")).toLowerCase();

	    // 파일 기본 경로
	    String dftFilePath = uploadPath + "/dailyBoard/temp";
	    // 파일 기본 경로 + 상세 경로
	    String filePath = dftFilePath + no + "/";
	    File fileDir = new File(filePath);
	    // 파일 기본 경로가 존재하지 않을 경우 기본경로 생성
	    if (!fileDir.exists()) {
	    	fileDir.mkdirs();
	    }

	    // 새로운 파일명 생성
	    String fileName = "";
	    // 파일명을 yyyyMMddHHmmss+랜덤문자열.확장자로 지정
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	    String today = formatter.format(new java.util.Date());
	    fileName = today + UUID.randomUUID().toString() + filename_ext;
	    String rlFileNm = filePath + fileName;

	    // 서버에 파일 쓰기 =====================================================================
	    try (InputStream is = request.getInputStream();
	        OutputStream os = new BufferedOutputStream(new FileOutputStream(rlFileNm))) {
//	    	byte[] b = new byte[Integer.parseInt(request.getHeader("file-size"))];
	    	byte[] b = new byte[10 * 1024];
	    	log.info("** request에서 받아온 파일 사이즈: {}", request.getHeader("file-size"));
	    	log.info("** is.available: {}", is.available());
	    	log.info("** 생성된 byte배열 크기: {}", b.length);
	        int numRead;
	        long totalBytesRead = 0;
	        while ((numRead = is.read(b)) != -1) {
	            os.write(b, 0, numRead);
	            totalBytesRead += numRead;
	        }

	        log.info("** 파일 저장 완료: {}byte 읽어들임", totalBytesRead);
	    } catch (Exception e) {
			// TODO: handle exception
	    	log.error("** Error writing file to server: {}", e.getMessage(), e);
	    }
	    // 서버에 파일 쓰기 =====================================================================

	    // 정보 출력
	    sFileInfo += "&bNewLine=true";
	    sFileInfo += "&sFileName=" + sFileName;
	    sFileInfo += "&sFileURL=/dailyboard/" + no + "/" + fileName;

	    log.info("** sFileInfo : {}", sFileInfo);

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
		String imgPath = uploadPath + "/dailyBoard/" + no;
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
