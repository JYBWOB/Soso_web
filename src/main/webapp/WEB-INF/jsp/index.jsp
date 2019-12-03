<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页面</title>
<link rel="stylesheet" type="text/css" href="./CSS/style.css">
</head>
<body>

<%
String info = (String)request.getAttribute("registerSucceed");
if(info != null) {
%>
<script type="text/javascript" language="javascript">
alert("<%=info%>");  

// window.location='index'; 
</script>
<%
}
%>

<%
info = (String)request.getAttribute("delInfo");
if(info != null) {
%>
<script type="text/javascript" language="javascript">
alert("<%=info%>");  

window.location='index'; 

</script>
<%
session.invalidate();
}
%>
<div class="Login-box">
	<h1>Soso业务大厅</h1>
	<form action="/Soso_web/LoginCtrl">
		<%
			int state = 0;
			try {
				state = (int)request.getAttribute("state");
			}
			catch(Exception e) {
				;
			}
			if(state == -1)
				out.println("<br/><font size=\"2\" color=\"red\">手机号或密码输入错误</font>");
		%>
		<div class="name">
			<label>手机号：</label>
			<input type="text" name="phone" id=""  value="<% 
				String number = (String)request.getAttribute("defaultNumber");
				if(number == null)
					out.print("");
				else
					out.print(number.replace(" ", ""));
			%>" />
		</div>
		<div class="pwd">
			<label>密码：</label>
			<input type="password" name="password" maxlength="16" id=""/>
		</div>
		<br/>
		<br/>
		<div class="Login">
			<button type="submit" formmethod="post">登录</button>
			<button type="button" onclick="window.location.href='/Soso_web/register'">注册</button>
		</div>
	</form>
</div>

<div class="screenbg">
	<ul>
		<li><a href="javascript:;"><img src="./Images/background.jpg"></a></li>
	</ul>
</div>

</body>
</html>
