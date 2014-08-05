package cn.org.act.sdp.bpmnRepository.entity;

import java.io.Serializable;

public class SerializeTest implements Serializable {
	public String name;

	public SerializeTest(String n) {
		this.name = n;
	}
}
