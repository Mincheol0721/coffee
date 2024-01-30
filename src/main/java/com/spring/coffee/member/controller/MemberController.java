package com.spring.coffee.member.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.coffee.mapper.MemberMapper;
import com.spring.coffee.member.vo.GoogleInfo;
import com.spring.coffee.member.vo.KakaoInfo;
import com.spring.coffee.member.vo.MemberVO;
import com.spring.coffee.member.vo.NaverInfo;
import com.spring.coffee.member.vo.OAuthToken;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("coffee/member")
public class MemberController {
	
	private final static String viewPath = "/WEB-INF/views/member/";
	
	@Value("${upload.directory}")
	private String uploadPath;
	
	@Autowired(required = false)
	MemberVO member;
	
	@Autowired
	MemberMapper memberMapper;
	
	@RequestMapping("loginForm")
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("center", viewPath + "loginForm.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("login")
	public ModelAndView login(@ModelAttribute("member") MemberVO vo, RedirectAttributes rAttr, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		MemberVO member = new MemberVO();
		
		member = memberMapper.login(vo);
//		System.out.println("member: " + member.getId());
		
		//id와 비밀번호로 조회 해온 정보가 존재 시 로그인 처리
		if(member != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			session.setAttribute("isLogOn", true);
			session.setAttribute("isOwnMember", true);
			
			mav.setViewName("redirect:/coffee/main");
		//id 비밀번호로 조회한 정보가 존재하지 않을경우
		} else {
			rAttr.addFlashAttribute("result", "loginFailed");
			mav.setViewName("redirect:/coffee/member/loginForm");
		}
		
		return mav;
	}
	
	@RequestMapping(value="kakaoLogin", produces = "application/json;charset=UTF-8", method=RequestMethod.GET)
	public @ResponseBody ModelAndView kakaoLogin(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("############# " + code);
		//POST방식으로 key=value 데이터를 카카오 서버쪽으로 Token요청
		RestTemplate rt = new RestTemplate();
		
		//HTTP POST 메소드의 헤더영역에 Content-type 1개의 값 저장
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//HTTP POST 메소드 바디영역에 4개의 요청할 값 저장
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "6f333d17c0737d247630d8be3aeead82");
		params.add("redirect_uri", "http://localhost:8090/coffee/member/kakaoLogin");
		params.add("code", code);
		
		//카카오 서버에 token을 요청할 데이터를 담을 HttpEntity객체 생성
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<MultiValueMap<String,String>>(params, headers);
		
		//카카오 서버에 HTTP POST 메소드로 token요청
		ResponseEntity<String> responseEntity = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, kakaoTokenRequest, String.class);
		
		//JSONObject의 값들을 파싱하여 vo클래스의 변수에 매핑하여 저장시킬 라이브러리로 ObjectMapper 사용
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = objectMapper.readValue(responseEntity.getBody(), OAuthToken.class);
		
		System.out.println("kakaoLogin access_token: " + oauthToken.getAccess_token());
		//-------------------------------------------------------------------------------------------------------------------------------------------------------
		//받은 엑세스 토큰을 카카오 서버로 전달하여 카카오 DB에 저장된 회원정보 받기
		RestTemplate rt2 = new RestTemplate();
		
		//HTTP POST 메소드의 헤더영역에 Content-type 1개의 값 저장
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		//카카오 서버에 token을 요청할 데이터를 담을 HttpEntity객체 생성
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
		
		//카카오 서버에 HTTP POST 메소드로 token값을 문자열로 받아 저장
		ResponseEntity<String> responseEntity2 = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);
		
		//응답데이터 확인
		System.out.println("responseEntity2: " + responseEntity2.getBody());
		
		//JSONObject의 값들을 파싱하여 vo클래스의 변수에 매핑하여 저장시킬 라이브러리로 ObjectMapper 사용
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoInfo kakaoInfo = objectMapper2.readValue(responseEntity2.getBody(), KakaoInfo.class);
		
		//id값 얻기
		String socialId = kakaoInfo.getId();
		//세션얻기
		HttpSession session = request.getSession();
		
		//아이디로 회원 조회
		MemberVO member = memberMapper.getMemberBySocialId(socialId);
		
		if(member != null) {
			System.out.println("kakao 아이디 존재");
			Map<String, Object> map = new HashMap<String, Object>();
			memberMapper.login(member);
			
			session.setAttribute("isLogOn", true);
			session.setAttribute("member", member);
			session.setAttribute("isKakao", true);
			
			mav.setViewName("redirect:/coffee/main");
		} else {
			System.out.println("kakao 아이디 미존재");
			Map<String, String> map = new HashMap<String, String>();
			map.put("socialId", socialId);
			map.put("name", kakaoInfo.getKakao_account().getName());
			map.put("email", kakaoInfo.getKakao_account().getEmail());
			map.put("fileName", kakaoInfo.getProperties().getProfile_image());
			System.out.println("profileImage: " + kakaoInfo.getProperties().getProfile_image());
			
			mav.addObject("kakaoReg", true);
			mav.addObject("map", map);
			mav.addObject("center", viewPath + "regForm.jsp");
			mav.setViewName("main");
		}
		
		return mav;
	}
	
	@RequestMapping("oauth2google")
	public @ResponseBody ModelAndView googleLogin(String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("######################## " + code);
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		
		//POST방식으로 key=value 데이터를 구글 서버쪽으로 Token요청
		RestTemplate rt = new RestTemplate();
		//HTTP POST 메소드 바디영역에 5개의 요청할 값 저장
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", "1870458841-rqbmjhnqhn43rk2ecv1gppvhvhj2i4tp.apps.googleusercontent.com");
		params.add("client_secret", "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");
		params.add("redirect_uri", "http://localhost:8090/coffee/member/oauth2google");
		params.add("grant_type", "authorization_code");
		//구글 서버에 HTTP POST 메소드로 token요청
		ResponseEntity<String> responseEntity = rt.postForEntity("https://oauth2.googleapis.com/token", params, String.class);
		System.out.println(responseEntity.getBody());
		
		//JSONObject의 값들을 파싱하여 vo클래스의 변수에 매핑하여 저장시킬 라이브러리로 ObjectMapper 사용
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = objectMapper.readValue(responseEntity.getBody(), OAuthToken.class);
		
		System.out.println("google access_token: " + oauthToken.getAccess_token());
		//-------------------------------------------------------------------------------------------------------------------------------------------------------
		//받은 엑세스 토큰을 구글 서버로 전달하여 구글 DB에 저장된 회원정보 받기
		RestTemplate rt2 = new RestTemplate();
		
		//HTTP POST 메소드의 헤더영역에 Content-type 1개의 값 저장
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		
		//구글 서버에 token을 요청할 데이터를 담을 HttpEntity객체 생성
		HttpEntity<MultiValueMap<String, String>> googleProfileRequest = new HttpEntity<>(headers);
		
		//구글 서버에 HTTP POST 메소드로 token값을 문자열로 받아 저장
		ResponseEntity<String> responseEntity2 = rt.exchange("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET, googleProfileRequest, String.class);
		//응답데이터 확인
		System.out.println("responseEntity2: " + responseEntity2.getBody());
		
		//JSONObject 값들을 파싱하여 VO클래스의 변수에 저장
		ObjectMapper objectMapper2 = new ObjectMapper();
		GoogleInfo googleInfo = objectMapper2.readValue(responseEntity2.getBody(), GoogleInfo.class);
		
		//id값 얻기
		String socialId = googleInfo.getId();
		MemberVO member = memberMapper.getMemberBySocialId(socialId);
		
		if(member != null) {
			System.out.println("Google 아이디 존재");
			Map<String, Object> map = new HashMap<String, Object>();
			memberMapper.login(member);
			
			session.setAttribute("isLogOn", true);
			session.setAttribute("member", member);
			session.setAttribute("isGoogle", true);
			
			mav.setViewName("redirect:/coffee/main");
		} else {
			System.out.println("Google 아이디 미존재");
			Map<String, String> map = new HashMap<String, String>();
			map.put("socialId", socialId);
			map.put("name", googleInfo.getName());
			map.put("email", googleInfo.getEmail());
			map.put("fileName", googleInfo.getPicture());
			System.out.println("email: " + googleInfo.getEmail());
			System.out.println("profileImage: " + googleInfo.getPicture());
			
			mav.addObject("googleReg", true);
			mav.addObject("map", map);
			mav.addObject("center", viewPath + "regForm.jsp");
			mav.setViewName("main");
		}
		
		return mav;
	}
	
	@RequestMapping("naverLogin")
	public @ResponseBody ModelAndView naverLogin(String code,String state, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		
		String clientId = "LqbceHaqklXQzbcgejR8";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "P7HnauOPPY";//애플리케이션 클라이언트 시크릿값";
	    
	    //POST방식으로 key=value 데이터를 네이버 서버쪽으로 Token요청
		RestTemplate rt = new RestTemplate();
		
		//HTTP POST 메소드 바디영역에 5개의 요청할 값 저장
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("state", state);
		params.add("client_id", clientId);
		params.add("client_secret", clientSecret);
		params.add("redirect_uri", "http://localhost:8090/coffee/member/naverLogin");
		params.add("grant_type", "authorization_code");
		//네이버 서버에 HTTP POST 메소드로 token요청
		ResponseEntity<String> responseEntity = rt.postForEntity("https://nid.naver.com/oauth2.0/token", params, String.class);
		System.out.println(responseEntity.getBody());
		
		//JSONObject의 값들을 파싱하여 vo클래스의 변수에 매핑하여 저장시킬 라이브러리로 ObjectMapper 사용
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = objectMapper.readValue(responseEntity.getBody(), OAuthToken.class);
		
		session.setAttribute("client_id", clientId);
		session.setAttribute("client_secret", clientSecret);
		session.setAttribute("access_token", oauthToken.getAccess_token());
		
		System.out.println("naver access_token: " + oauthToken.getAccess_token());
		//-------------------------------------------------------------------------------------------------------------------------------------------------------
		//받은 엑세스 토큰을 네이버 서버로 전달하여 네이버 DB에 저장된 회원정보 받기
		RestTemplate rt2 = new RestTemplate();
		
		//HTTP POST 메소드의 헤더영역에 Content-type 1개의 값 저장
		HttpHeaders headers = new HttpHeaders();
		headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
		headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		
		//네이버 서버에 token을 요청할 데이터를 담을 HttpEntity객체 생성
		HttpEntity<MultiValueMap<String, String>> naverProfileRequest = new HttpEntity<>(headers);
		
		//네이버 서버에 HTTP POST 메소드로 token값을 문자열로 받아 저장
		ResponseEntity<String> responseEntity2 = rt.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.POST, naverProfileRequest, String.class);
		//응답데이터 확인
		System.out.println("responseEntity2: " + responseEntity2.getBody());
		
		//JSONObject 값들을 파싱하여 VO클래스의 변수에 저장
		ObjectMapper objectMapper2 = new ObjectMapper();
		objectMapper2.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
		NaverInfo naverInfo = objectMapper2.readValue(responseEntity2.getBody(), NaverInfo.class);
		
		//회원정보 조회를 위해 id얻기
		String socialId = naverInfo.getResponse().getId();
		System.out.println("name: " + naverInfo.getResponse().getName());
		MemberVO member = memberMapper.getMemberBySocialId(socialId);
		
		if(member != null) {
			System.out.println("Naver 아이디 존재");
			Map<String, Object> map = new HashMap<String, Object>();
			memberMapper.login(member);
			
			session.setAttribute("isLogOn", true);
			session.setAttribute("member", member);
			session.setAttribute("isNaver", true);
			
			mav.setViewName("redirect:/coffee/main");
		} else {
			System.out.println("Naver 아이디 미존재");
			Map<String, String> map = new HashMap<String, String>();
			map.put("socialId", socialId);
			map.put("name", naverInfo.getResponse().getName());
			map.put("email", naverInfo.getResponse().getEmail());
			map.put("fileName", naverInfo.getResponse().getProfile_image());
			
			mav.addObject("naverReg", true);
			mav.addObject("map", map);
			mav.addObject("center", viewPath + "regForm.jsp");
			mav.setViewName("main");
		}
		
		return mav;
	}
	
	@RequestMapping("regForm")
	public ModelAndView regForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("center", viewPath + "regForm.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping(value="addMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView addMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception {
		System.out.println("addMember탑승");
		ModelAndView mav = new ModelAndView();
		MemberVO member = new MemberVO();
		
		//파일 명 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파일 경로 저장할 변수 설정
		String filePath = uploadPath + "/member/";
		System.out.println("filePath: " + filePath);
		//파일명 저장
		String fileName = file.getOriginalFilename();
		
		//입력한 값들의 정보를 저장할 Map 생성
		Map map = new HashMap();
		
		//request에서 값들을 꺼내와 저장한 후 배열자체를 반환할 Enumeration객체 생성
		Enumeration enu = request.getParameterNames();
		
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}
		map.put("fileName", fileName);
		
		//파일 경로를 /member/아이디명 으로 저장해주기 위해 id 얻기
		String id = (String)map.get("id");
		//폴더 명을 id로 하는 폴더를 생성하기 전 루트폴더가 존재하지 않으면 생성
		File rootPath = new File(filePath);
		if(!rootPath.exists()) {
			rootPath.mkdir();
		}
		//파일 업로드할 폴더를 생성하기 위해 경로 설정
		File memDir = new File(filePath + id); 
		System.out.println("memDir폴더 경로: " + memDir.getPath());
		//폴더가 존재하지 않을 시 폴더 생성
		if(!memDir.exists()) {
			System.out.println("memDir 폴더 생성을 위해 if문 탑승");
			if(memDir.mkdir()) {
				System.out.println("memDir 폴더 생성 성공");
			} else {
				System.out.println("memDir 폴더 생성 실패");
			}
		}
		
		String imgPath = filePath + id + "/" + fileName;
		File dest = new File(imgPath);
		//파일을 해당 폴더로 이동
		Files.copy(file.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		
		memberMapper.addMember(map);
		
		mav.setViewName("redirect:/coffee/member/loginForm");
		return mav;
	}
	
	@RequestMapping(value = "addKakaoMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView addKakaoMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("addKakaoMember탑승");
		MemberVO member = new MemberVO();
		HttpSession session = request.getSession();
		
		//업로드할 파일명 또는 입력한 데이터가 한글일 경우 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파일경로를 저장할 변수 설정
		String filePath = uploadPath + "/member/";
		File rootPath = new File(filePath);
		//위 파일 루트 경로가 존재하지 않을 경우 생성
		if(!rootPath.exists()) {
			rootPath.mkdir();
		}
		
		//입력한 값들의 정보를 저장할 Map 생성
		Map map = new HashMap();
		
		//request에서 값을 꺼내와 배열에 저장 후 배열 자체를 반환할 Enumeration객체 생성
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}
		
		//id 값 저장
		String id = map.get("id").toString();
		//이미지 URL에서 이미지를 다운받아 저장하기 위해 URL정보 저장
		String imgURL = map.get("imgURL").toString();
		
		//이미지 URL 생성
		URL url = new URL(imgURL);
		//저장할 폴더 경로 생성
		String saveFolderPath = filePath + id;
		//입력한 url에서 이미지를 내려받을 입력스트림 통로 객체 생성
		InputStream in = url.openStream();
		//저장할 폴더 경로 생성
		Path saveFolder = Path.of(saveFolderPath);
		//폴더가 없다면 생성
		if(!Files.exists(saveFolder)) {
			Files.createDirectories(saveFolder);
		}
		//이미지 파일 이름 추출
		String fileName = imgURL.substring(imgURL.lastIndexOf('/') + 1);
		System.out.println("추출한 파일명: " + fileName);
		map.put("fileName", fileName);
		
		//저장할 이미지 파일 경로 생성
		Path savePath = saveFolder.resolve(fileName);
		//이미지를 파일로 저장
		Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
		
		System.out.println("이미지 다운로드 및 저장 완료: " + savePath);
		
		//mapper호출하여 db에 값 insert
		memberMapper.addSocialMember(map);
		
		//insert후 MemberVO객체에 값들을 저장하여 바로 로그인 요청
		member.setId(map.get("id").toString());
		if (map.get("password") != null) {
			member.setPassword(map.get("password").toString());
		}
		member.setName(map.get("name").toString());
		member.setSsn(map.get("ssn").toString());
		member.setNickname(map.get("nickname").toString());
		member.setEmail(map.get("email").toString());
		member.setMobile(map.get("mobile").toString());
		member.setZipcode(map.get("zipcode").toString());
		member.setRoadAddr(map.get("roadAddr").toString());
		member.setDetailAddr(map.get("detailAddr").toString());
		member.setJibunAddr(map.get("jibunAddr").toString());
		member.setFileName(fileName);
		
		//로그인 처리
		memberMapper.login(member);
		session.setAttribute("isLogOn", true);
		session.setAttribute("member", member);
		session.setAttribute("isKakao", true);
		
		mav.setViewName("redirect:/coffee/main");
		
		return mav;
	}
	
	@RequestMapping(value = "addGoogleMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView addGoogleMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("addGoogleMember탑승");
		MemberVO member = new MemberVO();
		HttpSession session = request.getSession();
		
		//업로드할 파일명 또는 입력한 데이터가 한글일 경우 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파일경로를 저장할 변수 설정
		String filePath = uploadPath + "/member/";
		File rootPath = new File(filePath);
		//위 파일 루트 경로가 존재하지 않을 경우 생성
		if(!rootPath.exists()) {
			rootPath.mkdir();
		}
		
		//입력한 값들의 정보를 저장할 Map 생성
		Map map = new HashMap();
		
		//request에서 값을 꺼내와 배열에 저장 후 배열 자체를 반환할 Enumeration객체 생성
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}
		
		//id 값 저장
		String id = map.get("id").toString();
		//이미지 URL에서 이미지를 다운받아 저장하기 위해 URL정보 저장
		String imgURL = map.get("imgURL").toString();
		
		//이미지 URL 생성
		URL url = new URL(imgURL);
		//저장할 폴더 경로 생성
		String saveFolderPath = filePath + id;
		//입력한 url에서 이미지를 내려받을 입력스트림 통로 객체 생성
		InputStream in = url.openStream();
		//저장할 폴더 경로 생성
		Path saveFolder = Path.of(saveFolderPath);
		//폴더가 없다면 생성
		if(!Files.exists(saveFolder)) {
			Files.createDirectories(saveFolder);
		}
		//이미지 파일 이름 추출
		String fileName = imgURL.substring(imgURL.lastIndexOf('=') + 1);
		System.out.println("추출한 파일명: " + fileName);
		map.put("fileName", fileName);
		
		//저장할 이미지 파일 경로 생성
		Path savePath = saveFolder.resolve(fileName);
		//이미지를 파일로 저장
		Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
		
		System.out.println("이미지 다운로드 및 저장 완료: " + savePath);
		
		//mapper호출하여 db에 값 insert
		memberMapper.addSocialMember(map);
		
		//insert후 MemberVO객체에 값들을 저장하여 바로 로그인 요청
		member.setId(map.get("id").toString());
		if (map.get("password") != null) {
			member.setPassword(map.get("password").toString());
		}
		member.setName(map.get("name").toString());
		member.setSsn(map.get("ssn").toString());
		member.setNickname(map.get("nickname").toString());
		member.setEmail(map.get("email").toString());
		member.setMobile(map.get("mobile").toString());
		member.setZipcode(map.get("zipcode").toString());
		member.setRoadAddr(map.get("roadAddr").toString());
		member.setDetailAddr(map.get("detailAddr").toString());
		member.setJibunAddr(map.get("jibunAddr").toString());
		member.setFileName(fileName);
		
		//로그인 처리
		memberMapper.login(member);
		session.setAttribute("isLogOn", true);
		session.setAttribute("member", member);
		session.setAttribute("isGoogle", true);
		
		mav.setViewName("redirect:/coffee/main");
		
		return mav;
	}

	@RequestMapping(value = "addNaverMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView addNaverMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("addNaverMember탑승");
		MemberVO member = new MemberVO();
		HttpSession session = request.getSession();
		
		//업로드할 파일명 또는 입력한 데이터가 한글일 경우 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파일경로를 저장할 변수 설정
		String filePath = uploadPath + "/member/";
		File rootPath = new File(filePath);
		//위 파일 루트 경로가 존재하지 않을 경우 생성
		if(!rootPath.exists()) {
			rootPath.mkdir();
		}
		
		//입력한 값들의 정보를 저장할 Map 생성
		Map map = new HashMap();
		
		//request에서 값을 꺼내와 배열에 저장 후 배열 자체를 반환할 Enumeration객체 생성
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}
		//id 값 저장
		String id = map.get("id").toString();
		//이미지 URL에서 이미지를 다운받아 저장하기 위해 URL정보 저장
		String imgURL = map.get("fileName").toString();
		
		//이미지 URL 생성
		URL url = new URL(imgURL);
		//저장할 폴더 경로 생성
		String saveFolderPath = filePath + id;
		//입력한 url에서 이미지를 내려받을 입력스트림 통로 객체 생성
		InputStream in = url.openStream();
		//저장할 폴더 경로 생성
		Path saveFolder = Path.of(saveFolderPath);
		//폴더가 없다면 생성
		if(!Files.exists(saveFolder)) {
			Files.createDirectories(saveFolder);
		}
		//이미지 파일 이름 추출
		String fileName = imgURL.substring(imgURL.lastIndexOf('/') + 1);
		System.out.println("추출한 파일명: " + fileName);
		map.put("fileName", fileName);
		
		//저장할 이미지 파일 경로 생성
		Path savePath = saveFolder.resolve(fileName);
		//이미지를 파일로 저장
		Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
		
		System.out.println("이미지 다운로드 및 저장 완료: " + savePath);
		
		//mapper호출하여 db에 값 insert
		memberMapper.addSocialMember(map);
		
		//insert후 MemberVO객체에 값들을 저장하여 바로 로그인 요청
		member.setId(map.get("id").toString());
		if (map.get("password") != null) {
			member.setPassword(map.get("password").toString());
		}
		member.setName(map.get("name").toString());
		member.setSsn(map.get("ssn").toString());
		member.setNickname(map.get("nickname").toString());
		member.setEmail(map.get("email").toString());
		member.setMobile(map.get("mobile").toString());
		member.setZipcode(map.get("zipcode").toString());
		member.setRoadAddr(map.get("roadAddr").toString());
		member.setDetailAddr(map.get("detailAddr").toString());
		member.setJibunAddr(map.get("jibunAddr").toString());
		member.setFileName(fileName);
		
		//로그인 처리
		memberMapper.login(member);
		session.setAttribute("isLogOn", true);
		session.setAttribute("member", member);
		session.setAttribute("isNaver", true);
		
		mav.setViewName("redirect:/coffee/main");
		
		return mav;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.invalidate();
		mav.setViewName("redirect:/coffee/main");
		mav.addObject("logout", "logout");
		
		return mav;
	}
	
	@RequestMapping("kakaoLogout")
	public ModelAndView kakaoLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		mav.setViewName("redirect:/coffee/main");
		
		return mav;
	}
	
	@RequestMapping("googleLogout")
	public ModelAndView googleLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
		
		return new ModelAndView("redirect:/coffee/main");
	}
	
	@RequestMapping("naverLogout")
	public ModelAndView naverLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		String clientID = (String)session.getAttribute("client_id");
		String clientSecret = (String)session.getAttribute("client_secret");
		String accessToken = (String)session.getAttribute("access_token");
		System.out.println("logout: " + clientID + " / " + clientSecret + " / " + accessToken);
		
		//네이버 로그아웃 요청
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "delete");
		params.add("client_id", clientID);
		params.add("client_secret", clientSecret);
		params.add("access_token", accessToken);
		
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<String> responseEntity = new RestTemplate().postForEntity("https://nid.naver.com/oauth2.0/token", requestEntity, String.class);
		
		System.out.println("Naver Logout Response: " + requestEntity.getBody());
		
		session.invalidate();
		mav.setViewName("redirect:/coffee/main");
		
		return mav;
	}
	
	@RequestMapping("memberDetail")
	public ModelAndView detailMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO)session.getAttribute("member");
		String id = vo.getId();
		
		member = memberMapper.getMemberById(id);
		
		mav.addObject("vo", member);
		mav.addObject("center", viewPath + "memberDetail.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("modForm")
	public ModelAndView modForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		mav.addObject("center", viewPath + "modMemberForm.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("modPasswordForm")
	public ModelAndView modPasswordForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("center", viewPath + "modPasswordForm.jsp");
		mav.setViewName("main");
		
		return mav;
	}
	
	@RequestMapping("modPassword")
	public ModelAndView modPassword(@RequestParam("curPassword") String curPassword, @RequestParam("password") String password, @RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView();
		member = memberMapper.getMemberById(id);
		
		if(member.getPassword().equals(curPassword)) {
			System.out.println("현재 비밀번호 일치하여 수정실행!");
			memberMapper.modPassword(id, password);
			
			mav.setViewName("redirect:/coffee/member/memberDetail");
			
			return mav;
		} else {
			//비밀번호 변경 클릭 시 자꾸 message 출력되는 현상 fix필요
			System.out.println("현재 비밀번호 틀림!");
			
			mav.addObject("message", "failed");
			mav.addObject("center", viewPath + "modPasswordForm.jsp");
			mav.setViewName("main");
			
			return mav;
		}
		
	}

	@RequestMapping(value="modMember", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView modMember(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request, RedirectAttributes rAttr) throws Exception {
		System.out.println("modMember탑승");
		ModelAndView mav = new ModelAndView();
		MemberVO member = new MemberVO();
		HttpSession session = request.getSession();
		//파일 명 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파일 경로 저장할 변수 설정
		String filePath = uploadPath + "/member/";
		
		//입력한 값들의 정보를 저장할 Map 생성
		Map map = new HashMap();
		
		//request에서 값들을 꺼내와 저장한 후 배열자체를 반환할 Enumeration객체 생성
		Enumeration enu = request.getParameterNames();
		
		while (enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			String value = request.getParameter(key);
			
			map.put(key, value);
		}

		String fileName = (String)map.get("fileName");
		String id = (String)map.get("id");
		
		//파일명 받아오기
		System.out.println("update fileName: " + fileName);
		
		//기존 이미지 폴더 경로 얻기
		String imgPath = filePath + id;
		System.out.println("modMember imgPath: " + imgPath);

		if(!file.isEmpty()) {
			System.out.println("if문 탑승! 수정한 이미지 파일 존재!");
			fileName = file.getOriginalFilename();
			map.put("fileName", fileName);
			//기존 이미지 삭제
			File existingFile = new File(imgPath);
			//기존 이미지 폴더 및 파일 삭제
			if(existingFile.isDirectory()) {
				File[] files = existingFile.listFiles();
				if(files != null) {
					for(File image : files) {
						image.delete();
					}
				}
				existingFile.delete();
			}
			
			//새 이미지 업로드
			//폴더 생성을 위해 경로 설정
			File memDir = new File(imgPath);
			//폴더 생성
			memDir.mkdir();
			
			filePath = imgPath + "/" + fileName;
			File dest = new File(filePath);
			System.out.println("filePath: " + filePath);
			// 파일을 해당 폴더로 복사
			Files.copy(file.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		
		memberMapper.modMember(map);
		
		mav.setViewName("redirect:/coffee/member/memberDetail");
		return mav;
	}
	
	@RequestMapping("download")
	public void download(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("id: " + id);
		HttpSession session = request.getSession();
		MemberVO member = memberMapper.getMemberById(id);
		String fileName = member.getFileName();
		
		//사진을 내려받기 위한 출력스트림 통로 객체 생성
		OutputStream out = response.getOutputStream();
		//사진이 저장된 경로를 찾아가기 위해 경로 저장
		String imgPath = uploadPath + "/member/";
		//다운로드할 파일의 경로 저장
		String filePath = imgPath + "/" + id + "/" + fileName;
		
		//이미지 파일을 조작할 수 있는 파일객체 생성
		File image = new File(filePath);
		
		System.out.println("파일 경로: " + filePath);
		System.out.println("파일: " + image);
		
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8"));
		
		//사진을 입력하기 위한 입력스트림 통로 객체 생성
		FileInputStream in = new FileInputStream(image);
		
		//이미지 파일을 담아 출력할 바이트 배열 생성
		byte[] buffer = new byte[1024 * 8];
		
		while (true) {
			int count = in.read(buffer);
			
			if(count == -1) {
				break;
			}
			out.write(buffer, 0, count);
		}
		
		in.close();
		out.close();
	}
	
	@RequestMapping("nickValidate")
	public String nickValidate(@RequestParam("nickname") String nickname, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("닉네임 유효성 검사: " + nickname);
		
		String validatedNickname = memberMapper.nickValidate(nickname);
		System.out.println("닉네임: " + validatedNickname);
		
		if(validatedNickname == null) {
			return "사용가능";
		} else {
			return "사용불가";
		}
		
	}
	
	@RequestMapping("idValidate")
	public String idValidate(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("아이디 유효성 검사: " + id);
		
		String validatedId = memberMapper.idValidate(id);
		System.out.println("닉네임: " + validatedId);
		
		if(validatedId == null) {
			return "사용가능";
		} else {
			return "사용불가";
		}
		
	}
	
	
	
}
