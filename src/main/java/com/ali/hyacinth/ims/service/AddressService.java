package com.ali.hyacinth.ims.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ali.hyacinth.ims.shared.dto.AddressDTO;

@Service
public interface AddressService {
	List<AddressDTO> getAddresses(String userId);
	AddressDTO getAddress(String addressId);

}
