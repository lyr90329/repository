package cn.sdp.Service4all.data.cassandra;

public class CassandraConfigFile {
	private final String server = "112.124.46.148:9160";
	private String keyspace;
	private String userName;
	private String password;
	
	public String getKeyspace() {
		return keyspace;
	}
	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServer() {
		return server;
	}
	
}
