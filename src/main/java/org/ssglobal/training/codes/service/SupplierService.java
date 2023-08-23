package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;

public interface SupplierService {

	//Post Advertisement
	List<PostAdvertisement> selectPostAdvertisementBySupplierId(Integer supplierId);
	PostAdvertisement insertIntoPostAdvertisement(Map<String, Object> payload);
	PostAdvertisement updateIntoPostAdvertisement(Map<String, Object> payload);
	PostAdvertisement softDeletePostAdvertisement(Integer postId);
	
	//Crop Specialization
	List<CropSpecialization> selectAllCropSpecialization();
	
	// Post Advertisement Respones
	List<PostAdvertisementResponse> selectAllPostAdvertisementResponsesByPostId(Integer postId);
}
