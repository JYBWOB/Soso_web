package entity;

import service.*;
import entity.MobileCard;

public class SuperPackage extends ServicePackage implements CallService, SendService, NetService {
    public final int talkTime;
    public final int smsCount;
    public final int flow;

    public SuperPackage() {
        talkTime = 200;
        flow = 1024;
        smsCount = 50;
        price = 78;
        curPrice = price;
    }
    
    public String showInfo() {
    	return  "锟阶诧拷锟斤拷锟酵ｏ拷锟斤拷锟斤拷锟阶诧拷\n"
    		  + "通锟斤拷时锟斤拷锟斤拷200锟斤拷锟斤拷\n"
    		  + "锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷100锟斤拷\n"
    		  + "锟斤拷锟斤拷锟斤拷锟斤拷锟�1GB\n"
    		  + "锟斤拷锟绞费ｏ拷    78元\n";
    }


    @Override
    public int call(int minCount, MobileCard card) {
    	// 锟斤拷锟斤拷锟斤拷
        int moreCount = card.realTalkTime + minCount - this.talkTime;
        // 未锟斤拷锟斤拷锟阶诧拷锟斤拷锟斤拷
        if(moreCount <= 0) {
            card.realTalkTime += minCount;
            return 0;
        }
        // 锟斤拷锟斤拷锟阶诧拷锟斤拷锟斤拷锟斤拷锟斤拷锟窖筹拷锟斤拷
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
    public int netPlay(int flow, MobileCard card) {
        int moreFlow = card.realFlow + flow - this.flow;
        if (moreFlow <= 0) {
            card.realFlow += flow;
            return 0;
        }
        else if(card.money >= moreFlow/10.0) {
            card.realFlow += flow;
            card.money = card.money - moreFlow/10.0;
            card.consumAmount += moreFlow * 0.1;
            return moreFlow;
        }
        else {
            card.realFlow = this.flow + (int)(card.money * 10);
            card.consumAmount += card.money;
            card.money = 0;
            return -1;
        }
    }

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 2;
	}
}
