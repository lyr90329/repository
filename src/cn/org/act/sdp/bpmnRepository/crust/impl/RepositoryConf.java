package cn.org.act.sdp.bpmnRepository.crust.impl;

import cn.org.act.sdp.bpmnRepository.config.Config;
import cn.org.act.sdp.bpmnRepository.config.Constants;
import cn.org.act.sdp.bpmnRepository.config.SqlStatementManager;
import cn.org.act.sdp.bpmnRepository.pool.ConnectionPool;

/**
 * This class manages the initialization of the connection pool and sql-manager.
 * 
 * @author <a href=��mailto:xuxt@act.buaa.edu.cn��>Xu Xiaotian</a>
 * @version $Revision: 1.2 $ $Date: 2010-05-13 03:19:12 $
 */
public class RepositoryConf {
	/**
	 * Automatically generated constructor: RepositoryConf
	 */
	private RepositoryConf() {

	}

	/**
	 * initialize the connection pool and sql-manager for the following
	 * operation towards the database
	 */
	public static void managerInit() {
		// System.out.println("***************");
		if (flag1) {
			try {
				// System.out.println("#############");
				SqlStatementManager manager = SqlStatementManager
						.singleInstance();
				// System.out.println(flag1);
				manager.initialize(Constants.LOCAL_SQL_MANAGE_PATH);
				// System.out.println("^^^^^^^^^^^^");
				flag1 = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void poolInit() {
		if (flag2) {
			try {
				pool = ConnectionPool.singleInstance();
				pool.initialize(Constants.LOCAL_POOL_CONFIG_PATH);
				flag2 = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * close the connection
	 */
	public static void close() {
		if (!flag2) {
			pool.close();
			flag2 = true;
		}
	}

	private static boolean flag1 = true;

	private static boolean flag2 = true;

	private static ConnectionPool pool = null;
}
