package model;

import java.time.LocalDateTime;

public class Board {
	private int BID; //글 아이디(순번)
	private String id; //작성 유저 아이디 #admin => 관리자
	private String title; //글 제목
	private LocalDateTime regdate; //등록일자
	private LocalDateTime upddate; //수정일자
	private String image; //이미지 경로
	private String content;
	private int goohit; // 좋아요 수
	private int hit; //조회수
	private String firstadd; //첫번째 시
	private String secondadd; //두번째 구
	
	public int getBID() {
		return BID;
	}
	public void setBID(int bID) {
		BID = bID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getRegdate() {
		return regdate;
	}
	public void setRegdate(LocalDateTime regdate) {
		this.regdate = regdate;
	}
	public LocalDateTime getUpddate() {
		return upddate;
	}
	public void setUpddate(LocalDateTime upddate) {
		this.upddate = upddate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getGoohit() {
		return goohit;
	}
	public void setGoohit(int goohit) {
		this.goohit = goohit;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getFirstadd() {
		return firstadd;
	}
	public void setFirstadd(String firstadd) {
		this.firstadd = firstadd;
	}
	public String getSecondadd() {
		return secondadd;
	}
	public void setSecondadd(String secondadd) {
		this.secondadd = secondadd;
	}
	
	
}
