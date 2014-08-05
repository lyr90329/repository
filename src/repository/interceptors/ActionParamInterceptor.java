package repository.interceptors;

import org.apache.struts2.ServletActionContext;

import repository.actions.BaseAction;
import repository.util.PageFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 初始化ACTION
 * 
 * @author E-mail:msg.mandula@gmail.com
 * @version
 * @since 2008-7-1 上午12:07:25
 * 
 */
public class ActionParamInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3569573928536546807L;

	public String intercept(ActionInvocation invocation) throws Exception {
		Object object = invocation.getAction();
		String result = null;
		if (object instanceof BaseAction) {
			BaseAction baseAction = (BaseAction) object;
			baseAction.setPage(PageFactory.getPage(ServletActionContext
					.getRequest()));
			baseAction.setRequest(ServletActionContext.getRequest());
			baseAction.setResponse(ServletActionContext.getResponse());
			baseAction.setPageContext(ServletActionContext.getPageContext());
			baseAction.setSession(ServletActionContext.getRequest()
					.getSession());
			baseAction.setServletContext(ServletActionContext
					.getServletContext());
			result = invocation.invoke();

		} else {
			result = invocation.invoke();
		}
		return result;
	}

}
