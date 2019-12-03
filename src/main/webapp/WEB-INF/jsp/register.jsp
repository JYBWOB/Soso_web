<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="service.impl.CardServiceImpl"%>
<%@page import="service.CardService"%>
<%@ page isELIgnored ="false" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统登录</title>
<link rel="stylesheet" type="text/css" href="./CSS/style.css">
</head>

<body>
<%
String errorInfo = (String)request.getAttribute("registerError");
if(errorInfo != null) {
%>
<script type="text/javascript" language="javascript">
alert("<%=errorInfo%>");  

window.location='register'; 
</script>
<%
}
%>
<div class="Login-box">

	<form method="post" action="/Soso_web/RegisterCtrl">
		<h1>用户注册</h1>
		<br/>
		<h2>选择手机号</h2>
		<br/>
		
		<%
			String[] cardNumbers = (String [])request.getAttribute("numbers");
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					String str = String.format(
							"<input type=\"radio\" name=\"phone\" value=\"%s\"/><font size=\"4\">%s</font>",
							cardNumbers[3 * i + j], cardNumbers[3 * i + j]
							);
					out.print(str);
				}
				out.println("<br/>");
			}
		%>
		<br/>
		<h2>套餐选择</h2>
		<br/>
		<input type="radio" name="pack" value="0" /><font size="4">话唠套餐</font>
		<input type="radio" name="pack" value="1" /><font size="4">网虫套餐</font>
		<input type="radio" name="pack" value="2" /><font size="4">超人套餐</font>
		
		<div class="name">
				<label>姓 名：</label>
				<input type="text" name="userName" id="" autocomplete="off" />
		</div>
		<div class="pwd">
				<label>密  码：</label>
				<input type="password" name="password" maxlength="16" id=""/>
		</div>
		<div class="name">
				<label>预 存：</label>
				<input type="text" name="money" id="" autocomplete="off"/>
		</div>
		<br/>
		<div class="Register">
			<button type="submit">注册</button>
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
