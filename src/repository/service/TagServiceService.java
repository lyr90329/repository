package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.TagServiceTBean;

public interface TagServiceService {

	public boolean save(String tagName, Long serviceId);

	public List<TagServiceTBean> getByServiceId(Long serviceId);

	public List<TagServiceTBean> getByTagName(String tagName);

	public boolean isExist(String tagName, long serviceId);

}
