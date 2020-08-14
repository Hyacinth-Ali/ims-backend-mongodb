package com.ali.hyacinth.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	Employee findByEmail(String email);
	Employee findByUserName(String employeeUserName);
	Employee findByEmployeeId(String employeeId);
}

