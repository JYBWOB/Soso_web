package service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CardDao;
import dao.ConsumeDao;
import entity.ConsumInfo;
import entity.MD5;
import entity.MobileCard;
import entity.NetPackage;
import entity.Scene;
import entity.SuperPackage;
import entity.TalkPackage;
import service.CardService;

@Service
public class CardServiceImpl implements CardService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CardDao cardDao;
	
	@Autowired
	private ConsumeDao consumeDao;
	
	private Scene[] scenes;
	{
		scenes = new Scene[6];
		for(int i = 0; i < 6; i++)
    		scenes[i] = new Scene();
        scenes[0].type = "通话"; scenes[0].description = "问候客户，谁知其如此难缠  通话90分钟\n";scenes[0].data = 90;
        scenes[1].type = "短信"; scenes[1].description = "通知朋友手机换号，发送短信50条\n";scenes[1].data = 50;
        scenes[2].type = "短信"; scenes[2].description = "参与环境保护实施方案问卷调查 发送短信5条\n";scenes[2].data = 5;
        scenes[3].type = "通话"; scenes[3].description = "给家长打电话，通话50分钟\n";scenes[3].data = 50;
        scenes[4].type = "上网"; scenes[4].description = "看一部电影，流量消耗1GB\n";scenes[4].data = 1024;
        scenes[5].type = "上网"; scenes[5].description = "晚上看游戏直播，流量消耗3GB\n";scenes[5].data = 3 * 1024;
	}
	
	@Override
	public MobileCard getCardByNumber(String number) throws SQLException {
		return cardDao.queryByNumber(number);
	}
	
	@Override
	public boolean chargeMoney(String number, double money) throws SQLException {
        if (!this.isExist(number))
            return false;
		MobileCard mc = this.getCardByNumber(number);
        mc.money += money;
        cardDao.updateCard(mc);
        return true;
	}
	
	@Override
	public String changingPack(String number, int packNum) {
		MobileCard cd = null;
		try {
			cd = this.getCardByNumber(number);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // TalkPackage ----- 0
        // NetPackage  ----- 1
        // Superpackage----- 2
        //变更套餐要初始化实际使用套餐余量为0，并在话费中扣除新套餐费用
        switch (packNum) {
            case 0:
                if (cd.serPackage == 0)
                    return "您已经是该套餐用户，无需换套餐！";
                if(cd.money < TalkPackage.price)
                    return "余额不足以支付新套餐当月费用，请充值后变更套餐";
                cd.realFlow = 0;
                cd.realSMSCount = 0;
                cd.realTalkTime = 0;
                cd.serPackage = packNum;
                cd.money = cd.money - cd.getPackage().curPrice;
                cardDao.updateCard(cd);
                return "套餐变更成功!";
            case 1:
                if (cd.serPackage == 1)
                    return "您已经是该套餐用户，无需换套餐！";
                if(cd.money < NetPackage.price)
                    return "余额不足以支付新套餐当月费用，请充值后变更套餐";
                cd.realFlow = 0;
                cd.realSMSCount = 0;
                cd.realTalkTime = 0;
                cd.serPackage = packNum;
                cd.money = cd.money - cd.getPackage().curPrice;
                cardDao.updateCard(cd);
                return "套餐变更成功!";
            case 2:
                if (cd.serPackage == 2)
                    return "您已经是该套餐用户，无需换套餐！";
                if(cd.money < SuperPackage.price)
                    return "余额不足以支付新套餐当月费用，请充值后变更套餐";
                cd.realFlow = 0;
                cd.realSMSCount = 0;
                cd.realTalkTime = 0;
                cd.serPackage = packNum;
                cd.money = cd.money - cd.getPackage().curPrice;
                cardDao.updateCard(cd);
                return "套餐变更成功!";
            default:
                return null;
        }
	}

	@Override
	public void delCardByNumber(String number) {
		// TODO Auto-generated method stub
		cardDao.delByNumber(number);
	}

	@Override
	public boolean isExist(String number) throws SQLException {
		// TODO Auto-generated method stub
		MobileCard mc = cardDao.queryByNumber(number);
		return (mc != null);
	}

	@Override
	public boolean isExist(String number, String password) throws SQLException {
		// TODO Auto-generated method stub
		MobileCard mc = cardDao.query(number, MD5.getMD5(password));
		return (mc != null);
	}

	@Override
	public void addCard(MobileCard mc) {
		// TODO Auto-generated method stub
		cardDao.insert(mc);
	}

	@Override
	public String[] getNewNumbers(int number) throws SQLException {
		// TODO Auto-generated method stub
		String[] numbers = new String[number];
		for(int i = 0; i < number; i++) {
			numbers[i] = new String(this.createNumber());
			if(this.isExist(numbers[i])) 
				i--;
		}
		return numbers;
	}
	
    public String createNumber() {
        Random rd = new Random();
        while (true) {
            StringBuffer number = new StringBuffer();
            number.append("139");
            for(int i = 0; i < 8; i++) {
            	number.append(String.valueOf(rd.nextInt(10)));
            }
            return number.toString();
        }
    }
    
    @Override
    public List<ConsumInfo> getConsumeInfo(String number) {
    	List<ConsumInfo> infos = consumeDao.queryByNumber(number);
    	return consumeDao.queryByNumber(number);
    }

	@Override
	public String useSoso(String number) {
		MobileCard card = null;
		try {
			card = this.getCardByNumber(number);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(card.money == 0) {
        	// 如果停机直接提示退出
        	return "没有话费，已停机，请充值";
        }
        
        // 随机数用于随机进入消费场景
        Random rd = new Random();
        if (card.serPackage == 1) {
            int index = 0;
            // 随机进入消费场景
            while (true) {
                index = rd.nextInt(6);
                if (!scenes[index].type.equals("上网"))
                    continue;
                break;
            }
            ConsumInfo ci = new ConsumInfo();
            ci.type = scenes[index].type;
            ci.cardNumber = number;
            ci.consumData = scenes[index].data;
            consumeDao.insert(ci);
            NetPackage ns = (NetPackage)card.getPackage();
            int result = ns.netPlay(scenes[index].data, card);
            
            cardDao.updateCard(card);
            // 套餐余量充足
            if (result == 0) {
                return scenes[index].description;
            }
            // 套餐余量不足，但剩余话费充足
            else if (result >0) {
                String str = scenes[index].description + "但超过套餐余量，话费扣除" + result/10.0 + "元\n";
                return str;
            }
            // 套餐余量不足， 剩余话费也不足
            else if (result < 0) {
                String str = scenes[index].description + "但超过套餐余量，话费已用完,请充值\n";
                return str;
            }
        }
        else if (card.serPackage == 0) {
            int index = 0;
            while (true) {
                index = rd.nextInt(6);
                if (scenes[index].type.equals("上网"))
                    continue;
                break;
            }
            ConsumInfo ci = new ConsumInfo();
            ci.type = scenes[index].type;
            ci.cardNumber = number;
            ci.consumData = scenes[index].data;
            consumeDao.insert(ci);
            double result;
            if (scenes[index].type.equals("短信")) {
                TalkPackage ss = (TalkPackage)card.getPackage();
                result = ss.send(scenes[index].data, card)*0.1;
            }
            else {
            	TalkPackage cs = (TalkPackage)card.getPackage();;
                result = cs.call(scenes[index].data, card)*0.2;
            }
            cardDao.updateCard(card);
            if (result == 0) {
                return scenes[index].description;
            }
            else if (result >0) {
                String str = scenes[index].description + "但超过套餐余量，话费扣除" + result + "元\n";
                return str;
            }
            else if (result < 0) {
                String str = scenes[index].description + "但超过套餐余量，话费已用完,请充值\n";
                return str;
            }
        }
        else if (card.serPackage == 2) {
            int index = 0;
            index = rd.nextInt(6);
            ConsumInfo ci = new ConsumInfo();
            ci.type = scenes[index].type;
            ci.cardNumber = number;
            ci.consumData = scenes[index].data;
            consumeDao.insert(ci);
            double result;
            if (scenes[index].type.equals("短信")) {
            	SuperPackage ss = (SuperPackage)card.getPackage();;
                result = ss.send(scenes[index].data, card)*0.1;
            }
            else if (scenes[index].type.equals("通话")) {
            	SuperPackage cs = (SuperPackage)card.getPackage();;
                result = cs.call(scenes[index].data, card)*0.2;
            }
            else {
            	SuperPackage nt = (SuperPackage)card.getPackage();
                result = nt.netPlay(scenes[index].data, card)*0.1;
            }
            
            cardDao.updateCard(card);
            if (result == 0) {
                return scenes[index].description;
            }
            else if (result >0) {
                String str = scenes[index].description + "但超过套餐余量，话费扣除" + result + "元\n";
                return str;
            }
            else if (result < 0) {
                String str = scenes[index].description + "但超过套餐余量，话费已用完,请充值\n";
                return str;
            }
        }
        cardDao.updateCard(card);
		return "";
	}
	
}
