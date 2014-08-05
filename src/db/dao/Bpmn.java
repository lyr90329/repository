package db.dao;

public class Bpmn {

	private long bpmnid;

	private String bpmnfile;

	private String diagramfile;

	private String diagramflex;

	private String addition;

	private String userName;

	private String time;

	private String xml;

	private long state;

	private int times;

	public void settimes(int i) {
		times = i;
	}

	public int gettimes() {
		return times;
	}

	public long getBpmnid() {
		return bpmnid;
	}

	public void setBpmnid(long bpmnid) {
		this.bpmnid = bpmnid;
	}

	public String getBpmnfile() {
		return bpmnfile;
	}

	public void setBpmnfile(String bpmnfile) {
		this.bpmnfile = bpmnfile;
	}

	public String getDiagramfile() {
		return diagramfile;
	}

	public void setDiagramfile(String diagramfile) {
		this.diagramfile = diagramfile;
	}

	public String getAddition() {
		return addition;
	}

	public void setAddition(String addition) {
		this.addition = addition;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public String getDiagramflex() {
		return diagramflex;
	}

	public void setDiagramflex(String diagramflex) {
		this.diagramflex = diagramflex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
