package com.ali.hyacinth.ims.service;

import java.util.List;

import com.ali.hyacinth.ims.shared.dto.AddressDTO;

public interface AddressService {
	List<AddressDTO> getAddresses(String userId);
	AddressDTO getAddress(String addressId);

}
