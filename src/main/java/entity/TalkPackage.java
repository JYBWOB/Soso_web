package entity;

import service.*;
import entity.MobileCard;

public class TalkPackage extends ServicePackage implements CallService, SendService {
    public final int talkTime;
    public final int smsCount;

    public TalkPackage() {
        talkTime = 500;
        smsCount = 30;
        price = 58;
        curPrice = price;
    }
    
    public String showInfo() {
    	return    "�ײ����ͣ������ײ�\n"
    			+ "ͨ��ʱ����200����\n"
    			+ "����������50��\n"
    			+ "���ʷѣ�    58Ԫ\n";
    }

    @Override
    public int call(int minCount, MobileCard card) {
        int moreCount = card.realTalkTime + minCount - this.talkTime;
        if(moreCount <= 0) {
            card.realTalkTime += minCount;
            return 0;
        }
        else if (card.money >= moreCount * 0.2) {
            card.realTalkTime += minCount;
            card.money = card.money - moreCount * 0.2;
            card.consumAmount += moreCount * 0.2;
            return moreCount;
        }
        else {
            card.realTalkTime = this.talkTime + (int)(card.money * 5);
            card.consumAmount += card.money;
            card.money = 0;
            return -1;
        }
    }

    @Override
    public int send(int count, MobileCard card) {
        int moreCount = card.realSMSCount + count - this.smsCount;
        if (moreCount <= 0) {
            card.realSMSCount += count;
            return 0;
        }
        else if (moreCount/10.0 <= card.money) {
            card.realSMSCount += count;
            card.money = card.money - moreCount / 10.0;
            card.consumAmount += moreCount * 0.1;
            return moreCount;
        }
        else {
            card.realSMSCount = this.smsCount + (int)(card.money*10);
            card.consumAmount += card.money;
            card.money = 0;
            return -1;
        }
    }

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}
}
