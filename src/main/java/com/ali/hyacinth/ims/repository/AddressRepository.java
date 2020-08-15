package com.ali.hyacinth.ims.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Address;
import com.ali.hyacinth.ims.model.Employee;

@Repository
public interface AddressRepository extends MongoRepository<Address, String>{
	
	List<Address> findAllByEmployee(Employee employeeDetails);
	Address findByAddressId(String addressId);

}
