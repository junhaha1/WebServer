package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
	private int MID; //메세지 id
	private String UID; //보낸 사람
	private String RID; //받은 사람
	private String TITLE; //제목
	private String CONTENT; //내용
	private LocalDateTime SENDDATE; //보낸 날짜 
	private int RDCHECK; //읽음 여부
	public int getMID() {
		return MID;
	}
	public void setMID(int mID) {
		MID = mID;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getRID() {
		return RID;
	}
	public void setRID(String rID) {
		RID = rID;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public LocalDateTime getSENDDATE() {
		return SENDDATE;
	}
	public void setSENDDATE(LocalDateTime sENDDATE) {
		SENDDATE = sENDDATE;
	}
	public int getRDCHECK() {
		return RDCHECK;
	}
	public void setRDCHECK(int rDCHECK) {
		RDCHECK = rDCHECK;
	}
	
	
	
	
}
