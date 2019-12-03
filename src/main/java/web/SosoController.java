package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SosoController {
	
	@RequestMapping("/")
	private String userLogin(Model model) {
		return "index";
	}
	
	@RequestMapping("/index")
	private String userIndex(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/main/main")
	private String userMain(Model model) {
		return "main/main";
	}
	
	@RequestMapping(value = "/main/left")
	private String leftMain(Model model) {
		return "main/left";
	}
	
	@RequestMapping(value = "/main/top")
	private String topMain(Model model) {
		return "main/top";
	}
	
	@RequestMapping(value = "/main/information")
	private String infoMain(Model model) {
		return "main/information";
	}
	
	@RequestMapping(value = "/main/queryBill")
	private String billMain(Model model) {
		return "main/queryBill";
	}
	
	@RequestMapping(value = "/main/packRest")
	private String restMain(Model model) {
		return "main/packRest";
	}
	
	@RequestMapping(value = "/main/consumeDetails")
	private String detailMain(Model model) {
		return "main/consumeDetails";
	}
	
	@RequestMapping(value = "/main/charge")
	private String chargeMain(Model model) {
		return "main/charge";
	}
	
	@RequestMapping(value = "/main/changePack")
	private String changeMain(Model model) {
		return "main/changePack";
	}
}
