package entity;


public class MobileCard {
    public String cardNumber;
    public String userName;
    public String passWord;
    public int serPackage;
    public double consumAmount;
    public double money;
    public int realTalkTime;
    public int realSMSCount;
    public int realFlow;
    public MobileCard() {}
    
    public ServicePackage getPackage() {
    	if(this.serPackage == 0)
    		return new TalkPackage();
    	else if(this.serPackage == 1)
    		return new NetPackage();
    	else if(this.serPackage == 2)
    		return new SuperPackage();
    	return null;
    }

    public MobileCard(String cardNumber, String userName, String passWord,
                      int serPackage, double money) {
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.passWord = passWord;
        this.serPackage = serPackage;
        ServicePackage sp = this.getPackage();
        this.money = money - sp.curPrice;
        this.consumAmount = sp.curPrice;
    }

    public MobileCard(String cardNumber, String userName, String passWord,
                      int serPackage, double consumAmount, double money,
                      int realTalkTime, int realSMSCount, int realFlow) {
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.passWord = passWord;
        this.serPackage = serPackage;
        this.consumAmount = consumAmount;
        this.money = money;
        this.realTalkTime = realTalkTime;
        this.realSMSCount = realSMSCount;
        this.realFlow = realFlow;
    }

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getSerPackage() {
		return serPackage;
	}

	public void setSerPackage(int serPackage) {
		this.serPackage = serPackage;
	}

	public double getConsumAmount() {
		return consumAmount;
	}

	public void setConsumAmount(double consumAmount) {
		this.consumAmount = consumAmount;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getRealTalkTime() {
		return realTalkTime;
	}

	public void setRealTalkTime(int realTalkTime) {
		this.realTalkTime = realTalkTime;
	}

	public int getRealSMSCount() {
		return realSMSCount;
	}

	public void setRealSMSCount(int realSMSCount) {
		this.realSMSCount = realSMSCount;
	}

	public int getRealFlow() {
		return realFlow;
	}

	public void setRealFlow(int realFlow) {
		this.realFlow = realFlow;
	}
    
}