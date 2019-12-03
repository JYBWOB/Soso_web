<%@page import="entity.SuperPackage"%>
<%@page import="entity.TalkPackage"%>
<%@page import="entity.NetPackage"%>
<%@page import="entity.MobileCard"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
    <%
    MobileCard card = (MobileCard)session.getAttribute("curCard");
    
    String result;
    int flow;
    int smsCount;
    int talkCount;
    result = "您的卡号是：" + card.cardNumber + ",\n套餐内剩余信息：\n";
    if (card.serPackage == 1) {
        NetPackage np = new NetPackage();
        if (np.flow - card.realFlow >= 0)
            flow = np.flow - card.realFlow;
        else
            flow = 0;
        result  = result + "上网流量：" + flow / 1024.0 + "GB\n";
    }
    else if (card.serPackage == 0) {
        TalkPackage tp = new TalkPackage();
        if(tp.talkTime - card.realTalkTime >= 0)
            talkCount = tp.talkTime - card.realTalkTime;
        else
            talkCount = 0;
        result  = result + "通话时长：" + talkCount + "分\n";
        if(tp.smsCount - card.realSMSCount >= 0)
            smsCount = tp.smsCount - card.realSMSCount;
        else smsCount = 0;
        result = result + "短信条数：" + smsCount + "条\n";
    }
    else {
        SuperPackage sup = new SuperPackage();
        if (sup.flow - card.realFlow >= 0)
            flow = sup.flow - card.realFlow;
        else
            flow = 0;
        result  = result + "上网流量：" + flow / 1024.0 + "GB\n";
        if(sup.talkTime - card.realTalkTime >= 0)
            talkCount = sup.talkTime - card.realTalkTime;
        else
            talkCount = 0;
        result  = result + "通话时长：" + talkCount + "分\n";
        if(sup.smsCount - card.realSMSCount >= 0)
            smsCount = sup.smsCount - card.realSMSCount;
        else smsCount = 0;
        result = result + "短信条数：" + smsCount + "条\n";
    }    
    
    	out.print("<h3>");
    	out.print(result.replace("\n", "<br/>"));
    	out.print("</h3>");
    %>
</body>
</html>
