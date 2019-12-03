<%@page import="java.text.DecimalFormat"%>
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
    	MobileCard card = (MobileCard)session.getAttribute("curCard");
    
	    StringBuffer result = new StringBuffer();
	    DecimalFormat format = new DecimalFormat("#.0");
	 
	    result.append("您的卡号：" + card.cardNumber + "\n当月账单信息：\n      ");
	    result.append("套餐资费：" + format.format(card.getPackage().curPrice) + "元\n");
	    result.append("合计：" + format.format(card.consumAmount) + "元\n");
	    result.append("账号余额：" + format.format(card.money) + "元\n");
	    
    	out.print("<h3>");
	    out.print(result.toString().replace("\n", "<br/>"));
    	out.print("</h3>");
    %>
</body>
</html>
