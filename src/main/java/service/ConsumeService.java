package service;

import java.util.List;

import entity.ConsumInfo;

public interface ConsumeService {
	List<ConsumInfo> getConsumeInfo(String number);
	void addConsumeInfo(ConsumInfo consume);
}
