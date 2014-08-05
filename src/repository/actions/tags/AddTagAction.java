package repository.actions.tags;

import java.util.LinkedList;
import java.util.List;

import repository.actions.BaseAction;
import repository.service.TagService;
import repository.service.TagServiceService;
import repository.util.Validate;

import cn.org.act.sdp.repository.cleavage.entity.TagServiceTBean;
import cn.org.act.sdp.repository.cleavage.entity.TagTBean;


public class AddTagAction extends BaseAction {

	private static final long serialVersionUID = -570297457726391720L;

	private String tagName;
	private long serviceId;
	private List<TagTBean> tags;	//属于ServiceId指定的Service包含的Tag
	private TagService tagService;
	private TagServiceService tagServiceService;
	private Validate validate;
	
	public String execute()throws Exception{
		if(validate.isString(tagName)){
			//此Tag不存在则保存
			if(!tagService.isExist(tagName)){
				tagService.save(tagName);
				tagServiceService.save(tagName, serviceId);
			}
			else{
				if(!tagServiceService.isExist(tagName, serviceId)){
					tagServiceService.save(tagName, serviceId);
					tagService.update(tagName);
				}
			}
		}
		//获取属于此Service的所有TagTBean
		List<TagServiceTBean> tagServiceBeans = tagServiceService.getByServiceId(serviceId);
		tags = new LinkedList<TagTBean>();
		TagTBean tagBean = null;
		for(int i=0;i<tagServiceBeans.size();i++){
			tagBean = tagService.getByName(tagServiceBeans.get(i).getTagName());
			tags.add(tagBean);
		} 
		return SUCCESS;
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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

	public Validate getValidate() {
		return validate;
	}

	public void setValidate(Validate validate) {
		this.validate = validate;
	}
	
}
