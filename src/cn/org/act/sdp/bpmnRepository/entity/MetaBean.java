package cn.org.act.sdp.bpmnRepository.entity;

import java.util.Date;

/**
 * 元数据标记
 * 
 * @author dell
 * 
 */
public class MetaBean {
	private int id;

	private String description;
	private Date creDate;
	private Date modDate;
	private String author;
	private int titleId;
	private TitleBean title;
	private BpmnBean bpmn;
	
	private DomainBean domain;

	private int domainId;

	private int version;

	private int versions;

	private int latest;
	
	private int derive;
	
	public MetaBean(){
		this.creDate = new Date();
		this.modDate = this.creDate;
		this.version = 1;
	}

	public int getDerive() {
		return derive;
	}

	public void setDerive(int derive) {
		this.derive = derive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreDate() {
		return creDate;
	}

	public void setCreDate(Date creDate) {
		this.creDate = creDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public DomainBean getDomain() {
		return domain;
	}

	public void setDomain(DomainBean domain) {
		this.domain = domain;
	}

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public TitleBean getTitle() {
		return title;
	}

	public void setTitle(TitleBean title) {
		this.title = title;
	}

	public int getVersions() {
		return versions;
	}

	public void setVersions(int versions) {
		this.versions = versions;
	}

	public int getLatest() {
		return latest;
	}

	public void setLatest(int latest) {
		this.latest = latest;
	}

	public BpmnBean getBpmn() {
		return bpmn;
	}

	public void setBpmn(BpmnBean bpmn) {
		this.bpmn = bpmn;
	}

}
