package cn.sdp.Service4all.data.cassandra;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

public class CassandraKeyspaceFactory {
	private static Keyspace keyspace = null;
	private static Cluster cluster = null;
	
	static {
//		System.out.println("conn");

		CassandraHostConfigurator config = new CassandraHostConfigurator(ConfigReader.getProperty("server-address"));
		config.setAutoDiscoverHosts(true);
		cluster = HFactory.getOrCreateCluster("Test Cluster", config);
		createKeyspace();
	}
	
	public static Cluster getCluster()
	{
		return cluster;
	}
		
	public static Keyspace getKeyspace()
	{
		if (keyspace == null)
		{
			if (cluster == null)
			{
				rebuildCluster();
			}
			if (cluster != null)
				createKeyspace();
		}
		return keyspace;
	}

	private static void rebuildCluster() {
		// TODO Auto-generated method stub
		if (cluster == null)
		{
			CassandraHostConfigurator config = new CassandraHostConfigurator(ConfigReader.getProperty("server-address"));
			config.setAutoDiscoverHosts(true);
			cluster = HFactory.getOrCreateCluster("TestCluster", config);	
		}
		
	}

	private static void createKeyspace()
	{
		ConfigurableConsistencyLevel consistency = new ConfigurableConsistencyLevel();
		consistency.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
		consistency.setDefaultWriteConsistencyLevel(HConsistencyLevel.ONE);
		keyspace = HFactory.createKeyspace(ConfigReader.getProperty("keyspace-name"), cluster, consistency, FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE);
	}
		
	public static void close() {
		// TODO Auto-generated method stub
		cluster.getConnectionManager().shutdown();
	}
}
