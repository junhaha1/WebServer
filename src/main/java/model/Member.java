package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Member {
	private String id;
	private String password;
	private String email;
	private String name;
	private LocalDateTime registerDateTime;
	private String mbti;
	private LocalDateTime lastDateTime; //마지막 활동 시간
	private int activity; //현재 활동중 
	
	public String getId() {
		return id;
	}

	public LocalDateTime getLastDateTime() {
		return lastDateTime;
	}

	public void setLastDateTime(LocalDateTime lastDateTime) {
		this.lastDateTime = lastDateTime;
	}

	public int getActivity() {
		return activity;
	}

	public void setActivity(int activity) {
		this.activity = activity;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getRegisterDateTime() {
		return registerDateTime;
	}

	public void setRegisterDateTime(LocalDateTime registerDateTime) {
		this.registerDateTime = registerDateTime;
	}

	public String getMbti() {
		return mbti;
	}

	public void setMbti(String mbti) {
		this.mbti = mbti;
	}

	//member에 저장된 비밀번호와 넘겨받은 비밀번호가 일치하는지 파악
	public boolean matchPassword(String password) {
		return Objects.equals(this.password, password);
	}
	
	public String toString() {
		return id + ", " + email + ", " + password + ", " + name + ", " + registerDateTime;
	}
}
