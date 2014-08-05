package repository.actions.service;

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
import repository.service.ServiceService;

import cn.org.act.sdp.repository.classstorage.config.SqlStatementManager;
import cn.org.act.sdp.repository.cleavage.entity.MessageTBean;
import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;
import cn.org.act.sdp.repository.cleavage.session.MessageSession;
import cn.org.act.sdp.repository.cleavage.session.OperationSession;
import cn.org.act.sdp.repository.entity.ServiceTBean;
import cn.org.act.sdp.repository.session.ServiceSession;
import cn.org.act.sdp.repository.session.SessionFactory;
import cn.org.act.sdp.repository.session.SessionType;

public class ServiceUpdateAction extends BaseAction {

	private static final long serialVersionUID = 8672965663024699359L;

	private String wsdl_url;
	private String description;
	private long serviceId;
	private Long newServiceId;
	private ServiceService serviceService;
	private ServiceSession serviceSession;

	private long jobId = 1;
	private long typeId = 1;
	private String filename = "";
	private MessageSession messageSession = null;
	private OperationSession operationSession = null;
	private String end;

	public String execute() throws Exception {
		serviceSession = (ServiceSession) SessionFactory
				.openSession(SessionType.Service);
		messageSession = (MessageSession) cn.org.act.sdp.repository.cleavage.session.SessionFactory
				.openSession(cn.org.act.sdp.repository.cleavage.session.SessionType.Message);
		operationSession = (OperationSession) cn.org.act.sdp.repository.cleavage.session.SessionFactory
				.openSession(cn.org.act.sdp.repository.cleavage.session.SessionType.Operation);

		ServiceTBean bean = serviceService.getById(jobId, serviceId);

		String oldWsdlUrl = bean.getWsdlUrl();
		bean.setDescription(description);
		if (oldWsdlUrl.equals(wsdl_url)) {
			serviceService.update(bean);
			return SUCCESS;
		} else {
			updateServices(bean);
			end =AnalyseWsdl();
		}
		return end ;
	}

	public String getWsdl_url() {
		return wsdl_url;
	}

	public void setWsdl_url(String wsdl_url) {
		this.wsdl_url = wsdl_url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	public Long getNewServiceId() {
		return newServiceId;
	}

	public void setNewServiceId(Long newServiceId) {
		this.newServiceId = newServiceId;
	}

	public void updateServices(ServiceTBean bean){
		serviceSession.delete(bean);
		bean.setWsdlUrl(wsdl_url);
		bean.setDescription(description);
		serviceSession.save(bean);
		bean = serviceService.getByUrl(wsdl_url, jobId);
		serviceId = bean.getId();
		filename = bean.getWsdlUrl();
		
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

}
