package cn.sdp.Service4all.data.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.regionserver.StoreFile.BloomType;
import org.apache.hadoop.hbase.util.Bytes;


public class HbaseClient {
	private Configuration conf = null;
	private HConnection connection = null;
	private final String COL_NAME = "Value";
	
	public HbaseClient()
	{
		conf = HBaseConfiguration.create();
		try {
			connection = HConnectionManager.createConnection(conf);
//			}
		} catch (ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int createTable(String tableName, List<String> cfnames) {
		try {
		    HBaseAdmin admin = new HBaseAdmin(conf);
			if (admin.tableExists(tableName)) {
			    System.out.println("表已经存在！");
			    return -3;    
			}
			else
			{
				HTableDescriptor tableDesc = new HTableDescriptor(tableName);
				for (String cf : cfnames)
					tableDesc.addFamily(new HColumnDescriptor(cf));
				admin.createTable(tableDesc);
				System.out.println("表创建成功！");
		    }
		} catch (IOException e) {
	        e.printStackTrace();
	        return -3;
	    }
		return 0;
	}  
	
	public int insertObject(String tableName, String cfName, List<TestObject> objs) {
		HTableInterface table = null;
		try {
			table = connection.getTable(tableName);
			table.setAutoFlush(false);
			for (TestObject obj : objs)
				table = putObject(cfName, table, obj);
			table.flushCommits();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return -3;
	    } finally {
	    	try {
				table.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return 0;
	}
	
	private HTableInterface putObject(String cfName, HTableInterface table, TestObject obj) throws IOException {
		Put put1 = new Put(Bytes.toBytes(obj.getKey()));
		put1.add(Bytes.toBytes(cfName),
		        Bytes.toBytes("Value"),
				Bytes.toBytes(obj.getValue()));
		table.put(put1);
		return table;
	}
	
	public TestObject getObject(String tbName, String cfName, String key)
	{
		TestObject ret = null;
		HTableInterface table = null;
		try {
			table = connection.getTable(tbName);
			Get g = new Get(Bytes.toBytes(key));
			Result r = table.get(g);

			TestObject obj = new TestObject();
			obj.setValue(Bytes.toString(r.getValue(Bytes.toBytes(cfName),Bytes.toBytes(COL_NAME))));
			obj.setKey(Bytes.toString(r.getRow()));
			
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ret;
	    } finally {
	    	try {
				table.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return ret;
	}
	
	public List<TestObject> scanObject(String tbName, String cfName, String key1, String key2)
	{
		List<TestObject> ret = new ArrayList<TestObject>();
		ResultScanner results = null;
		HTableInterface table = null;
		try {
			table = connection.getTable(tbName);
			Scan scan = new Scan();
			scan.setCaching(10000);
			scan.setStartRow(Bytes.toBytes(key1)).setStopRow(Bytes.toBytes(key2));
			scan.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(COL_NAME));
			results = table.getScanner(scan);
			for (Result r : results)
			{
				TestObject obj = new TestObject();
				obj.setValue(Bytes.toString(r.getValue(Bytes.toBytes(cfName),Bytes.toBytes(COL_NAME))));
				obj.setKey(Bytes.toString(r.getRow()));
				ret.add(obj);
			}
	    } catch (IOException e) {
	        e.printStackTrace();
	        return ret;
	    } finally {
	    	try {
				table.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return ret;
	}
	
	public static void main(String[] args) {
		
	}
}
