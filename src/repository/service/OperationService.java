package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cleavage.entity.OperationTBean;

public interface OperationService {

	public List<OperationTBean> getByServiceId(Long jobId, Long serviceId);

	public OperationTBean getByName(Long jobId, String operationName);

	public OperationTBean getById(Long jobId, Long operationId);
}
