package dao;

import java.util.List;

import entity.ConsumInfo;

public interface ConsumeDao {
	// ��ѯ�����Ӧ����������Ϣ
	List<ConsumInfo> queryByNumber(String number);
	// ���������Ϣ
	void insert(ConsumInfo consum);
}
