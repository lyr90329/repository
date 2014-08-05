package db.entity;

import java.util.ArrayList;
import java.util.List;

import constant.MenuItems;

public class User {
	private String userName;
	private String password;
	private String nationality;
	private String company;
	private String job;
	private String email;
	private String concern;
	private int conf;

	public User(String userName, String password, String nationality,
			String company, String job, String email, String concern, int conf) {

		this.userName = userName;
		this.password = password;
		this.nationality = nationality;
		this.company = company;
		this.job = job;
		this.email = email;
		this.concern = concern;
		this.conf = conf;
	}

	public User() {

	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getConcern() {
		return concern;
	}

	public void setConcern(String concern) {
		this.concern = concern;
	}

	public int getConf() {
		return conf;
	}

	public void setConf(int conf) {
		this.conf = conf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
