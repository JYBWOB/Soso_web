package dao;

import org.apache.ibatis.annotations.Param;

import entity.MobileCard;

public interface CardDao {
	// ���Ų�ѯ��ȡ������
	MobileCard queryByNumber(String number);
	// ɾ��������Ϣ�����ڰ�������
	void delByNumber(String number);
	// �˺������ѯ
	MobileCard query(@Param("number")String number,
			@Param("password")String password);
	// ���뿨����
	void insert(MobileCard card);
	// ���¿�����
	void updateCard(MobileCard card);
}
