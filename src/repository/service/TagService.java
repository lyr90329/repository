package repository.service;

import java.util.List;
import java.util.Map;

import repository.entity.HotTag;

import cn.org.act.sdp.repository.cleavage.entity.TagTBean;

public interface TagService {

	/**
	 * 保存一个新Tag
	 * @param tagName
	 * @return
	 */
	public boolean save(String tagName);
	
	/**
	 * 获取所有的Tag
	 * @return
	 */
	public List<TagTBean> getAll();
	
	/**
	 * 通过tagName获取TagTBean对象
	 * @param tagName
	 * @return
	 */
	public TagTBean getByName(String tagName);
	
	/**
	 * 已经存在的Tag的times字段增加一
	 * @param tagName
	 * @return
	 */
	public boolean update(String tagName);
	
	/**
	 * 判断某个Tag是否已经存在
	 * @param tagName
	 * @return 返回true表明存在,否则不存在
	 */
	public boolean isExist(String tagName);
	
	/**
	 * 
	 * @param size
	 * @return
	 */
	public Map<String,HotTag> getHotTags(int size);
}
