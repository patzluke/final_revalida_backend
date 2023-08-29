package org.ssglobal.training.codes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.repository.LandingPageRepository;
import org.ssglobal.training.codes.service.LandingPageService;

@Service
public class LandingPageServiceImpl implements LandingPageService {

	@Autowired
	private LandingPageRepository repository;
	
	@Override
	public List<PostAdvertisement> selectAllPostAdvertisements() {
		return repository.selectAllPostAdvertisements();
	}

	@Override
	public List<CropSpecialization> selectAllCropSpecialization() {
		return repository.selectAllCropSpecialization();
	}

}
