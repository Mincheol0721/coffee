package com.spring.coffee.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.coffee.member.dao.MemberDaoImpl;
import com.spring.coffee.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("board")
public class BoardUtil {

	@Value("${upload.directory}")
	private String uploadPath;

	@Autowired
	private MemberDaoImpl memberDao;

	@RequestMapping("download")
	public void download(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberVO memberVo = new MemberVO();
		memberVo.setId(id);
		memberVo = memberDao.selectMemberInfoRow(memberVo);
		String fileName = memberVo.getFileName();

		//사진을 내려받기 위한 출력스트림 통로 객체 생성
		OutputStream out = response.getOutputStream();
		//사진이 저장된 경로를 찾아가기 위해 경로 저장
		String imgPath = uploadPath + "/member/";
		//다운로드할 파일의 경로 저장
		String filePath = imgPath + "/" + id + "/" + fileName;

		//이미지 파일을 조작할 수 있는 파일객체 생성
		File image = new File(filePath);

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

}
