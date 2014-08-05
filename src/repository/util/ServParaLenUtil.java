package repository.util;

import cn.org.act.sdp.repository.entity.ServiceTBean;

public class ServParaLenUtil {

	public static ServiceTBean cut(ServiceTBean serviceBean) {
		if (serviceBean.getName().length() > 25)
			serviceBean.setName(serviceBean.getName().substring(0, 24) + "...");
		if (serviceBean.getDescription().length() > 36)
			serviceBean.setDescription(serviceBean.getDescription().substring(
					0, 35)
					+ "...");
		if (serviceBean.getUserName() == null
				|| serviceBean.getUserName().equals(""))
			serviceBean.setUserName("unknown");
		else if (serviceBean.getUserName().length() > 10)
			serviceBean.setUserName(serviceBean.getUserName().substring(0, 9)
					+ "...");
		return serviceBean;
	}
}
