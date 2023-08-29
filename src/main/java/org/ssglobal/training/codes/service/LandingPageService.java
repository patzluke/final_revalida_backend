package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;

public interface LandingPageService {

	//Post Advertisement
	List<PostAdvertisement> selectAllPostAdvertisements();
	
	//Crop Specialization
	List<CropSpecialization> selectAllCropSpecialization();
}
