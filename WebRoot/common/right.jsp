<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="right_div">

	<div>
		<table width="100%">
			<tr>
				<td align="center"><a href="http://192.168.3.229:8080/iwsrecommender/iwslist/home.jsp"><img src=img/iwslist_logo.png width=180 height=40 border=1></a></td>
			</tr>
					
		</table>
	</div>

	<div class="right_one">
		<div class="side_logo"><strong>Login</strong></div>
		<div class="side_content">
			<s:if test="#session.userName==null">
				<div class="login">
					<form action="user/LoginUser.action" method="post">
						<div>Email:</div>
						<div><input type="text" name="userName" size="15"/></div>
						<div>Password:</div>
						<div><input type="password" name="password" size="15"/></div>
						<div><input type="image" src="img/login_button.gif"> or <a href="user/createAccount.jsp"> Sign Up</a></div>
					</form>
				</div>
			</s:if>
			<s:else>
				<div class="login">
					<div>Welcome <s:property value="#session.userName" /></div>
					<div><a href="user/GetUserInfo.action">My Account</a></div>
					<div><a href="user/LogoutUser.action">logout</a></div>
				</div>
			</s:else>
		</div>
	</div>
	<div class="right_two">
		<div class="side_logo"><strong>Tags</strong></div>
		<div class="side_content">
			<div class="tag_cloud"></div>
		</div>
	</div>
</div>