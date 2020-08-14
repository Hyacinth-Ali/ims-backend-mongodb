package com.ali.hyacinth.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ali.hyacinth.ims.model.Employee;


public interface EmployeeRepository extends MongoRepository<Employee, Long> {
	Employee findByEmail(String email);
	Employee findByUserName(String employeeUserName);
	Employee findByEmployeeId(String employeeId);
}

