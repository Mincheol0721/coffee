<%@page import="jakarta.servlet.http.HttpSession"%>
<%@page import="com.spring.coffee.dailyboard.service.DailyBoardService"%>
<%@page import="com.spring.coffee.mapper.DailyBoardMapper"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	//File Information
	String fileInfo = "";
	//Original File Name
	String fileName = request.getHeader("file-name");
	//File Extension
	String fileNameExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	//PrintWriter
	PrintWriter pw = response.getWriter();
	// int no = new DailyBoardService().getNewBoardNo();
	// System.out.println("jsp no: " + no);
	
	//Img validation variables
	String[] extArr = { "jpg", "png", "bmp", "gif" };
	
	//Extension Validate
	int nCnt = 0;
	for (int i = 0; i < extArr.length; i++) {
		if (fileNameExt.equals(extArr[i])) {
			nCnt++;
		}
	}
	
	//if not img
	if (nCnt == 0) {
		pw.print("NOTALLOW_" + fileName);
		pw.flush();
		pw.close();
	} else {
		//Directory setting and Upload
		String defaultPath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("defaultPath: " + defaultPath);
		String filePath = defaultPath + "se2" + File.separator + "img" + File.separator;
		System.out.println("filePath: " + filePath);
		
		File file = new File(filePath);
		
		if (!file.exists()) {
			System.out.println("mkdirs");
			file.mkdirs();
		}
		
		String autoFileName = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String today = sdf.format(new java.util.Date());
		autoFileName = today + UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
		System.out.println("autoFileName: " + autoFileName);
		
		String rlFileName = filePath + autoFileName;
		System.out.println("rlFileName: " + rlFileName);
	
	
		//Input File To Server
		InputStream in = request.getInputStream();
		OutputStream os = new FileOutputStream(rlFileName);
		int num;
		byte[] b = new byte[Integer.parseInt(request.getHeader("file-size"))];
		while ((num = in.read(b, 0, b.length)) != -1) {
			os.write(b, 0, num);
		}
		if (in != null) {
			in.close();
		}
		os.flush();
		os.close();
		
		//IMG
		//Print Info
		fileInfo += "&bNewLine=true";
		//title = original file name processing
		fileInfo += "&sFileName=" + fileName;
		fileInfo += "&sFileURL=/se2/img" + autoFileName;
		System.out.println("fileInfo: " + fileInfo);
	
		pw.print(fileInfo);
		pw.flush();
		pw.close();
	
	}
%>