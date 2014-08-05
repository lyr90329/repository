package repository.actions.register;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.w3c.dom.DOMException;

import repository.actions.BaseAction;

import com.opensymphony.xwork2.ActionContext;

import cn.org.act.sdp.repository.classstorage.config.SqlStatementManager;
import cn.org.act.sdp.repository.cleavage.entity.MessageTBean;
import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.cleavage.session.MessageSession;
import cn.org.act.sdp.repository.cleavage.session.OperationSession;
import cn.org.act.sdp.repository.crust.impl.RegistryImpl;
import cn.org.act.sdp.repository.session.ServiceSession;
import cn.org.act.sdp.repository.session.SessionFactory;
import cn.org.act.sdp.repository.session.SessionType;

public class RegisterServiceAction extends BaseAction{
	//form message
	private String name;
	private String url;
	private String wsdlUrl;
	private String description;
	private String additionalInfo;
	private long businessId;
	
	//others
	private long id;
	private String msg;
	private RegistryImpl registryImpl;
	private MessageSession messageSession = null;
	private OperationSession operationSession = null;
	private ServiceSession serviceSession;
	private long jobId = 1;
	private long serviceId;
	private String filename = "";
	private long typeId = 1;
	private Long newServiceId;
	
	public String execute()throws Exception {        
		ActionContext ctx = ActionContext.getContext();
		String userName = (String)ctx.getSession().get("userName");

		id = registryImpl.registerServiceInfo(name, url, description, additionalInfo, wsdlUrl, "", businessId,userName);
		msg = "Register success!";
		//宋振华加入的部分start
		serviceSession = (ServiceSession) SessionFactory
		.openSession(SessionType.Service);
        messageSession = (MessageSession) cn.org.act.sdp.repository.cleavage.session.SessionFactory
		.openSession(cn.org.act.sdp.repository.cleavage.session.SessionType.Message);
        operationSession = (OperationSession) cn.org.act.sdp.repository.cleavage.session.SessionFactory
		.openSession(cn.org.act.sdp.repository.cleavage.session.SessionType.Operation);
		serviceId=id;
		filename=wsdlUrl;
		AnalyseWsdl();
		serviceSession.close();
		messageSession.close();
		operationSession.close();
		//宋振华加入的部分end
		return SUCCESS;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(long businessId) {
		this.businessId = businessId;
	}

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public RegistryImpl getRegistryImpl() {
		return registryImpl;
	}

	public void setRegistryImpl(RegistryImpl registryImpl) {
		this.registryImpl = registryImpl;
	}
	//宋振华加入的部分start
	private void parseOperation(Operation operation) {
		if (null == operation)
			return;

		OperationTBean operationBean = new OperationTBean();
		try {

			operationBean.setName(operation.getName());

			if (null == operation.getDocumentationElement())
				operationBean.setDescription("");
			else
				operationBean.setDescription(operation
						.getDocumentationElement().toString().replaceAll("'",
								"~"));

			// List inputParameters = new LinkedList();
			if (operation.getInput() != null) {
				if (operation.getInput().getMessage() != null) {
					if (operation.getInput().getMessage().getQName() != null) {
						Message input = operation.getInput().getMessage();
						String[] paras = new String[2];
						paras[0] = Long.toString(jobId);
						paras[1] = input.getQName().toString();
						long inputId = ((MessageTBean) messageSession
								.get(paras)).getId();
						operationBean.setInput(inputId);
					}
					
				}
			} else {
				operationBean.setInput(-1);
			}

			// List outputParameters = new LinkedList();
			if (operation.getOutput() != null) {
				if (operation.getOutput().getMessage() != null) {
					if (operation.getOutput().getMessage().getQName() != null) {
						Message output = operation.getOutput().getMessage();
						String[] paras = new String[2];
						paras[0] = Long.toString(jobId);
						paras[1] = output.getQName().toString();
						long outputId = ((MessageTBean) messageSession
								.get(paras)).getId();
						operationBean.setOutput(outputId);
					}

					
				}
			} else {
				operationBean.setOutput(-1);
			}

			operationBean.setFault("none");
			operationBean.setJobId(jobId);
			operationBean.setServiceId(serviceId);
			operationSession.save(operationBean);

		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	public String AnalyseWsdl(){
		// 载入WSDL文档************************************************************************************************
		Definition def = null;
		WSDLReader reader = null;

		try {
			WSDLFactory factory = WSDLFactory.newInstance();
			reader = factory.newWSDLReader();

			reader.setFeature("javax.wsdl.verbose", true);
			reader.setFeature("javax.wsdl.importDocuments", false);

			def = reader.readWSDL(null, filename);
			System.out.print(def.toString());

		} catch (Exception e) {
			e.printStackTrace();

			return ERROR;
		}

		SqlStatementManager manager = SqlStatementManager.singleInstance();
		Iterator iter = def.getServices().entrySet().iterator();

		Service service = null;
		try {
			service = (Service) ((Entry) iter.next()).getValue();
		} catch (Exception e) {
			System.err.println("service error");
		}

		try {
			Iterator it = def.getMessages().entrySet().iterator();
			while (it.hasNext()) {
				Message message = (Message) ((Entry) it.next()).getValue();
				MessageTBean messageBean = new MessageTBean();
				messageBean.setName(message.getQName().toString());
				if (message.getDocumentationElement() != null) {

					messageBean.setDescription(message
							.getDocumentationElement().toString()
							.replaceAll("'", "~"));

				} else {
					messageBean.setDescription("");
				}
				messageBean.setContent(message.toString());
				messageBean.setServiceId(serviceId);
				messageBean.setJobId(jobId);
				messageBean.setTypeId(typeId);
				messageSession.save(messageBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 载入完成*****************************************************************************************************

		// 解析文档*****************************************************************************************************
		iter = def.getServices().entrySet().iterator();
		service = null;
		service = (Service) ((Entry) iter.next()).getValue();
		if (null == service)
			return ERROR;

		Iterator operationIter = service.getPorts().entrySet().iterator();
		List operationList = null;
		Iterator subIter = null;
		Port tmpPort;
		while (operationIter.hasNext()) {
			tmpPort = ((Port) ((Entry) operationIter.next()).getValue());
			if (tmpPort != null && tmpPort.getBinding() != null
					&& tmpPort.getBinding().getPortType() != null) {
				operationList = tmpPort.getBinding().getPortType()
						.getOperations();
				if (operationList != null) {
					subIter = operationList.iterator();
					while (subIter.hasNext()) {
						parseOperation((Operation) subIter.next());

					}
				}

			}
		}

		// 解析完成****************************************************************************************************

		newServiceId = new Long(serviceId);
		return SUCCESS;
	}
	//宋振华加入的部分end
		
}
