package cn.org.act.sdp.bpmnRepository.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListenerContainer {
	private static ListenerContainer _container = null;
	private static HashMap<Integer, List<BpmnVersionUpdateListener>> _map = null;

	public static ListenerContainer getInstance() {
		if (_container == null) {
			_container = new ListenerContainer();
			_map = new HashMap<Integer, List<BpmnVersionUpdateListener>>();
		}
		return _container;
	}

	public boolean addListener(int titleId, BpmnVersionUpdateListener listener) {
		if (_map.containsKey(titleId)) {
			List<BpmnVersionUpdateListener> list = _map.get(titleId);
			list.add(listener);
		} else {
			List<BpmnVersionUpdateListener> list = new ArrayList<BpmnVersionUpdateListener>();
			list.add(listener);
			_map.put(titleId, list);
		}
		return true;
	}

	public boolean removeListener(int titleId,
			BpmnVersionUpdateListener listener) {
		if (_map.containsKey(titleId)) {
			List<BpmnVersionUpdateListener> list = _map.get(titleId);
			list.remove(listener);
			if (list.size() <= 0) {
				_map.remove(titleId);
			}
		}
		return true;
	}

	/**
	 * 
	 * @param titleId
	 *            updated title id
	 * @param bpmnId
	 *            new version bpmn id
	 */

	public void inform(int titleId, int bpmnId) {
		if (_map.containsKey(titleId)) {
			List<BpmnVersionUpdateListener> list = _map.get(titleId);
			for (BpmnVersionUpdateListener listener : list) {
				listener.refresh(bpmnId);
			}
		}
	}
}
