package WSDL_Structure;

import java.util.ArrayList;
import java.util.List;



public class WSDL_ParameterInfo{

	private String name;//参数名
	private String type;//参数类型
	private String value = "";//参数值
	private int id;//参数标识 
    private String serviceid;//服务id
	private String operationname;//操作名
	private String kind;
	private List children = new ArrayList();
	private boolean isSimple = true;
	
	//重构函数
	public WSDL_ParameterInfo(){
		
	}
	public WSDL_ParameterInfo(String string) {
		this.name=string;
	}
	
	//获取、设置参数？？
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	//获取、设置操作名
	public String getOperationname() {
		return operationname;
	}
	public void setOperationname(String operationname) {
		this.operationname = operationname;
	}
	
	//获取、设置服务id
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	
	//获取、设置参数值
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	//获取、设置参数标识
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	
	//获取、设置参数类型
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	//获取、设置参数名
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	/*......????是否需要在这添加添加子节点代码.......*/
	public void addChildren(WSDL_ParameterInfo parameter) {
		this.children.add(parameter);
	}
	
	public List getList() {
		return children;
	}
	
	//获取、设置参数类型（简单类型or复杂类型）
	public void setIsSimple(boolean simple) {
		this.isSimple = simple;
	}
	public boolean getIsSimple() {
		return isSimple;
	}

	//设置输入输出子节点
	public void setInChildren(List children) {
		this.children = children;
	}
	public void setOutChildren(List children) {
		this.children = children;
	}
	
	//获取输入输出子节点
	
	public List getInChildren() {
		return children;
	}

	public List getOutChildren() {
		return children;
	}
}
