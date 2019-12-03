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
        scenes[0].type = "ͨ��"; scenes[0].description = "�ʺ�ͻ���˭֪������Ѳ�  ͨ��90����\n";scenes[0].data = 90;
        scenes[1].type = "����"; scenes[1].description = "֪ͨ�����ֻ����ţ����Ͷ���50��\n";scenes[1].data = 50;
        scenes[2].type = "����"; scenes[2].description = "���뻷������ʵʩ�����ʾ���� ���Ͷ���5��\n";scenes[2].data = 5;
        scenes[3].type = "ͨ��"; scenes[3].description = "���ҳ���绰��ͨ��50����\n";scenes[3].data = 50;
        scenes[4].type = "����"; scenes[4].description = "��һ����Ӱ����������1GB\n";scenes[4].data = 1024;
        scenes[5].type = "����"; scenes[5].description = "���Ͽ���Ϸֱ������������3GB\n";scenes[5].data = 3 * 1024;
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
        //����ײ�Ҫ��ʼ��ʵ��ʹ���ײ�����Ϊ0�����ڻ����п۳����ײͷ���
        switch (packNum) {
            case 0:
                if (cd.serPackage == 0)
                    return "���Ѿ��Ǹ��ײ��û������軻�ײͣ�";
                if(cd.money < TalkPackage.price)
                    return "������֧�����ײ͵��·��ã����ֵ�����ײ�";
                cd.realFlow = 0;
                cd.realSMSCount = 0;
                cd.realTalkTime = 0;
                cd.serPackage = packNum;
                cd.money = cd.money - cd.getPackage().curPrice;
                cardDao.updateCard(cd);
                return "�ײͱ���ɹ�!";
            case 1:
                if (cd.serPackage == 1)
                    return "���Ѿ��Ǹ��ײ��û������軻�ײͣ�";
                if(cd.money < NetPackage.price)
                    return "������֧�����ײ͵��·��ã����ֵ�����ײ�";
                cd.realFlow = 0;
                cd.realSMSCount = 0;
                cd.realTalkTime = 0;
                cd.serPackage = packNum;
                cd.money = cd.money - cd.getPackage().curPrice;
                cardDao.updateCard(cd);
                return "�ײͱ���ɹ�!";
            case 2:
                if (cd.serPackage == 2)
                    return "���Ѿ��Ǹ��ײ��û������軻�ײͣ�";
                if(cd.money < SuperPackage.price)
                    return "������֧�����ײ͵��·��ã����ֵ�����ײ�";
                cd.realFlow = 0;
                cd.realSMSCount = 0;
                cd.realTalkTime = 0;
                cd.serPackage = packNum;
                cd.money = cd.money - cd.getPackage().curPrice;
                cardDao.updateCard(cd);
                return "�ײͱ���ɹ�!";
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
        	// ���ͣ��ֱ����ʾ�˳�
        	return "û�л��ѣ���ͣ�������ֵ";
        }
        
        // �������������������ѳ���
        Random rd = new Random();
        if (card.serPackage == 1) {
            int index = 0;
            // ����������ѳ���
            while (true) {
                index = rd.nextInt(6);
                if (!scenes[index].type.equals("����"))
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
            // �ײ���������
            if (result == 0) {
                return scenes[index].description;
            }
            // �ײ��������㣬��ʣ�໰�ѳ���
            else if (result >0) {
                String str = scenes[index].description + "�������ײ����������ѿ۳�" + result/10.0 + "Ԫ\n";
                return str;
            }
            // �ײ��������㣬 ʣ�໰��Ҳ����
            else if (result < 0) {
                String str = scenes[index].description + "�������ײ�����������������,���ֵ\n";
                return str;
            }
        }
        else if (card.serPackage == 0) {
            int index = 0;
            while (true) {
                index = rd.nextInt(6);
                if (scenes[index].type.equals("����"))
                    continue;
                break;
            }
            ConsumInfo ci = new ConsumInfo();
            ci.type = scenes[index].type;
            ci.cardNumber = number;
            ci.consumData = scenes[index].data;
            consumeDao.insert(ci);
            double result;
            if (scenes[index].type.equals("����")) {
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
                String str = scenes[index].description + "�������ײ����������ѿ۳�" + result + "Ԫ\n";
                return str;
            }
            else if (result < 0) {
                String str = scenes[index].description + "�������ײ�����������������,���ֵ\n";
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
            if (scenes[index].type.equals("����")) {
            	SuperPackage ss = (SuperPackage)card.getPackage();;
                result = ss.send(scenes[index].data, card)*0.1;
            }
            else if (scenes[index].type.equals("ͨ��")) {
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
                String str = scenes[index].description + "�������ײ����������ѿ۳�" + result + "Ԫ\n";
                return str;
            }
            else if (result < 0) {
                String str = scenes[index].description + "�������ײ�����������������,���ֵ\n";
                return str;
            }
        }
        cardDao.updateCard(card);
		return "";
	}
	
}
