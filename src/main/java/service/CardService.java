package service;

import java.sql.SQLException;
import java.util.List;

import entity.ConsumInfo;
import entity.MobileCard;

public interface CardService {
	// 获取卡对象
	MobileCard getCardByNumber(String number) throws SQLException;
	// 删除卡对象
	void delCardByNumber(String number);
	// 是否存在，用于充值时判断
	boolean isExist(String number) throws SQLException;
	// 是否存在，用于登录判断
	boolean isExist(String number, String password) throws SQLException;
	// 添加卡号，用于注册
	void addCard(MobileCard mc);
	// 获取未使用的number个卡号，
	String[] getNewNumbers(int number) throws SQLException;
	// 使用soso
	String useSoso(String number);
	// 话费充值
	boolean chargeMoney(String number, double money) throws SQLException;
	// 变更套餐
	String changingPack(String number, int packNum);
	// 获取消费信息
	List<ConsumInfo> getConsumeInfo(String number);
}
