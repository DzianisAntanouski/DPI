package com.sap.showcase.media.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.sap.showcase.media.model.Edition;

@Service
@Validated
public class EditionService {
	@Autowired
	private EditionRepository editionRepository;

	public List<Edition> getEditions(Long magazineID){
		List<Edition> Edition = new ArrayList<>();
		editionRepository.findByMagazineMagazineID(magazineID).forEach(Edition::add);
		return Edition;
	}
	
	public Edition getEdition(Long id) {
		return editionRepository.findById(id).get();
	}

	public void addEdition(Edition Edition) {
		editionRepository.save(Edition);		
	}

	public void updateEdition(Long id, Edition Edition) {
		editionRepository.save(Edition);		
	}

	public void deleteEdition(Long id) {
		editionRepository.deleteById(id);
	}
	
	public void deleteAllEdition() {
		editionRepository.deleteAll();
	}
	
	public void deleteAllEditionByMagazineID(Long id) {
		for(Edition edition : editionRepository.findByMagazineMagazineID(id) ){
			editionRepository.deleteById(edition.getEditionID());
		}
	}
	
	public boolean exists(Long id) {
		return editionRepository.existsById(id);
	}

}
