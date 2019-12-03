<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="entity.MobileCard" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>

<%
String errorInfo = (String)request.getAttribute("chargeError");
if(errorInfo != null) {
%>
<script type="text/javascript" language="javascript">
alert("<%=errorInfo%>");  

window.location='./main/charge'; 
</script>
<%
}
%>
<div class="Login-box">
	<form action="/Soso_web/ChargeCtrl" method="post">
	    <div class="name">
			<label>手机号：</label>
			<input type="text" name="phone" id="" autocomplete="off" value="<%=(String)session.getAttribute("phone")%>" />
		</div>
		<div class="name">
			<label>充值费用</label>
			<input type="text" name="money" id="" autocomplete="off"/>
		</div>
		<br/>
		<div class="Register">
			<button type="submit">充值</button>
		</div>
	</form>
</div>
</body>
</html>
