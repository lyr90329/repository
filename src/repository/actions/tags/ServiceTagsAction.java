package repository.actions.tags;

import java.util.LinkedList;
import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.TagServiceTBean;
import cn.org.act.sdp.repository.cleavage.entity.TagTBean;

import repository.actions.BaseAction;
import repository.service.TagService;
import repository.service.TagServiceService;

public class ServiceTagsAction extends BaseAction {

	private long serviceId;
	private boolean flag = false;
	private List<TagTBean> tags;
	private TagService tagService;
	private TagServiceService tagServiceService;

	public String execute() {
		int len = 0;
		tags = new LinkedList<TagTBean>();
		//获取属于此Service的所有TagTBean
		List<TagServiceTBean> tagServiceBeans = tagServiceService
				.getByServiceId(serviceId);
		TagTBean tagBean = null;
		for (int i = 0; i < tagServiceBeans.size(); i++) {
			tagBean = tagService.getByName(tagServiceBeans.get(i).getTagName());
			len = len + tagBean.getName().length() + 2;
			if (len > 45) {
				flag = true;
				break;
			}
			tags.add(tagBean);

		}
		return SUCCESS;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public List<TagTBean> getTags() {
		return tags;
	}

	public void setTags(List<TagTBean> tags) {
		this.tags = tags;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public TagServiceService getTagServiceService() {
		return tagServiceService;
	}

	public void setTagServiceService(TagServiceService tagServiceService) {
		this.tagServiceService = tagServiceService;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
