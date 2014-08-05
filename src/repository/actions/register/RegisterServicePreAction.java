package repository.actions.register;

import com.opensymphony.xwork2.ActionContext;

import repository.actions.BaseAction;

public class RegisterServicePreAction extends BaseAction {
	public String execute() throws Exception {
		ActionContext ctx = ActionContext.getContext();
		String userName = (String) ctx.getSession().get("userName");
		if (userName == null || userName.equals("")) {
			return LOGIN;
		}
		return SUCCESS;
	}
}
