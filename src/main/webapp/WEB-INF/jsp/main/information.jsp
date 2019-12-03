<%@page import="entity.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>

 <%
 	MobileCard mc = (MobileCard)session.getAttribute("curCard");
 %>
 
<%
String info = (String)request.getAttribute("UseInfo");
if(info != null) {
%>
<script type="text/javascript" language="javascript">
alert("<%=info%>");  

window.location='/Soso_web/main/information'; 
</script>
<%
}
%>
 	<div class="Login-box">
	  	<h3> 手机号：<%=mc.cardNumber%> </h3><br/>
		<h3>  姓名：<%=mc.userName %> </h3> <br/>
		<%
		out.print("<h3>套餐类型：");
		if(mc.serPackage == 0)
			out.print("话唠套餐");
		else if(mc.serPackage == 1)
			out.print("网虫套餐");
		else if(mc.serPackage == 2)
			out.print("超人套餐");
		out.print("</h3><br/>");
		%>
		<h3>话费余额：<%=mc.money %> </h3><br/>
		<form action="/Soso_web/UseCtrl">
			<button type="submit" formmethod="post">使用</button>
		</form>
		<br/>
		<form action="/Soso_web/CheckoutCtrl" target="_parent">
			<button type="submit" formmethod="post">退网</button>
		</form>
	</div>
</body>
</html>
