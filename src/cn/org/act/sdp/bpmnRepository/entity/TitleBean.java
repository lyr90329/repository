package cn.org.act.sdp.bpmnRepository.entity;

import java.util.HashMap;

public class TitleBean {
	private int id;
	private String title;
	private int versions;
	private int latest;
	private HashMap<String, MetaBean> metas;
	private int domainId;
	private DomainBean domain;
	private String description;

	public TitleBean() {
		this.versions = 0;
		this.latest = 0;
		this.domainId = 0;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
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

	public HashMap<String, MetaBean> getMetas() {
		return metas;
	}

	public void setMetas(HashMap<String, MetaBean> metas) {
		this.metas = metas;
	}

	public int getDomainId() {
		return domainId;
	}

	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}

	public DomainBean getDomain() {
		return domain;
	}

	public void setDomain(DomainBean domain) {
		this.domain = domain;
	}

	public MetaBean getLatestVersionMeta() {
		return this.metas.get("v0");
	}

	public MetaBean getMetaByVersion(int v) {
		return this.metas.get("v" + v);
	}
}
