package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Board {
	
	private int BID; //�� ���̵�(����)
	private String id; //�ۼ� ���� ���̵� #admin => ������
	private String mbti; //�ۼ� ���� mbti
	private String title; //�� ����
	private LocalDateTime regdate; //�������
	private LocalDateTime upddate; //��������
	private String image; //�̹��� ���
	private String content;
	private int goohit; // ���ƿ� ��
	private int hit; //��ȸ��
	private String pname; // ������ �̸�
	private String paddress; //���θ� �ּ�
	private String latclick; //����
	private String lngclick; //�浵
	
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
