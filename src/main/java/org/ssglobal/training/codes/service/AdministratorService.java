package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.FarmingTip;

public interface AdministratorService {
	
	List<Administrator> selectAllAdministrators();
	
	//Farming Tips
	List<FarmingTip> selectAllFarmingTip();
	FarmingTip insertIntoFarmingTip(FarmingTip farmingTip);
	FarmingTip updateIntoFarmingTip(FarmingTip farmingTip);
	FarmingTip deleteFarmingTip(Integer farmingTipId);
}
