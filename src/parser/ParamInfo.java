package parser;

import java.util.ArrayList;

public class ParamInfo {
	private String name;	//参数名
	private String type;	//参数数据类型
	private String value;	//参数值	
	private boolean isSimpleType;//是否是简单类型	
	//private boolean isOptional;//是否是可选填的参数
	//private boolean isUnbounded;//是否是无限出现次数
	private int minOccurs;//最小出现次数
	private int maxOccurs;//最多出现次数
	private ArrayList<ParamInfo> paramList;	//节点列表,第一个ParamInfo对象的参数名为类名
		
	public ParamInfo(String name) {
		super();
		this.name = name;
		this.isSimpleType = true;
		this.paramList = null;
	}
	
	public ParamInfo(String name, String type) {
		super();
		this.name = name;
		this.type = type;
		this.isSimpleType = true;
		this.paramList = null;
	}

	public ParamInfo(String name, String value, String type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		this.isSimpleType = true;
		this.paramList = null;
	}
	
	public ParamInfo(String name, String type, boolean isOptional) {
		super();
		this.name = name;
		this.type = type;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ArrayList<ParamInfo> getParamList() {
		return paramList;
	}

	public void setParamList(ArrayList<ParamInfo> paramList) {
		this.paramList = paramList;
	}

	public boolean isSimpleType() {
		if (this.type==null){
			return isSimpleType;
		}
		if (this.type.toLowerCase().equals("int")
				|| this.type.toLowerCase().equals("integer")
				|| this.type.toLowerCase().equals("string")
				|| this.type.toLowerCase().equals("float")
				|| this.type.toLowerCase().equals("double")
				|| this.type.toLowerCase().equals("char")
				|| this.type.toLowerCase().equals("long")
				|| this.type.toLowerCase().equals("boolean"))
			return true;
		else
			return false;
	}

	public void setSimpleType(boolean isSimpleType) {
		this.isSimpleType = isSimpleType;
	}

	public boolean isOptional() {		
		return (this.minOccurs == 0?true:false);
	}
	
	public boolean isUnbounded() {
		return (this.maxOccurs == -1?true:false);
	}

	public int getMaxOccurs() {
		return maxOccurs;
	}

	public void setMaxOccurs(int maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	public int getMinOccurs() {
		return minOccurs;
	}

	public void setMinOccurs(int minOccurs) {
		this.minOccurs = minOccurs;
	}
	
}
