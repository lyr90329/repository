package repository.service;

import java.util.List;

import cn.org.act.sdp.repository.cleavage.session.RelatedType;

public interface ServiceGraphService {

	public List<Long> getRelatedOperationIds(long operationId, RelatedType type);

}
