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
String changeInfo = (String)request.getAttribute("changeInfo");
if(changeInfo != null) {
%>
<script type="text/javascript" language="javascript">
alert("<%=changeInfo%>");  

window.location='./main/changePack'; 
</script>
<%
}
%>

<%
	MobileCard mc = (MobileCard)session.getAttribute("curCard");
	int index = mc.serPackage;
	String[] str = new String[3];
	str[0] = str[1] = str[2] = "";
	str[index] = "checked";
%>
<div class="Login-box">
	<form action="/Soso_web/ChangeCtrl" method="post">
	    <input type="radio" name="pack" value="0"  <%=str[0] %>/><font size="4">话唠套餐</font>
		<input type="radio" name="pack" value="1" <%=str[1] %> /><font size="4">网虫套餐</font>
		<input type="radio" name="pack" value="2" <%=str[2] %> /><font size="4">超人套餐</font>
		<br/>
		<br/>
		<br/>
		<div class="Register">
			<button type="submit">变更</button>
		</div>
	</form>
</div>
</body>
</html>
