package cn.org.act.sdp.bpmnRepository.pool;

import java.sql.Connection;

import org.smartlib.pool.core.ConnectionPoolException;
import org.smartlib.pool.core.SmartPoolFactory;

/**
 * This class implements a connection pool for database.
 * 
 * @author <a href=��mailto:linwei@act.buaa.edu.cn��>Lin Wei</a>
 * @version $Revision: 1.1 $ $Date: 2010-04-26 06:56:15 $
 */
public class ConnectionPool {

	/**
	 * the single instance
	 */
	private static ConnectionPool pool = null;

	/**
	 * the smart pool instance
	 */
	private SmartPoolFactory smp;

	private static final Object classLock = ConnectionPool.class;

	/**
	 * Initialize the connection pool factory
	 * 
	 * @param filename
	 *            the configuration file
	 * @exception Exception
	 *                , if configuration file is invalid
	 */
	public synchronized void initialize(String filename) {
		if (smp == null) {
			try {
				smp = new SmartPoolFactory(filename);

			} catch (Exception e) {
			}
			return;
		}
		SmartPoolFactory.shutDown();
		try {
			smp = new SmartPoolFactory(filename);
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The private constructor
	 */
	private ConnectionPool() {

	}

	/**
	 * Get the single instance of the factory
	 * 
	 * @return the single instance of the factory
	 */
	public synchronized static ConnectionPool singleInstance() {

		if (pool == null) {
			pool = new ConnectionPool();

		}

		return pool;
	}

	/**
	 * Get a connection to the database
	 * 
	 * @return a connection to the database
	 * @throws Exception
	 *             if any error occurs
	 */
	public synchronized Connection getConnection() throws Exception {
		return SmartPoolFactory.getConnection();
	}

	/**
	 * Close this connection pool
	 */
	public synchronized void close() {
		SmartPoolFactory.shutDown();
	}

}
