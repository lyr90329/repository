package WSDL_Structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Complextype_Object {

	private WSDL_ParameterInfo objectN;
	private List objectA = new ArrayList();

	public void setObjectName(WSDL_ParameterInfo objectN) {
		this.objectN = objectN;
	}

	public WSDL_ParameterInfo getObjectName() {
		return objectN;
	}

	public void addObjectA(WSDL_ParameterInfo parameter) {
		objectN.addChildren(parameter);
	}

	public void addObjectH() {
		this.objectA.add(objectN);
	}

	public List getObjectA() {
		return objectA;
	}

	public void findObject(String name, String type) {
		boolean exist = false;
		Iterator inputv = objectA.iterator();

		while (inputv.hasNext()) {
			WSDL_ParameterInfo p = new WSDL_ParameterInfo();

			p = (WSDL_ParameterInfo) inputv.next();
			System.out.println(p.getName());
			if (p.getName().equals(name)) {
				p.setType(type);
				p.setKind(type);
				exist = true;
				break;
			} else {
				exist = FindP(p, exist, name, type);
				if (exist == true) {
					break;
				}
			}
		}
		System.out.println("******" + exist);
	}

	public boolean FindP(WSDL_ParameterInfo p, boolean exist, String name,
			String type) {
		List list0 = p.getList();
		// System.out.println(list0.toString());
		Iterator in = list0.iterator();
		while (in.hasNext()) {
			WSDL_ParameterInfo p2 = new WSDL_ParameterInfo();

			p2 = (WSDL_ParameterInfo) in.next();
			// System.out.println(">>>>"+p2.getName());
			if (p2.getName().equals(name)) {
				System.out.println(">>>>" + p2.getName());
				p2.setType(type);
				exist = true;
				break;
			} else {
				exist = FindP(p2, exist, name, type);
				if (exist == true) {
					break;
				}
			}
		}
		System.out.println("******" + exist + "******");
		return exist;
	}
}
