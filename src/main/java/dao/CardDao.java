package dao;

import org.apache.ibatis.annotations.Param;

import entity.MobileCard;

public interface CardDao {
	// 卡号查询获取卡对象
	MobileCard queryByNumber(String number);
	// 删除卡号信息，用于办理退网
	void delByNumber(String number);
	// 账号密码查询
	MobileCard query(@Param("number")String number,
			@Param("password")String password);
	// 插入卡对象
	void insert(MobileCard card);
	// 更新卡对象
	void updateCard(MobileCard card);
}
