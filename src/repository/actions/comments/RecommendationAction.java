package repository.actions.comments;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import repository.service.ServiceService;

import cn.org.act.sdp.repository.cleavage.recommendation.Preference;
import cn.org.act.sdp.repository.cleavage.recommendation.Recommendation;
import cn.org.act.sdp.repository.entity.ServiceTBean;

public class RecommendationAction extends ActionSupport {

	private static final long serialVersionUID = 6094721061560725837L;

	private String preference;
	private List<ServiceTBean> services;
	private ServiceService serviceService;

	public String getRecServices() throws Exception {
		Preference pre;
		if (preference.equals("correctness"))
			pre = Preference.correctness;
		else if (preference.equals("executeTime"))
			pre = Preference.executeTime;
		else if (preference.equals("respondVelocity"))
			pre = Preference.respondVelocity;
		else if (preference.equals("price"))
			pre = Preference.price;
		else if (preference.equals("reputation"))
			pre = Preference.reputation;
		else if (preference.equals("reliability"))
			pre = Preference.reliability;
		else if (preference.equals("stability"))
			pre = Preference.stability;
		else if (preference.equals("compatibility"))
			pre = Preference.compatibility;
		else
			return ERROR;
		services = new LinkedList<ServiceTBean>();
		Recommendation r = new Recommendation();
		List list = r.average(5, pre);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			services.add(serviceService.getById(new Long(1), (Long) it.next()));
		}
		return SUCCESS;

	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public List<ServiceTBean> getServices() {
		return services;
	}

	public void setServices(List<ServiceTBean> services) {
		this.services = services;
	}

	public ServiceService getServiceService() {
		return serviceService;
	}

	public void setServiceService(ServiceService serviceService) {
		this.serviceService = serviceService;
	}
}
