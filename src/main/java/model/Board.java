package model;

import java.time.LocalDateTime;

public class Board {
	private int BID; //�� ���̵�(����)
	private String id; //�ۼ� ���� ���̵� #admin => ������
	private String title; //�� ����
	private LocalDateTime regdate; //�������
	private LocalDateTime upddate; //��������
	private String image; //�̹��� ���
	private String content;
	private int goohit; // ���ƿ� ��
	private int hit; //��ȸ��
	private String firstadd; //ù��° ��
	private String secondadd; //�ι�° ��
	
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
