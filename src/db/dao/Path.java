package db.dao;

public class Path {

	private long id;

	private String pathname;

	private long type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

}
