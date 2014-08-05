package cn.sdp.Service4all.data.test;

import cn.sdp.Service4all.data.cassandra.CassandraConfigFile;
import cn.sdp.Service4all.data.cassandra.SysCassandraDAO;

public class DataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SysCassandraDAO dao = SysCassandraDAO.getInstance();
		String user = "sdp1234";
		String passwd = "123456";
		System.out.println(dao.createCassandraUser(user, passwd));
		CassandraConfigFile config = dao.getCassandraConfig("sdp1234");
		if (config != null)
			System.out.println(config.getKeyspace()+"-"+config.getPassword()+"-"+
				config.getUserName()+"-"+config.getServer());
	}

}
