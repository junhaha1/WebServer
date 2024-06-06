package model;

import java.time.LocalDateTime;

public class Coment {
	private int CNUM;
	private int BID;
	private String Btitle; //글 제목만 읽어올 때 사용하기
	private String id;
	private String content;
	private LocalDateTime regdate;
	private String pid;
	
	
	public int getCNUM() {
		return CNUM;
	}
	public void setCNUM(int cNUM) {
		CNUM = cNUM;
	}
	public int getBID() {
		return BID;
	}
	public void setBID(int bID) {
		BID = bID;
	}
	
	public String getBtitle() {
		return Btitle;
	}
	public void setBtitle(String btitle) {
		Btitle = btitle;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getRegdate() {
		return regdate;
	}
	public void setRegdate(LocalDateTime regdate) {
		this.regdate = regdate;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
}
