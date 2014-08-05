package cn.org.act.sdp.bpmnRepository.search.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.org.act.sdp.bpmnRepository.entity.GraphModelBean;
import cn.org.act.sdp.bpmnRepository.tool.GraphModelTool;

public class Correlativity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3325365094156644438L;

	/**
	 * 
	 */

	private HashSet<Node> activities = new HashSet<Node>();

	private HashMap<String, Integer> appearMap = new HashMap<String, Integer>();

	private HashMap<String, Integer> appearCMap = new HashMap<String, Integer>();

//	private HashMap<String, DLGraph> workflows = new HashMap<String, DLGraph>();
	
//	private int appearTime=0;
	
//	private int mostFre=0;
	

	private  HashMap<String, Float> C = new HashMap<String, Float>();

	private  HashMap<String, HashSet<String>> P = new HashMap<String, HashSet<String>>();

//	private  float numofWorkflow = 0;

	public void computeC() {
//		numofWorkflow = workflows.size();
		GraphModelTool tool=new GraphModelTool();
		ArrayList<GraphModelBean> graphs=tool.getAllModel();
		for (GraphModelBean bean : graphs) {
			DLGraph graph=bean.getModelStream();
			List<Node> nodes=Arrays.asList(graph.vertices());
			activities.addAll(nodes);
			for (Node node : nodes) {
				String acti1=node.getLabel();
				if (!appearMap.containsKey(acti1))
					appearMap.put(acti1, 0);
				Integer in = appearMap.get(acti1);
				++in;
				appearMap.put(acti1, in);
				for (Node node2 : nodes) {
					String acti2=node2.getLabel();
					if (!appearCMap.containsKey(acti1 + "$$" + acti2))// 联合出现以$$连接
						appearCMap.put(acti1 + "$$" + acti2, 0);
					Integer in1 = appearCMap.get(acti1 + "$$" + acti2);
					++in1;
					appearCMap.put(acti1 + "$$" + acti2, in1);
				}
			}
		}
		for (Node node : activities) {
			String acti1=node.getLabel();
			for (Node node1 : activities) {
				String acti2=node1.getLabel();
				int Nofelement = appearMap.get(acti1);
				int NofC;
				if (appearCMap.containsKey(acti1 + "$$" + acti2))// 联合出现以$$连接
				{
					NofC = appearCMap.get(acti1 + "$$" + acti2);
					C.put(acti1 + "$$" + acti2, (float) NofC
							/ (float) Nofelement);
//					 P.put(acti1 + "$$" + acti2, (float)
//					 NofC/(float)workflows.size());
				}
			}
		}
		/* 统计所有活动在流程库中出现的次数(针对的是某个流程) */
//		Set<String> key = workflows.keySet();
//		for (Iterator iter = key.iterator(); iter.hasNext();) {
//			String element = (String) iter.next();
//			WorkflowGraph workflow = workflows.get(element);
//			Set<String> actis = workflow.getActivities();
//			appearTime+=actis.size();//为了计算活动平均出现次数
//			for (Iterator iterator = actis.iterator(); iterator.hasNext();) {
//				String element1 = (String) iterator.next();
//				if (!P.containsKey(element1))// 这里一并统计P的值
//					P.put(element1, new HashSet<String>());
//				HashSet<String> te = P.get(element1);
//				te.add(workflow.getName());
//				P.put(element1, te);
//				if (!appearMap.containsKey(element1))
//					appearMap.put(element1, 0);
//				Integer in = appearMap.get(element1);
//				++in;
//				appearMap.put(element1, in);
//				for (Iterator iterator1 = actis.iterator(); iterator1.hasNext();) {
//					String element2 = (String) iterator1.next();
//					if (!appearCMap.containsKey(element1 + "$$" + element2))// 联合出现以$$连接
//						appearCMap.put(element1 + "$$" + element2, 0);
//					Integer in1 = appearCMap.get(element1 + "$$" + element2);
//					++in1;
//					appearCMap.put(element1 + "$$" + element2, in1);
//				}
//			}
//		}
//		System.out.println("活动的平均出现次数："+(float)appearTime/(float)activities.size()+" 活动数"+activities.size());
//		for (Iterator iter = activities.iterator(); iter.hasNext();) {
//			String element = (String) iter.next();
//			for (Iterator iterator = activities.iterator(); iterator.hasNext();) {
//				String element1 = (String) iterator.next();
//				int Nofelement = appearMap.get(element);
//				if(Nofelement>mostFre&&Nofelement!=443)//计算最多出现次数
//				{	
//					mostFre=Nofelement;
//					if(mostFre==95)
//						System.out.println(element);
//				}
//				int NofC;
//				if (appearCMap.containsKey(element + "$$" + element1))// 联合出现以$$连接
//				{
//					NofC = appearCMap.get(element + "$$" + element1);
//					C.put(element + "$$" + element1, (float) NofC
//							/ (float) Nofelement);
//					// P.put(element + "$$" + element1, (float)
//					// NofC/(float)workflows.size());
//				}
//				// else{
//				// NofC=0;
//				// C.put(element + "$$" + element1, (float) NofC
//				// / (float) Nofelement);
//				// }
//			}
//		}
//		System.out.println("mostFrequency: "+mostFre);
//		try {
//			showSta();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void showSta() throws IOException {// 输出数据
		File cout = new File("output/correlativity");
		FileWriter writer1 = new FileWriter(new File("output/countSingle.re"));
		FileWriter writer2 = new FileWriter(new File("output/countCommon.re"));
		FileWriter writer3 = new FileWriter(new File("output/correlativity.re"));
		Set<String> key = appearMap.keySet();
		writer1.write("******************出现次数*********************" + '\n');
		for (Iterator iter = key.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			writer1.write(element + ":   " + appearMap.get(element) + '\n');
			// System.out.println(element + ": " + appearMap.get(element));
		}
		writer1.flush();
		key = appearCMap.keySet();
		writer2.write("******************相关出现*********************" + '\n');
		for (Iterator iter = key.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			writer2.write(element + ":   " + appearCMap.get(element) + '\n');
			// System.out.println(element + ": " + appearCMap.get(element));
		}
		writer2.flush();
		key = C.keySet();
		writer3.write("******************相关度*********************" + '\n');
		for (Iterator iter = key.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			 writer3.write(element + ": " + C.get(element)+'\n');
//			 System.out.println(element + ": " + C.get(element));
		}
		writer3.flush();
	}

	public  float getC(String e1, String e2) {
		if (!C.containsKey(e1 + "$$" + e2))
			return 0;
		else
			return C.get(e1 + "$$" + e2);
	}

//	public  float getP(HashSet<String> in) {
//		HashSet<String> start = new HashSet<String>();// 可能破坏数据结构,需要新建
//		boolean first = true;
//		for (Iterator iter = in.iterator(); iter.hasNext();) {
//			String element = (String) iter.next();
//			if (first) {
//				first = false;
//				start.addAll(P.get(element));
//			} else
//				start.retainAll(P.get(element));// 不断的取其交集
//
//		}
////		System.out.println((float) start.size() / numofWorkflow);
////		return (float) start.size() / numofWorkflow;
//	}

	public HashSet<Node> getActivities() {
		return activities;
	}

	public void setActivities(HashSet<Node> activities) {
		this.activities = activities;
	}

	public HashMap<String, Integer> getAppearCMap() {
		return appearCMap;
	}

	public void setAppearCMap(HashMap<String, Integer> appearCMap) {
		this.appearCMap = appearCMap;
	}

	public HashMap<String, Float> getC() {
		return C;
	}

	public void setC(HashMap<String, Float> c) {
		C = c;
	}
}
