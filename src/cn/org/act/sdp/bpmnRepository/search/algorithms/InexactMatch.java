package cn.org.act.sdp.bpmnRepository.search.algorithms;

import java.util.HashSet;

import cn.org.act.sdp.bpmnRepository.search.model.Correlativity;
import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;
import cn.org.act.sdp.bpmnRepository.search.model.Node;
import cn.org.act.sdp.bpmnRepository.search.model.Path;

public class InexactMatch implements Match{
	//重复性和相关性分别所占的比重
	private final float q=(float)0.5;
	private Correlativity corr;
	public InexactMatch(Correlativity corr) {
		this.corr=corr;
	}
	public float match(DLGraph s,DLGraph query) {
        float simiarity=0;
        float size=query.getPaths().size();
        for (Path pathi : query.getPaths()) {
			simiarity+=sim(pathi,s);
		}
        simiarity=simiarity/size;
        return simiarity;
	}

	private float sim(Path pathi, DLGraph s) {
		float max=0;
		for (Path pathj : s.getPaths()) {
			float tem=0;
			tem=computeSim(pathi, pathj);
			if(tem>max)
				max=tem;			
		}
		return max;
	}

	private float computeSim(Path pathi, Path pathj) {
		//返回值
		float re=0;
		//具有相同的活动的部分
		float com=0;
		//具有相关性的部分
		float cor=0;
		//包含的相同活动
		HashSet<String> comAct=new HashSet<String>();
		//可能具有相关性的活动
		HashSet<String> corAct=new HashSet<String>();
		//pathi活动
		HashSet<String> queryAct=new HashSet<String>();
		for (Node node : pathi.getActivities()) {
			queryAct.add(node.getLabel());
		}
		for (Node node : pathj.getActivities()) {
			String lab=node.getLabel();
			if(queryAct.contains(lab)){
			    comAct.add(lab);
			}else{
				corAct.add(lab);
			}
		}
		com=((float)comAct.size())/((float)queryAct.size());
		cor=computeCor(corAct,queryAct);
		re=com*q+cor*(1-q);
//		if(pathi.getLabel().contains(pathj.getLabel()))
//			return 1;
		return re;
	}
    /**
     * 计算两条路径中不同节点的相关度
     * @param corAct
     * @param queryAct
     * @return
     */
	private float computeCor(HashSet<String> corAct, HashSet<String> queryAct) {
		float re=0;
		for (String query : queryAct) {
			for (String other : corAct) {
				re+=corr.getC(query, other);//更多的这里返回的是零
			}
		}
		if(queryAct.size()==0||corAct.size()==0)
			return 0;
		re=re/(float)(queryAct.size()*corAct.size());
		return re;
	}
}
