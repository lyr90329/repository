package cn.org.act.sdp.bpmnRepository.portal.servlet.editor;

public class Result {
	private StringBuffer _xml;

	public Result() {
		this._xml = new StringBuffer("<root>");
	}

	public void setItem(String item, String value) {
		_xml.append("<" + item + ">" + value + "</" + item + ">");
	}

	public String asXML() {
		_xml.append("</root>");
		String xml = _xml.toString();
		return xml;
	}
}
