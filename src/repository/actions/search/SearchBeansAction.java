package repository.actions.search;

import java.util.LinkedList;
import java.util.List;

import repository.actions.BaseAction;
import repository.entity.Page;
import repository.service.KeywordService;
import repository.service.ServiceKeywordService;
import repository.service.ServiceService;


import cn.org.act.sdp.repository.cleavage.entity.KeywordTBean;
import cn.org.act.sdp.repository.cleavage.entity.ServiceKeywordTBean;
import cn.org.act.sdp.repository.entity.ServiceTBean;
import cn.org.act.sdp.repository.query.common.BeanType;
import cn.org.act.sdp.repository.query.common.UBean;

/**
 * 搜索
 * @author Mandula Jugaqin 
 *
 */
public class SearchBeansAction extends BaseAction{	
	
	private static final long serialVersionUID = -5690517110424251636L;

	private String searchText;			//搜索的内容
	private String searchType;			//搜索类型
	private BeanType beanType;			//bean的类型
	private List<ServiceTBean> serviceBeans;	//发送给客户端的serviceTBean集合
	private List<UBean> uBeanList;		//发送给客户端的bean集合
	
	private cn.org.act.sdp.repository.query.compiler.Compiler compiler;		//搜多模块
	
	private KeywordService keywordService;
	private ServiceKeywordService serviceKeywordService;
	private ServiceService serviceService;
	
	public String execute()throws Exception {
		List<UBean> allUBean = null;	//xpath搜索出来的所有Bean集合
		List<ServiceTBean> allServices = null;	//关键字搜索出来的所有ServiceTBean集合
		KeywordTBean keywordBean = null;
		List<ServiceKeywordTBean> serviceKeywordBeans = null;
		
		if(searchType.equals("xpath")){ //xpath只能搜索service
			compiler = new cn.org.act.sdp.repository.query.compiler.Compiler(searchText);
			allUBean = compiler.compile(1);
			if(allUBean==null){		//搜索结果位空
				return XPATH;
			}
			else{//分页处理
				this.getPage().setTotalLine(allUBean.size());
				uBeanList = new LinkedList<UBean>();
				for(int i=getPage().getBeginLine();i<=getPage().getEndLine();i++){
					uBeanList.add(allUBean.get(i));
				}
				return XPATH;
			}
		}
		else if(searchType.equals("key_word")){ //关键字搜索，针对ServiceTBean的搜索		
			Long jobId = new Long(1);
			allServices = new LinkedList<ServiceTBean>();
			
			keywordBean = keywordService.getByValue(jobId,searchText);
			if(keywordBean==null)
				return KEYWORD;
			serviceKeywordBeans = serviceKeywordService.getByKeywodId(jobId, keywordBean.getId());
			allServices = serviceService.getById(jobId, serviceKeywordBeans);
		
			getPage().setTotalLine(allServices.size());
			
			serviceBeans = new LinkedList<ServiceTBean>();
			for(int i=getPage().getBeginLine();i<=getPage().getEndLine();i++){
				serviceBeans.add(allServices.get(i));
			}
			return KEYWORD;
		}
		else if(searchType.equals("rec_search")){
			Long jobId = new Long(1);
			allServices = new LinkedList<ServiceTBean>();
			
			keywordBean = keywordService.getByValue(jobId,searchText);
			if(keywordBean==null)
				return "recommendation";
			serviceKeywordBeans = serviceKeywordService.getByKeywodId(jobId, keywordBean.getId());
			allServices = serviceService.getById(jobId, serviceKeywordBeans);
			serviceBeans = allServices;
			return "recommendation";
		}
		return ERROR;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public BeanType getBeanType() {
		return beanType;
	}

	public void setBeanType(BeanType beanType) {
		this.beanType = beanType;
	}

	public List<ServiceTBean> getServiceBeans() {
		return serviceBeans;
	}

	public void setServiceBeans(List<ServiceTBean> serviceBeans) {
		this.serviceBeans = serviceBeans;
	}

	public List<UBean> getUBeanList() {
		return uBeanList;
	}

	public void setUBeanList(List<UBean> beanList) {
		uBeanList = beanList;
	}

	public cn.org.act.sdp.repository.query.compiler.Compiler getCompiler() {
		return compiler;
	}

	public void setCompiler(
			cn.org.act.sdp.repository.query.compiler.Compiler compiler) {
		this.compiler = compiler;
	}

	public KeywordService getKeywordService() {
		return keywordService;
	}

	public void setKeywordService(KeywordService keywordService) {
		this.keywordService = keywordService;
	}

	public ServiceKeywordService getServiceKeywordService() {
		return serviceKeywordService;
	}

	public void setServiceKeywordService(ServiceKeywordService serviceKeywordService) {
		this.serviceKeywordService = serviceKeywordService;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}
}
