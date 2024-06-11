package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Board {
	
	private int BID; //글 아이디(순번)
	private String id; //작성 유저 아이디 #admin => 관리자
	private String mbti; //작성 유저 mbti
	private String title; //글 제목
	private LocalDateTime regdate; //등록일자
	private LocalDateTime upddate; //수정일자
	private String image; //이미지 경로
	private String content;
	private int goohit; // 좋아요 수
	private int hit; //조회수
	private String pname; // 음식점 이름
	private String paddress; //도로명 주소
	private String latclick; //위도
	private String lngclick; //경도
	
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
	public String getMbti() {
		return mbti;
	}
	public void setMbti(String mbti) {
		this.mbti = mbti;
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
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPaddress() {
		return paddress;
	}
	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}
	public String getLatclick() {
		return latclick;
	}
	public void setLatclick(String latclick) {
		this.latclick = latclick;
	}
	public String getLngclick() {
		return lngclick;
	}
	public void setLngclick(String lngclick) {
		this.lngclick = lngclick;
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
	
	@Override
	public String toString() {
		return "Board [BID=" + BID + ", id=" + id + ", title=" + title + ", goohit=" + goohit + ", hit=" + hit + "]";
	}
	
}
