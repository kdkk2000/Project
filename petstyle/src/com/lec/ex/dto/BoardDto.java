package com.lec.ex.dto;

import java.sql.Timestamp;

public class BoardDto {
	private int boardId;
	private String userId;
	private String userName; // join해서 출력
	private String boardTitle;
	private String boardContent;
	private String boardFileName;
	private Timestamp boardDate;
	private int boardHit;
	private int boardGroup;
	private int boardStep;
	private int boardIndent;
	private String boardIp;
	
	public BoardDto() {}
	
	public BoardDto(int boardId, String userId, String userName, String boardTitle, String boardContent,
			String boardFileName, Timestamp boardDate, int boardHit, int boardGroup, int boardStep, int boardIndent,
			String boardIp) {
		this.boardId = boardId;
		this.userId = userId;
		this.userName = userName;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardFileName = boardFileName;
		this.boardDate = boardDate;
		this.boardHit = boardHit;
		this.boardGroup = boardGroup;
		this.boardStep = boardStep;
		this.boardIndent = boardIndent;
		this.boardIp = boardIp;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardFileName() {
		return boardFileName;
	}
	public void setBoardFileName(String boardFileName) {
		this.boardFileName = boardFileName;
	}
	public Timestamp getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(Timestamp boardDate) {
		this.boardDate = boardDate;
	}
	public int getBoardHit() {
		return boardHit;
	}
	public void setBoardHit(int boardHit) {
		this.boardHit = boardHit;
	}
	public int getBoardGroup() {
		return boardGroup;
	}
	public void setBoardGroup(int boardGroup) {
		this.boardGroup = boardGroup;
	}
	public int getBoardStep() {
		return boardStep;
	}
	public void setBoardStep(int boardStep) {
		this.boardStep = boardStep;
	}
	public int getBoardIndent() {
		return boardIndent;
	}
	public void setBoardIndent(int boardIndent) {
		this.boardIndent = boardIndent;
	}
	public String getBoardIp() {
		return boardIp;
	}
	public void setBoardIp(String boardIp) {
		this.boardIp = boardIp;
	}
	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", userId=" + userId + ", userName=" + userName + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardFileName=" + boardFileName + ", boardDate="
				+ boardDate + ", boardHit=" + boardHit + ", boardGroup=" + boardGroup + ", boardStep=" + boardStep
				+ ", boardIndent=" + boardIndent + ", boardIp=" + boardIp + "]";
	}

	
}
