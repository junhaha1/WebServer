package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
	private int MID; //�޼��� id
	private String UID; //���� ���
	private String RID; //���� ���
	private String TITLE; //����
	private String CONTENT; //����
	private LocalDateTime SENDDATE; //���� ��¥ 
	private int RDCHECK; //���� ����
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
