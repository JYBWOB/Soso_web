<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="entity.MobileCard" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
    <% 
    	String result = (String)request.getAttribute("conInfo");
    	out.print("<h3>");
    	out.print(result);
    	out.print("</h3>");
    %>
</body>
</html>
