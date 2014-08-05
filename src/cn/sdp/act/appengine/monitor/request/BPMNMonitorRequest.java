package cn.sdp.act.appengine.monitor.request;

import java.util.Map;

import org.enqu.xml.utils.XmlObject;

public abstract class BPMNMonitorRequest extends XmlObject {

	protected void addAttribute(String name, String value,
			Map<String, String> attrs) {
		if (name == null || value == null) {
			return;
		}
		attrs.put(name, value);
	}

}
