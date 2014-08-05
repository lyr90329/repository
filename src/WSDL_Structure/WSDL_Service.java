package WSDL_Structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.exolab.castor.xml.schema.Schema;

public class WSDL_Service {

	/**服务名*/
	private String name;
	/** WSDL文件位置 */
	private String wsdllocation;
	private String endpoint;
	private String targetnamespace;
	private String ns;
	private Schema wsdlType;
	/** The list of operations that this service defines. */
	public List operations = new ArrayList();

	public Schema getWsdlType() {
		return wsdlType;
	}

	public void setWsdlTypes(Schema wsdlType) {
		this.wsdlType = wsdlType;
	}

	public List getOperation() {
		return operations;
	}

	public Iterator getOperations() {
		return operations.iterator();
	}

	public void addOperation(WSDL_Operations operation) {
		operations.add(operation);
	}

	public String toString() {
		return getName();
	}

	public String getTargetnamespace() {
		return targetnamespace;
	}

	public void setTargetnamespace(String targetnamespace) {
		this.targetnamespace = targetnamespace;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getWsdllocation() {
		return wsdllocation;
	}

	public void setWsdllocation(String wsdllocation) {
		this.wsdllocation = wsdllocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNS() {
		return ns;
	}

	public void setNS(String ns) {
		this.ns = ns;
	}

	public void setInChildren(List children) {
		this.operations = children;
	}

	public void setOutChildren(List children) {
		this.operations = children;
	}

	public List getInChildren() {
		// TODO Auto-generated method stub
		return operations;
	}

	public List getOutChildren() {
		// TODO Auto-generated method stub
		return operations;
	}
}
