package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.models.PostAdvertisement;

public interface SupplierService {

	List<PostAdvertisement> selectPostAdvertisementBySupplierId(Integer supplierId);
	PostAdvertisement insertIntoPostAdvertisement(Map<String, Object> payload);
	PostAdvertisement updateIntoPostAdvertisement(Map<String, Object> payload);
	PostAdvertisement softDeletePostAdvertisement(Integer postId);
}
