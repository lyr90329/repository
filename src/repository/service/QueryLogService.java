package repository.service;

import java.util.List;

public interface QueryLogService {

	public boolean delete(Object[] parameters);

	public Object get(Object[] parameters);

	public List get(String name);

	public boolean save(Object o);

	public boolean update(Object o);

}
