package com.spring.coffee.dailyboard.vo;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Alias("dailyBoardInfo")
public class DailyBoardVO {
	private int no;
	private String title;
	private String content;
	private String writeDate;
	private String nickname;
	private int readCount;
	private String[] fileNames;

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String[] getFileNames() {
		return fileNames;
	}
	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
