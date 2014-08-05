package repository.entity;


public class HotTag {
	
	private String tagName;
	
	private long times;
	
	/**设置Tag在前台显示的大小的标准，取值范围0——9，具体的大小在前台设置*/
	private int globalTagWeight;
	
	/**前台显示tag的时候鼠标放在上面的时候显示的内容，可以是被使用的次数等*/
	private String title;
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public long getTimes() {
		return times;
	}
	public void setTimes(long times) {
		this.times = times;
	}
	public int getGlobalTagWeight() {
		return globalTagWeight;
	}
	public void setGlobalTagWeight(int globalTagWeight) {
		this.globalTagWeight = globalTagWeight;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
