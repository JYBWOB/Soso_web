package web;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import entity.ConsumInfo;
import entity.MD5;
import entity.MobileCard;
import service.CardService;

@Controller
@RequestMapping("/")
public class ActionController {
	@Autowired
	CardService cardService;

	// 访问注册页面，
	@RequestMapping("/register")
	private String userRegister(Model model, HttpServletRequest request) {
		try {
			// 需生成九个未使用的号码
			String[] numbers = cardService.getNewNumbers(9);
			request.setAttribute("numbers", numbers);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "register";
	}
	
	@RequestMapping(value = "/RegisterCtrl", method = RequestMethod.POST)
	private String registerCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String phone = request.getParameter("phone");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String pack = request.getParameter("pack");
		String money = request.getParameter("money");
		// 输入信息不能为空
		if(
			phone == null ||
			userName.equals("") ||
			password.equals("") ||
			pack == null ||
			money.equals("")
		) {
			request.setAttribute("registerError", "请填写完整信息"); 
			return "forward:register";
		}
		
		// 正则表达式匹配，预存话费只能是数字
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); 
        if(!pattern.matcher(money).matches()) {
        	request.setAttribute("registerError", "预存话费只允许输入数字"); 
			return "forward:register";
        }
		
		MobileCard mc = new MobileCard(
					phone, userName, MD5.getMD5(password), 
					Integer.valueOf(pack), Double.valueOf(money)
				);
		
		// 话费需高于套餐费用
		if(Double.valueOf(money) < mc.getPackage().curPrice) {
			request.setAttribute("registerError", "预存话费不足支付当前套餐费用"); 
			return "forward:register";
		}
		
		cardService.addCard(mc);
		String str = "注册成功：" + phone;
		// 给页面提示信息
		request.setAttribute("registerSucceed", str);
		// 登录界面自动输入号码
		request.setAttribute("defaultNumber", phone);
		
		// 转发请求
		return "forward:index";
	}
	
	@RequestMapping(value = "/LoginCtrl", method = RequestMethod.POST)
	private String loginCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession s = request.getSession(true);
		
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		try {
			// 登录判断
			if(!cardService.isExist(phone, password)) {
				// 标记登录错误
				request.setAttribute("state", -1);
				return "forward:index";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MobileCard mc = null;
		try {
			mc = cardService.getCardByNumber(phone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 号码和对应卡对象 写入session会话
		s.setAttribute("curCard", mc);
		s.setAttribute("phone", phone);
		// 重定向
		return "redirect:main/main";
	}
	
	// 使用Soso
	@RequestMapping(value = "/UseCtrl", method = RequestMethod.POST)
	private String useCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession s = request.getSession();
		String phone = (String)s.getAttribute("phone");
		String str = cardService.useSoso(phone);
		// 使用后需更新当前页面卡对象
		MobileCard mc = null;
		try {
			mc = cardService.getCardByNumber((String)s.getAttribute("phone"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.setAttribute("curCard", mc);
		// 给出使用Soso的结果信息：使用信息/错误信息
		request.setAttribute("UseInfo", str.replace("\n", ","));
		// 转发请求
		return "forward:main/information";
	}
	
	// 充值
	@RequestMapping(value = "/ChargeCtrl", method = RequestMethod.POST)
	private String chargeCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String phone = request.getParameter("phone");
		
		// 判断充值数量为数字
		String moneyStr = (String)request.getParameter("money");
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); 
        if(!pattern.matcher(moneyStr).matches()) {
        	request.setAttribute("chargeError", "充值话费只允许输入数字"); 
			return "forward:main/charge";
        }
		
		double money = Double.valueOf(request.getParameter("money"));
		
		try {
			if(!cardService.chargeMoney(phone, money)) {
				// 手机号不存在
				request.setAttribute("chargeError", "手机号不存在"); 
				return "forward:main/charge";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpSession s = request.getSession(true);
		// 更新会话中的卡对象
		MobileCard mc = null;
		try {
			mc = cardService.getCardByNumber((String)s.getAttribute("phone"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.setAttribute("curCard", mc);
		// 充值信息
		request.setAttribute("chargeError", "充值成功"); 
		return "forward:main/charge";
	}
	
	// 变更套餐
	@RequestMapping(value = "/ChangeCtrl", method = RequestMethod.POST)
	private String changeCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		int pack = Integer.valueOf(request.getParameter("pack"));
		HttpSession s = request.getSession(true);
		String phone = (String)s.getAttribute("phone");

		String info = cardService.changingPack(phone, pack);
		
		// 更新会话中卡对象
		MobileCard mc = null;
		try {
			mc = cardService.getCardByNumber(phone);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.setAttribute("curCard", mc);
		request.setAttribute("changeInfo", info); 
		return "forward:main/changePack";
	}
	
	// 办理退网
	@RequestMapping(value = "/CheckoutCtrl", method = RequestMethod.POST)
	private String checkoutCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession s = request.getSession();
		String phone = (String)s.getAttribute("phone");
		cardService.delCardByNumber(phone);
		request.setAttribute("delInfo", "办理退网成功"); 
		return "forward:index";
	}
	
	// 消费详单
	@RequestMapping(value = "/consumeCtrl")
	private String consumeCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession s = request.getSession();
		
		String number = (String)s.getAttribute("phone");
	    StringBuffer sb = new StringBuffer();
	    sb.append("卡号：" + number + "的消费记录：\n");
	    sb.append("序号" + "      " + "类型" + "      " + "数据\n");
	    // 获取消费信息
        List<ConsumInfo> infos = cardService.getConsumeInfo(number);
        int index = 1;
		for(ConsumInfo info : infos) {
		    sb.append(index++ + "             " + info.type + "      " + info.consumData);
		    if(info.type.equals("通话"))
		    	sb.append("分\n");
		    else if(info.type.equals("短信"))
		    	sb.append("条\n");
		    else sb.append("MB\n");
		}
		request.setAttribute("conInfo", sb.toString().replace("\n", "<br/>")); 
		return "forward:main/consumeDetails";
	}
}
