package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class SearchLog {
	private Object content; //로그 내용 //Board객체 등등
	private int pageNum;
	private int items;
	private String text;
	private String type;
	
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getItems() {
		return items;
	}
	public void setItems(int items) {
		this.items = items;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SearchLog [pageNum=" + pageNum + ", items=" + items + ", text=" + text + ", type=" + type + "]";
	}
	
	
}
