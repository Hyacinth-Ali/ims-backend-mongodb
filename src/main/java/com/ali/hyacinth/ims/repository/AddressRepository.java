package com.ali.hyacinth.ims.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ali.hyacinth.ims.model.Address;
import com.ali.hyacinth.ims.model.Employee;


public interface AddressRepository extends MongoRepository<Address, Long>{
	
	List<Address> findAllByEmployee(Employee employeeDetails);
	Address findByAddressId(String addressId);

}
