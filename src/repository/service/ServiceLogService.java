package repository.service;

import java.util.List;

public interface ServiceLogService {
	
	/**
	 * 保存服务查询日志
	 * @param o
	 * @return
	 */
	public boolean save(Object o);
	
	/**
	 * 通过用户名获取服务查询日志
	 * @param name
	 * @return
	 */
	public List get(String name);
	
	/**
	 * 获取所有的服务查询日志
	 * @return
	 */
	public List getAll();
}
