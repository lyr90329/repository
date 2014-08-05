package repository.entity;

import java.io.Serializable;

/**
 * 说明:全局翻页类
 * 
 * @author E-mail:msg.mandula@gmail.com
 * @version
 * @since 2008-6-30 下午10:17:17
 * 
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 474686333071715399L;

	public static final int DEFAULT_PAGE_SIZE = 10;// 默认页数

	private int pageSize = DEFAULT_PAGE_SIZE;// 一个页面显示总数

	private int pageIndex = 1;// 当前页

	private int totalLine = 0;// 总行数

	private int pageCount = 0;// 总页数

	private int beginLine = 0;// 起始行

	private int endLine = 0;// 结束行

	private String pageMode = null;

	/**
	 * 默认构造函数
	 */
	public Page() {
		this(1, DEFAULT_PAGE_SIZE, null);
	}

	/**
	 * 
	 * @param pageIndex
	 *            当前页
	 */
	public Page(int pageIndex) {
		this(pageIndex, DEFAULT_PAGE_SIZE, null);
	}

	/**
	 * 总行数为0的构造函数
	 * 
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            一个页面显示总数
	 */
	public Page(int pageIndex, int pageSize) {
		this(pageIndex, pageSize, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            一个页面显示总数
	 * @param totalLine
	 *            总行数
	 */
	public Page(int pageIndex, int pageSize, String pageMode) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		if (pageSize < 1) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.pageMode = pageMode;
	}

	public int getPageIndex() {
		return pageIndex < 1 ? 1 : pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize < 1 ? Page.DEFAULT_PAGE_SIZE : pageSize;
		this.endLine = this.beginLine + pageSize - 1;
	}

	public int getTotalLine() {
		return totalLine;
	}

	public void setTotalLine(int totalLine) {
		this.pageCount = (int) Math
				.ceil((double) totalLine / (double) pageSize);
		this.totalLine = totalLine;
		if (this.pageMode == null || this.pageMode.equals("")
				|| this.pageMode.equals("first")) {
			this.setPageIndex(1);
		} else if (this.pageMode.equals("next")) {
			this
					.setPageIndex(this.pageIndex + 1 > this.pageCount ? this.pageCount
							: this.pageIndex + 1);
		} else if (this.pageMode.equals("priv")) {
			this.setPageIndex(this.pageIndex - 1 < 1 ? 1 : this.pageIndex - 1);
		} else if (this.pageMode.equals("last")) {
			this.setPageIndex(pageCount);
		}
		this.beginLine = this.pageSize * (this.pageIndex - 1) > (this.totalLine - 1) ? (this.totalLine - 1)
				: this.pageSize * (this.pageIndex - 1);
		this.endLine = (this.beginLine + pageSize - 1) > (this.totalLine - 1) ? (this.totalLine - 1)
				: (this.beginLine + pageSize - 1);
		if (this.beginLine < 0 || this.endLine < 0)
			this.endLine = this.beginLine - 1;
	}

	public int getPageCount() {
		int i = (int) Math.ceil((double) totalLine / (double) pageSize);
		return i;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getFirstResult() {
		return (pageIndex - 1) * pageSize;
	}

	public boolean getHasPrevious() {
		return pageIndex > 1;
	}

	public boolean getHasNext() {
		return pageIndex < pageCount;
	}

	public boolean isEmpty() {
		return totalLine == 0;
	}

	public int getBeginLine() {
		return beginLine;
	}

	public void setBeginLine(int beginLine) {
		this.beginLine = beginLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public void setEndLine(int endLine) {
		this.endLine = endLine;
	}

	public String getPageMode() {
		return pageMode;
	}

	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

}
