package cn.org.act.sdp.bpmnRepository.entity;

import java.util.Date;

public class AnnotationBean {
	private int id;
	private String msg;
	private int bpmnId;
	private int elementId = 0;
	private int userId;
	private String author;
	private Date time = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getBpmnId() {
		return bpmnId;
	}

	public void setBpmnId(int bpmnId) {
		this.bpmnId = bpmnId;
	}

	public int getElementId() {
		return elementId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
