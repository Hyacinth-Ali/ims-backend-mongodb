package com.ali.hyacinth.ims.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Address;
import com.ali.hyacinth.ims.model.Employee;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{
	
	List<Address> findAllByEmployee(Employee employeeDetails);
	Address findByAddressId(String addressId);

}
