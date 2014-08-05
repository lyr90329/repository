package db.entity;

public class J2WSBean {

	private String file;
	private String user;
	private boolean deployed;
		
	public J2WSBean(String file, String user, boolean deployed) {
		super();
		this.file = file;
		this.user = user;
		this.deployed = deployed;
	}
	
	public J2WSBean() {
		super();
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public boolean isDeployed() {
		return deployed;
	}
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}
	
	
}
