package com.sap.showcase.media.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.showcase.media.model.AdvertisementType;

@Service
public class AdvertisementTypeService {
	@Autowired
	private AdvertisementTypeRepository advertisementTypeRepository;

	public List<AdvertisementType> getAdvertisementTypes(){
		List<AdvertisementType> advertTypes = new ArrayList<>();
		advertisementTypeRepository.findAll().forEach(advertTypes::add);
		return advertTypes;
	}
	
	public AdvertisementType getadvertisementType(Long id) {
		return advertisementTypeRepository.findById(id).get();
	}

	public void addAdvertisementType(AdvertisementType advertisementType) {
		advertisementTypeRepository.save(advertisementType);
		
	}

	public void updateAdvertisementType(Long id, AdvertisementType advertisementType) {
		advertisementType.setAdvertisementTypeId(id);
		advertisementTypeRepository.save(advertisementType);		
	}

	public void deleteAdvertisementType(Long id) {
		advertisementTypeRepository.deleteById(id);
	}

	public void deleteAllAdvertisementTypes() {
		advertisementTypeRepository.deleteAll();
	}

	public boolean exists(long id) {
		return advertisementTypeRepository.existsById(id);
	}
}