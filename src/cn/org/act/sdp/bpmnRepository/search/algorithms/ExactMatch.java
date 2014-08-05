package cn.org.act.sdp.bpmnRepository.search.algorithms;

import java.util.HashSet;

import cn.org.act.sdp.bpmnRepository.search.model.DLGraph;
import cn.org.act.sdp.bpmnRepository.search.model.Node;
import cn.org.act.sdp.bpmnRepository.search.model.Path;

/**
 * 精确匹配，严格的按照业务流程中节点的包含关系，不考虑数据挖掘中的支持度和相关度
 * @author dell
 *
 */
public class ExactMatch implements Match{
	public float match(DLGraph s,DLGraph query) {
        float simiarity=0;
        float size=query.getPaths().size();
        for (Path pathi : query.getPaths()) {
			simiarity+=sim(pathi,s);
		}
        simiarity=simiarity/size;
        return simiarity;
	}
    /**
     * 看pathi是否包含在s中，如果没有，返回有几个节点包含在s中（百分比）
     * @param pathi
     * @param s
     * @return
     */
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
//		cor=computeCor(corAct,queryAct);
//		re=com*q+cor*(1-q);
//		if(pathi.getLabel().contains(pathj.getLabel()))
//			return 1;
		return com;
	}
}
