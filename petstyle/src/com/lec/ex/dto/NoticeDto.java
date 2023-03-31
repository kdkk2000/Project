package com.lec.ex.dto;

import java.sql.Timestamp;

public class NoticeDto {
	private int noticeId;
	private String adminId;
	private String adminName; // join해서 출력
	private String noticeTitle;
	private String noticeContent;
	private String noticeFileName;
	private Timestamp noticeDate;
	private int noticeHit;
	private int noticeGroup;
	private int noticeStep;
	private int noticeIndent;
	private String noticeIp;
	
	public NoticeDto() {}
	
	public NoticeDto(int noticeId, String adminId, String adminName, String noticeTitle, String noticeContent,
			String noticeFileName, Timestamp noticeDate, int noticeHit, int noticeGroup, int noticeStep, int noticeIndent,
			String noticeIp) {
		this.noticeId = noticeId;
		this.adminId = adminId;
		this.adminName = adminName;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeFileName = noticeFileName;
		this.noticeDate = noticeDate;
		this.noticeHit = noticeHit;
		this.noticeGroup = noticeGroup;
		this.noticeStep = noticeStep;
		this.noticeIndent = noticeIndent;
		this.noticeIp = noticeIp;
	}
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getadminId() {
		return adminId;
	}
	public void setadminId(String adminId) {
		this.adminId = adminId;
	}
	public String getadminName() {
		return adminName;
	}
	public void setadminName(String adminName) {
		this.adminName = adminName;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getNoticeFileName() {
		return noticeFileName;
	}
	public void setNoticeFileName(String noticeFileName) {
		this.noticeFileName = noticeFileName;
	}
	public Timestamp getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Timestamp noticeDate) {
		this.noticeDate = noticeDate;
	}
	public int getNoticeHit() {
		return noticeHit;
	}
	public void setNoticeHit(int noticeHit) {
		this.noticeHit = noticeHit;
	}
	public int getNoticeGroup() {
		return noticeGroup;
	}
	public void setNoticeGroup(int noticeGroup) {
		this.noticeGroup = noticeGroup;
	}
	public int getNoticeStep() {
		return noticeStep;
	}
	public void setNoticeStep(int noticeStep) {
		this.noticeStep = noticeStep;
	}
	public int getNoticeIndent() {
		return noticeIndent;
	}
	public void setNoticeIndent(int noticeIndent) {
		this.noticeIndent = noticeIndent;
	}
	public String getNoticeIp() {
		return noticeIp;
	}
	public void setNoticeIp(String noticeIp) {
		this.noticeIp = noticeIp;
	}
	@Override
	public String toString() {
		return "NoticeDto [noticeId=" + noticeId + ", adminId=" + adminId + ", adminName=" + adminName + ", noticeTitle="
				+ noticeTitle + ", noticeContent=" + noticeContent + ", noticeFileName=" + noticeFileName + ", noticeDate="
				+ noticeDate + ", noticeHit=" + noticeHit + ", noticeGroup=" + noticeGroup + ", noticeStep=" + noticeStep
				+ ", noticeIndent=" + noticeIndent + ", noticeIp=" + noticeIp + "]";
	}

	
}
