package cn.org.act.sdp.bpmnRepository.config;

import java.util.List;

/**
 * This class is a tool class, it append parameters to the raw sql statement.
 * 
 * @author <a href=”mailto:linwei@act.buaa.edu.cn”>Lin Wei</a>
 * @version $Revision: 1.2 $ $Date: 2010-05-13 03:19:10 $
 */
public class SqlHelper {

	/**
	 * the tag which identify a parameter
	 */
	private static final String TAG = "{}";
	
	/**
	 * Append parameters to the raw sql statement and return final statement
	 * 
	 * @param statement
	 *            the raw sql where '?' means a paramenter.
	 * @param paras
	 *            the parameters
	 * @return the final sql statement
	 */
	public static String getFinalSql(String statement, List<String> paras) {

		// check the parameters
		if(	statement==null || statement.length()==0
			|| paras == null  || paras.size() == 0 )
			return statement;
		
		StringBuffer buf = new StringBuffer(statement);
		int index = buf.indexOf(TAG);
		for(String para : paras) {
			
			if(index == -1) {
				continue;
			}
			
			buf.replace(index, index+TAG.length(), para);
			index = buf.indexOf(TAG, index);
		}
		String sql = buf.toString();
//		System.out.println("debug: final sql :"+sql);
		return sql;
	}

}
