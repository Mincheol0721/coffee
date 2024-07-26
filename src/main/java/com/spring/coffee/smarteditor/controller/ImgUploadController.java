package com.spring.coffee.smarteditor.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.coffee.smarteditor.vo.ImgUploadVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController("seImgUpload")
@Slf4j
public class ImgUploadController {

	@Value("${upload.directory}")
	private String uploadPath;
	/*
	@RequestMapping("/singleImgUpload")
	public String singleImgUpload(@RequestParam(value = "vo", required = false) ImgUploadVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String callback = vo.getCallback();
		String callback_fn = vo.getCallback_fn();
		String file_result = "";

		if(vo.getFiledata() != null && vo.getFiledata().getOriginalFilename() != null) {
			String originalName = vo.getFiledata().getOriginalFilename();
			String ext = originalName.substring(originalName.lastIndexOf(".") + 1);
			String dftPath = uploadPath + "/dailyboard";
			String filePath = dftPath + "/temp/";
			File file = new File(filePath);
			log.info("path: " + filePath);

			if(!file.exists()) {
				file.mkdirs();
			}

			String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()) + UUID.randomUUID().toString() + "." + ext;

			vo.getFiledata().transferTo(new File(filePath + fileName));
			file_result += "&bNewLine=true&sFileName=" + originalName + "&sFileURL=/" + filePath + fileName;
			log.info("file_result: " + file_result);

		} else {
			file_result += "&errstr=error";
		}

		return "redirect:" + callback + "?callback_func" + callback_fn + file_result;
	}
	*/
	@RequestMapping("/multipleImgUpload")
	public void multipleImgUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("controller in");
		request.setCharacterEncoding("UTF-8");
		List<String> fileList = new ArrayList<String>();

		//File Information
		//파일정보         
		String sFileInfo = "";
		//파일명을 받는다 - 일반 원본파일명         
		String filename = request.getHeader("file-name");
		//파일 확장자         
		String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		//파일 기본경로
		String dftFilePath = uploadPath + "/dailyBoard";
		//파일 기본경로 _ 상세경로         
		String filePath = dftFilePath + "/temp/";
		File file = new File(filePath);

		if(!file.exists()) {
			file.mkdirs();
		}

		String realFileNm = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String today= formatter.format(new java.util.Date());
		realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		String rlFileNm = filePath + realFileNm;

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
		sFileInfo += "&sFileName="+ filename;
		sFileInfo += "&sFileURL=/dailyboard/temp/" + realFileNm;

		System.out.println("sfileInfo: " + sFileInfo);
		log.info("realFileName: " + realFileNm);

		PrintWriter print = response.getWriter();

		print.print(sFileInfo);
		print.flush();
		print.close();
	}

}
