<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../CSS/style2.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>菜单列表</div>
    
    <dl class="leftmenu">
    <dd>
    	<ul class="menuson">
        <li><cite></cite><a href="information" target="rightFrame">个人信息</a><i></i></li>
        <li><cite></cite><a href="queryBill" target="rightFrame">查询账单</a><i></i></li>
        <li><cite></cite><a href="packRest" target="rightFrame">套餐余量</a><i></i></li>
        <li><cite></cite><a href="/Soso_web/consumeCtrl" target="rightFrame">消费详单</a><i></i></li>
        <li><cite></cite><a href="charge" target="rightFrame">充值缴费</a><i></i></li>
        <li><cite></cite><a href="changePack" target="rightFrame">变更套餐</a><i></i></li>
        </ul>    
    </dd>
    </dl>
</body>
</html>
