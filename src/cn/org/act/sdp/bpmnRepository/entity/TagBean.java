package cn.org.act.sdp.bpmnRepository.entity;

import org.json.JSONString;

public class TagBean implements JSONString {
	private int id;
	private String tag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String toJSONString() {
		return "{\"id\":" + id + "," + "\"tag\":\"" + tag + "\"}";
	}

}
