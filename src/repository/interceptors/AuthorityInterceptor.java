package repository.interceptors;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import repository.util.UrlUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限拦截器，如果未登录用户访问特定的action会跳转到登陆页面
 * @author mandula
 *
 */
public class AuthorityInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 2722210568141805779L;
	private static final String[] actions = {
		"ServiceSubscription",
		"RegisterServicePre",
		"RegisterBusinessEntity",
		"RegisterService",
		"ServicePreUpdate",
		"ServiceUpdate",
		"Logout",
		"GetUserInfo",
		"GetServiceLog",
		"GetQueryLog",
		"GetServiceSubscriptionLog"
	};
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		int flag = 0;
		String result;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String currentAction = UrlUtil.getAction(request.getRequestURL().toString());	//获取请求的Action名称
		String userName = (String)ServletActionContext.getRequest().getSession().getAttribute("userName");	//登录用户名
		for(int i=0;i<actions.length;i++){
			if(actions[i].equals(currentAction)){
				flag = 1;
				break;
			}
		}
		if(flag==1 && (userName==null || userName.equals("")))	//不允许访问
			result = "login";
		else{
			result = invocation.invoke();
		}
		return result;
	}

}
