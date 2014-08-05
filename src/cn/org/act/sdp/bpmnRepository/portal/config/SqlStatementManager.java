package cn.org.act.sdp.bpmnRepository.portal.config;

import org.jconfig.Configuration;
import org.jconfig.ConfigurationLoader;

/**
 * This class implements a sql statement manager.
 * 
 * @author <a href=��mailto:linwei@act.buaa.edu.cn��>Lin Wei</a>
 * @version $Revision: 1.1 $ $Date: 2010-04-26 06:56:23 $
 */
public class SqlStatementManager {

	/**
	 * The single instance of the manager
	 */
	private static SqlStatementManager manager = null;
	private static String configFilePath = "conf/sql-statements.xml";

	/**
	 * The configuration of sql statements
	 */
	private Configuration config;

	/**
	 * Get the single instance of the manager
	 * 
	 * @return the single instance of the manager
	 */
	public static SqlStatementManager singleInstance() {

		if (null == manager) {
			manager = new SqlStatementManager();
			try {
				manager
						.initialize(cn.org.act.sdp.bpmnRepository.portal.config.Config
								.getPath(configFilePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return manager;
	}

	/**
	 * The default constructor
	 */
	private SqlStatementManager() {

	}

	/**
	 * Initialize the manager, load sql statement from config file.
	 * 
	 * @param filename
	 *            the config filename
	 * @exception Exception
	 *                , throw exeception if config file format is invalid
	 */
	public void initialize(String filename) throws Exception {
		// System.out.println("debug:"+filename);
		config = ConfigurationLoader.loadFile("SqlStatements", filename);
	}

	/**
	 * Get the sql statement from manager
	 * 
	 * @param tablename
	 *            the tablename which the sql statement will be used
	 * @param key
	 *            the statement identifier
	 * @return the statement text, if the identifier doesn't exist return null.
	 */
	public String getSqlStatement(String tablename, String key) {
		String sql = config.getProperty(key, null, tablename);
		// System.out.println("debug: sql :"+sql);
		return sql;
	}

}
