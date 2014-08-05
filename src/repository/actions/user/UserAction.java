package repository.actions.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;

import qualifyControl.Constants;
import qualifyControl.xmlTool;
import repository.actions.BaseAction;
import repository.service.UserService;
import cn.org.act.sdp.repository.cloud.entity.UserTBean;

import com.opensymphony.xwork2.ActionContext;

import config.Config;

/**
 * 用户执行Action
 * @author mandula
 *
 */
public class UserAction extends BaseAction {

	private static final long serialVersionUID = -1479377447712316899L;

	private String userName;
	private String password;
	private String nationality;
	private String company;
	private String job;
	private String email;
	private String concern;
	private int conf = 16383;
	private String ip;
	private Date registerTime;
	
	private UserTBean userBean;
	private boolean flag = false;
	private String msg;
	
	private UserService userService;
	
	/**
	 * 保存一个用户UserTBean：注册
	 * @return
	 */
	public String saveUser(){
		System.out.println("enter UserAction.saveUser()");
		userBean = new UserTBean();
		userName = userName.trim();
		ip = getIpAddr(ServletActionContext.getRequest());	//获取客户端IP地址
		email = userName;
		registerTime = new Date();
		
		if(userName!=null && !userName.equals(""))
			userBean.setUserName(userName);
		if(password!=null && !password.equals(""))
			userBean.setPassword(password);
		if(nationality!=null && !nationality.equals(""))
			userBean.setNationality(nationality);
		if(company!=null && !company.equals(""))
			userBean.setCompany(company);
		if(job!=null && !job.equals(""))
			userBean.setJob(job);
		if(email!=null && !email.equals(""))
			userBean.setEmail(email);
		if(concern!=null && !concern.equals(""))
			userBean.setConcern(concern);
		if(conf!=0)
			userBean.setConf(conf);
		if(ip!=null && !ip.equals(""))
			userBean.setIp(ip);
		if(registerTime!=null)
			userBean.setRegisterTime(registerTime);

		//保存用户
		
		Document doc=new Document(),response;
		Element root,username,userpassword;
		xmlTool tool=new xmlTool();
		String url=Config.getUserControlUrl();
		
		root=new Element("register");
		doc.setRootElement(root);
		
		username=new Element("userName");
		username.setText(userName);
		root.addContent(username);
		
		userpassword=new Element("password");
		userpassword.setText(password);
		root.addContent(userpassword);
		
		response=tool.getResult(doc, url);
		System.out.println("用户注册结果："+response.getRootElement().getChild("isSuccessful").getValue());
		if("true".equals(response.getRootElement().getChild("isSuccessful").getValue()))
		{
			if(userService.add(userBean))
				ActionContext.getContext().getSession().put("userName", userName);
			
			return SUCCESS;
		}
		else if("false".equals(response.getRootElement().getChild("isSuccessful").getValue()))
			return FAILED;
		else
			return ERROR;
	}

	/**
	 * 获取已经登录用户的信息
	 * @return
	 * @throws Exception
	 */
	public String getLoginUser()throws Exception{
		String userName = null;
		ActionContext ctx = ActionContext.getContext();
		userName = (String)ctx.getSession().get("userName");
		userBean = userService.get(userName);
		return SUCCESS;
	}
	
	/**
	 * 根据用户名判断一个用户是否存在
	 * @return
	 */
	public String isUserNameExist(){
		List<UserTBean> userBeans = userService.getAll();
		for(UserTBean oldUser: userBeans){
			if(userName.equals(oldUser.getUserName())){
				msg = "The User Name have been used";
				flag = true;
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 用户登录
	 * @return
	 * @throws Exception
	 */
	public String loginUser()throws Exception {
		System.out.println("enter UserAction.loginUser()");
		userName = userName.trim();
		if(userName.length()==0){
			msg = "The user name is needed.";
			return LOGIN;
		}
		if(password.length()==0){
			msg = "The password is needed.";
			return LOGIN;
		}
		userBean = userService.get(userName);
		if(userBean==null){
			msg = "The user name or password you entered is incorrect.";
			return LOGIN;
		}
		else{
			if(!userBean.getPassword().equals(password)){
				msg = "The user name or password you entered is incorrect.";
				return LOGIN;
			}
			else{
				
				Document doc=new Document(),response;
				Element root,username,userpassword;
				xmlTool tool=new xmlTool();
				String url=Config.getUserControlUrl();
				
				root=new Element("login");
				doc.setRootElement(root);
				
				username=new Element("userName");
				username.setText(userName);
				root.addContent(username);
				
				userpassword=new Element("password");
				userpassword.setText(password);
				root.addContent(userpassword);
				
				response=tool.getResult(doc, url);
				
				if("false".equals(response.getRootElement().getChild("isSuccessful").getValue()))
				{
					System.out.println("user has logged in!");
					//先登出
					root=new Element("logout");
					doc.setRootElement(root);
					username=new Element("userName");
					username.setText(userName);
					root.addContent(username);
					response=tool.getResult(doc, url);	
					
					//再登入
					root=new Element("login");
					doc.setRootElement(root);
					username=new Element("userName");
					username.setText(userName);
					root.addContent(username);
					userpassword=new Element("password");
					userpassword.setText(password);
					root.addContent(userpassword);
					response=tool.getResult(doc, url);
					
					System.out.println("userName:"+userName+"  password:"+password);
					ActionContext.getContext().getSession().put("userName", userName);
				}
				else
				{
					System.out.println("userName:"+userName+"  password:"+password);
					ActionContext.getContext().getSession().put("userName", userName);
				}
				
				return SUCCESS;
			}
		}
	}
	
	/**
	 * 用户登出
	 * @return
	 * @throws Exception
	 */
	public String logoutUser()throws Exception {
		System.out.println("enter logoutUser()");
		ActionContext ctx = ActionContext.getContext();
		String userName=(String)ctx.getSession().get("userName");
		System.out.println("userName:   "+userName);
		Document doc=new Document(),response;
		Element root,username;
		xmlTool tool=new xmlTool();
		String url=Config.getUserControlUrl();
		
		root=new Element("logout");
		doc.setRootElement(root);
		
		username=new Element("userName");
		username.setText(userName);
		root.addContent(username);
		
		response=tool.getResult(doc, url);
		ctx.getSession().clear();
		return SUCCESS;
	}
	
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	} 

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConcern() {
		return concern;
	}

	public void setConcern(String concern) {
		this.concern = concern;
	}

	public int getConf() {
		return conf;
	}

	public void setConf(int conf) {
		this.conf = conf;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public UserTBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserTBean userBean) {
		this.userBean = userBean;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public static void main(String[] args) 
	{
		
	}
}
