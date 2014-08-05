package cn.sdp.Service4all.data.cassandra;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.cql3.CFName;


import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;

public class SysCassandraDAO {
	private static final SysCassandraDAO instance = new SysCassandraDAO();
	private final Cluster cluster;
	private final String ksName = "service4all";
	private final String userCf = "users";
	
	private SysCassandraDAO() {
		CassandraHostConfigurator config = new CassandraHostConfigurator(ConfigReader.getProperty("server-address"));
		config.setAutoDiscoverHosts(true);
		Map<String, String> auth = new HashMap<String, String>();
		auth.put("username", "cassandra");
		auth.put("password", "cassandra");
		cluster = HFactory.getOrCreateCluster("Test Cluster", config, auth);

	}
	
	public static SysCassandraDAO getInstance()
	{
		return instance;
	}
	
	public int createCassandraUser(String userName, String passwd)
	{
		int code = createKeyspace(userName, passwd);
		if (code != 0)
			return code;
//		Session session = cqlCluster.connect();
//		String sql = "create user "+userName+" with password '"+passwd+"' nosuperuser";
//		session.execute(sql);
//		session.close();
		return 0;
	}
	
	public int createKeyspace(String userName, String passwd)
	{
		Keyspace keyspace = HFactory.createKeyspace(ksName, cluster);
		
		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(
				keyspace, userCf, new StringSerializer(), new StringSerializer());
		try{
			ColumnFamilyResult<String, String> res = template.queryColumns(userName);
		    if (res.hasResults())
		    {
				return -2;
		    }
		}		
		catch (HectorException e)
		{
			e.printStackTrace();
			return -3;
		}
		
		KeyspaceDefinition ksdf = HFactory.createKeyspaceDefinition("sp_"+userName);
		cluster.addKeyspace(ksdf);
		insertUser(userName, passwd);
		return 0;
	}

	private void insertUser(String userName, String passwd) {
		Keyspace keyspace = HFactory.createKeyspace(ksName, cluster);
		Mutator<String> mutator = HFactory.createMutator(keyspace, new StringSerializer());
		mutator.addInsertion(userName, userCf, HFactory.createStringColumn("Passwd", passwd));
		mutator.execute();
	}
	
	public CassandraConfigFile getCassandraConfig(String userName)
	{
		CassandraConfigFile config = new CassandraConfigFile();
		config.setKeyspace("sp_"+userName);
		config.setUserName(userName);
		Keyspace keyspace = HFactory.createKeyspace(ksName, cluster);
		ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(
				keyspace, userCf, new StringSerializer(), new StringSerializer());
		try{
			ColumnFamilyResult<String, String> res = template.queryColumns(userName);
		    if (res.hasResults())
		    {
				config.setPassword(res.getString("Passwd")); 
		    }
		}
		catch (HectorException e)
		{
			e.printStackTrace();
			return null;
		}
		return config;
	}


}
