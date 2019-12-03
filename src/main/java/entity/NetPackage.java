package entity;

import service.*;
import entity.MobileCard;

public class NetPackage extends ServicePackage implements NetService {
    public final int flow;
    public NetPackage() {
        flow = 3 * 1024;
        price = 68;
        curPrice = price;
    }
    
    public String showInfo() {
    	return  "锟阶诧拷锟斤拷锟酵ｏ拷锟斤拷锟斤拷锟阶诧拷\n"
    		  + "锟斤拷锟斤拷锟斤拷锟斤拷锟�5GB\n"
    		  + "锟斤拷锟绞费ｏ拷    68元\n";
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
		return 1;
	}
}
