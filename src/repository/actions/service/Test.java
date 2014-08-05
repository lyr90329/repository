package repository.actions.service;

import com.opensymphony.xwork2.ActionSupport;

public class Test extends ActionSupport {
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(name);
		return SUCCESS;
	}
}
