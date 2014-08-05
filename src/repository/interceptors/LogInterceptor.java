package repository.interceptors;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import repository.actions.BaseAction;
import repository.entity.Log;
import repository.service.QueryLogService;
import repository.service.ServiceLogService;
import repository.service.ServiceSubscriptionService;
import repository.service.impl.QueryLogServiceImpl;
import repository.service.impl.ServiceLogServiceImpl;
import repository.service.impl.ServiceSubscriptionServiceImpl;
import repository.util.UrlUtil;

import cn.org.act.sdp.repository.cloud.entity.QueryLogTBean;
import cn.org.act.sdp.repository.cloud.entity.ServiceLogTBean;
import cn.org.act.sdp.repository.cloud.entity.ServiceSubscriptionTBean;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 日志拦截器
 * @author Administrator
 *
 */
public class LogInterceptor extends AbstractInterceptor{

	
	private static final long serialVersionUID = -8328290435903056943L;
	

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String currentAction = UrlUtil.getAction(request.getRequestURL().toString());	//获取请求的Action名称
		String userName = (String)ServletActionContext.getRequest().getSession().getAttribute("userName");	//登录用户名
		String result = null;
		
		result = invocation.invoke();	//执行目标action
		
		if((result.equals(BaseAction.SUCCESS)) && currentAction.equals("ServiceOverView")){
			serviceLog(request,userName);
			QueryLog(request,userName);
		}
		if(result.equals(BaseAction.SUCCESS) && currentAction.equals("ServiceSubscription")){
			ServiceSubscription(request,userName);
		}
		
		return result;
	}
	
	/**
	 * 记录查询服务详细信息日志
	 * @param request
	 */
	public void serviceLog(HttpServletRequest request,String userName){
		if(userName==null || userName.equals(""))
			return;
		ServiceLogService service = new ServiceLogServiceImpl();
		ServiceLogTBean bean = new ServiceLogTBean();
		
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));
		bean.setServiceId(serviceId);
		bean.setTimestamp(new Date());
		bean.setUserName(userName);
		service.save(bean);
		
		//标准输出
		Log log = new Log(new Date(),userName+" over view the service@serviceId="+serviceId);
		System.out.println(log.toString());
	}
	
	/**
	 * 记录服务订阅日志
	 * @param request
	 */
	public void ServiceSubscription(HttpServletRequest request,String userName){
		ServiceSubscriptionService service = new ServiceSubscriptionServiceImpl();
		long serviceId = Long.parseLong(request.getParameter("serviceId"));
		ServiceSubscriptionTBean bean = new ServiceSubscriptionTBean(userName,serviceId);
		service.save(bean);
		//标准输出
		Log log = new Log(new Date(),userName+" subscribe the service@serviceId="+serviceId);
		System.out.println(log.toString());
	}
	
	public void QueryLog(HttpServletRequest request,String userName){
		if(userName==null || userName.equals(""))
			return;
		String searchText = request.getParameter("searchText");
		String searchType = request.getParameter("searchType");
		int serviceId = Integer.parseInt(request.getParameter("serviceId"));
		if(searchText==null || searchText.equals("") || !searchType.equals("key_word"))
			return;
		QueryLogService service = new QueryLogServiceImpl();
		QueryLogTBean bean = new QueryLogTBean();
		bean.setServiceId(serviceId);
		bean.setKeyword(searchText);
		bean.setUserName(userName);
		bean.setTimestamp(new Date());
		service.save(bean);
		//标准输出
		Log log = new Log(new Date(),userName+" query the service@serviceId="+serviceId+"with keyword="+searchText);
		System.out.println(log.toString());		
	}
	
	
	
	
	
	
	
	/**
	 * action执行之前运行
	 * @param invocation
	 * @throws Exception
	 */
//	protected void before(ActionInvocation invocation) throws Exception {
//		
//	}
	
	/**
	 * action执行之后运行
	 * @param invocation
	 * @param result
	 * @throws Exception
	 */
//	protected String after(ActionInvocation invocation, String result) throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String currentAction = UrlUtil.getAction(request.getRequestURL().toString());	//获取请求的Action名称
//		String userName = (String)ServletActionContext.getRequest().getSession().getAttribute("userName");	//登录用户名
//		
//		if(result.equals(BaseAction.SUCCESS) && currentAction.equals("ServiceOverView")){
//			serviceLog(request,userName);
//		}
//		if(result.equals(BaseAction.SUCCESS) && currentAction.equals("ServiceSubscription")){
//			ServiceSubscription(request,userName);
//		}
//		
//		return result;
//	}

}
