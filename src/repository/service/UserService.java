package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cloud.entity.UserTBean;

public interface UserService {

	public boolean add(UserTBean bean);

	public boolean delete(String userName);

	public UserTBean get(String userName);

	public List getAll();

	public boolean update(UserTBean bean);
}
