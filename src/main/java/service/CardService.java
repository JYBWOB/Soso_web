package service;

import java.sql.SQLException;
import java.util.List;

import entity.ConsumInfo;
import entity.MobileCard;

public interface CardService {
	// ��ȡ������
	MobileCard getCardByNumber(String number) throws SQLException;
	// ɾ��������
	void delCardByNumber(String number);
	// �Ƿ���ڣ����ڳ�ֵʱ�ж�
	boolean isExist(String number) throws SQLException;
	// �Ƿ���ڣ����ڵ�¼�ж�
	boolean isExist(String number, String password) throws SQLException;
	// ��ӿ��ţ�����ע��
	void addCard(MobileCard mc);
	// ��ȡδʹ�õ�number�����ţ�
	String[] getNewNumbers(int number) throws SQLException;
	// ʹ��soso
	String useSoso(String number);
	// ���ѳ�ֵ
	boolean chargeMoney(String number, double money) throws SQLException;
	// ����ײ�
	String changingPack(String number, int packNum);
	// ��ȡ������Ϣ
	List<ConsumInfo> getConsumeInfo(String number);
}
