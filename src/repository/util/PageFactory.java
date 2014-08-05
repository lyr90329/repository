package repository.util;

import javax.servlet.http.HttpServletRequest;

import repository.entity.Page;

/**
 * 说明:翻页工厂类
 * 
 * @author E-mail:hexin@resoft.css.com.cn
 * @version
 * @since 2008-6-30 下午10:24:52
 * 
 */
public class PageFactory {

	/**
	 * 构造器
	 */
	private PageFactory() {
	}

	/**
	 * 方法说明:取得page实例
	 * 
	 * @param servletrequest
	 *            前台request对象
	 * @return
	 * @author
	 * @since 2008-6-30 下午10:23:01
	 */
	public static final Page getPage(HttpServletRequest request) {
		Page page = new Page();
		String page_mode = request.getParameter("page_mode");
		String page_index = request.getParameter("page_index");
		String page_size = request.getParameter("page_size");
		if (page_mode != null && !page_mode.equals("") && page_index != null
				&& !page_index.equals("")) {
			page.setPageMode(page_mode);
			page.setPageIndex(Integer.parseInt(page_index));
		}
		if (page_size != null && !page_size.equals("")) {
			page.setPageSize(Integer.parseInt(page_size));
		}
		return page;
	}
}
