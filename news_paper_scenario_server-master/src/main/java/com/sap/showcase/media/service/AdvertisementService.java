package com.sap.showcase.media.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.showcase.media.model.Advertisement;

@Service
public class AdvertisementService {
	@Autowired
	private AdvertisementRepository advertisementRepository;	

	public List<Advertisement> getAdvertisementsByMagazineID(Long magazineID){
		List<Advertisement> advertisements = new ArrayList<>();
		advertisementRepository.findByMagazineMagazineID(magazineID).forEach(advertisements::add);
		return advertisements;
	}
	
	public Advertisement getAdvertisement(Long id) {
		return advertisementRepository.findById(id).get();
	}

	public void addAdvertisement(Advertisement advertisement) {
		advertisementRepository.save(advertisement);
		
	}


	public void updateAdvertisement( Advertisement advertisement) {
		advertisementRepository.save(advertisement);		
	}

	public void deleteAdvertisement(Long id) {
		advertisementRepository.deleteById(id);
		}
	
	public void deleteAllAdvertisements() {
		advertisementRepository.deleteAll();
		}

}