package repository.actions.tags;

import java.util.Map;

import repository.actions.BaseAction;
import repository.entity.HotTag;
import repository.service.TagService;

public class HotTagsAction extends BaseAction {

	private static final long serialVersionUID = 2806841783535716352L;

	private int num;
	private Map<String, HotTag> tags;
	private TagService tagService;

	public String execute() throws Exception {
		tags = tagService.getHotTags(num);
		return SUCCESS;
	}

	public Map<String, HotTag> getTags() {
		return tags;
	}

	public void setTags(Map<String, HotTag> tags) {
		this.tags = tags;
	}

	public TagService getTagService() {
		return tagService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
