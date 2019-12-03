package dao;

import java.util.List;

import entity.ConsumInfo;

public interface ConsumeDao {
	// 查询号码对应所有消费信息
	List<ConsumInfo> queryByNumber(String number);
	// 添加消费信息
	void insert(ConsumInfo consum);
}
