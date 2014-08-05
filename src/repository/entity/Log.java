package repository.entity;

import java.util.Date;

public class Log {

	private Date date;
	private String content;

	public Log() {
	}

	public Log(Date date, String content) {
		this.date = date;
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String toString() {
		return "[Log] " + this.date + " :" + this.content;
	}

}
