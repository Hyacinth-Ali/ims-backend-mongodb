package com.ali.hyacinth.ims.service;

import org.springframework.stereotype.Service;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.shared.dto.CEODTO;

@Service
public interface CEOService {
	
	CEODTO createCEO(CEODTO CEODTO) throws InvalidInputException;
	
	CEODTO updateCEO(String id, CEODTO CEODto) throws InvalidInputException;
	
	void deleteCEO(String CEOId);

}
