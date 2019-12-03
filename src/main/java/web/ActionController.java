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

	// ����ע��ҳ�棬
	@RequestMapping("/register")
	private String userRegister(Model model, HttpServletRequest request) {
		try {
			// �����ɾŸ�δʹ�õĺ���
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
		// ������Ϣ����Ϊ��
		if(
			phone == null ||
			userName.equals("") ||
			password.equals("") ||
			pack == null ||
			money.equals("")
		) {
			request.setAttribute("registerError", "����д������Ϣ"); 
			return "forward:register";
		}
		
		// ������ʽƥ�䣬Ԥ�滰��ֻ��������
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); 
        if(!pattern.matcher(money).matches()) {
        	request.setAttribute("registerError", "Ԥ�滰��ֻ������������"); 
			return "forward:register";
        }
		
		MobileCard mc = new MobileCard(
					phone, userName, MD5.getMD5(password), 
					Integer.valueOf(pack), Double.valueOf(money)
				);
		
		// ����������ײͷ���
		if(Double.valueOf(money) < mc.getPackage().curPrice) {
			request.setAttribute("registerError", "Ԥ�滰�Ѳ���֧����ǰ�ײͷ���"); 
			return "forward:register";
		}
		
		cardService.addCard(mc);
		String str = "ע��ɹ���" + phone;
		// ��ҳ����ʾ��Ϣ
		request.setAttribute("registerSucceed", str);
		// ��¼�����Զ��������
		request.setAttribute("defaultNumber", phone);
		
		// ת������
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
			// ��¼�ж�
			if(!cardService.isExist(phone, password)) {
				// ��ǵ�¼����
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
		// ����Ͷ�Ӧ������ д��session�Ự
		s.setAttribute("curCard", mc);
		s.setAttribute("phone", phone);
		// �ض���
		return "redirect:main/main";
	}
	
	// ʹ��Soso
	@RequestMapping(value = "/UseCtrl", method = RequestMethod.POST)
	private String useCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession s = request.getSession();
		String phone = (String)s.getAttribute("phone");
		String str = cardService.useSoso(phone);
		// ʹ�ú�����µ�ǰҳ�濨����
		MobileCard mc = null;
		try {
			mc = cardService.getCardByNumber((String)s.getAttribute("phone"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.setAttribute("curCard", mc);
		// ����ʹ��Soso�Ľ����Ϣ��ʹ����Ϣ/������Ϣ
		request.setAttribute("UseInfo", str.replace("\n", ","));
		// ת������
		return "forward:main/information";
	}
	
	// ��ֵ
	@RequestMapping(value = "/ChargeCtrl", method = RequestMethod.POST)
	private String chargeCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String phone = request.getParameter("phone");
		
		// �жϳ�ֵ����Ϊ����
		String moneyStr = (String)request.getParameter("money");
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); 
        if(!pattern.matcher(moneyStr).matches()) {
        	request.setAttribute("chargeError", "��ֵ����ֻ������������"); 
			return "forward:main/charge";
        }
		
		double money = Double.valueOf(request.getParameter("money"));
		
		try {
			if(!cardService.chargeMoney(phone, money)) {
				// �ֻ��Ų�����
				request.setAttribute("chargeError", "�ֻ��Ų�����"); 
				return "forward:main/charge";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpSession s = request.getSession(true);
		// ���»Ự�еĿ�����
		MobileCard mc = null;
		try {
			mc = cardService.getCardByNumber((String)s.getAttribute("phone"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.setAttribute("curCard", mc);
		// ��ֵ��Ϣ
		request.setAttribute("chargeError", "��ֵ�ɹ�"); 
		return "forward:main/charge";
	}
	
	// ����ײ�
	@RequestMapping(value = "/ChangeCtrl", method = RequestMethod.POST)
	private String changeCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		int pack = Integer.valueOf(request.getParameter("pack"));
		HttpSession s = request.getSession(true);
		String phone = (String)s.getAttribute("phone");

		String info = cardService.changingPack(phone, pack);
		
		// ���»Ự�п�����
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
	
	// ��������
	@RequestMapping(value = "/CheckoutCtrl", method = RequestMethod.POST)
	private String checkoutCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession s = request.getSession();
		String phone = (String)s.getAttribute("phone");
		cardService.delCardByNumber(phone);
		request.setAttribute("delInfo", "���������ɹ�"); 
		return "forward:index";
	}
	
	// �����굥
	@RequestMapping(value = "/consumeCtrl")
	private String consumeCtrl(Model model, HttpServletRequest request
			, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HttpSession s = request.getSession();
		
		String number = (String)s.getAttribute("phone");
	    StringBuffer sb = new StringBuffer();
	    sb.append("���ţ�" + number + "�����Ѽ�¼��\n");
	    sb.append("���" + "      " + "����" + "      " + "����\n");
	    // ��ȡ������Ϣ
        List<ConsumInfo> infos = cardService.getConsumeInfo(number);
        int index = 1;
		for(ConsumInfo info : infos) {
		    sb.append(index++ + "             " + info.type + "      " + info.consumData);
		    if(info.type.equals("ͨ��"))
		    	sb.append("��\n");
		    else if(info.type.equals("����"))
		    	sb.append("��\n");
		    else sb.append("MB\n");
		}
		request.setAttribute("conInfo", sb.toString().replace("\n", "<br/>")); 
		return "forward:main/consumeDetails";
	}
}
