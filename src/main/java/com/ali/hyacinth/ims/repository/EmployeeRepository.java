package com.ali.hyacinth.ims.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.Employee;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
	Employee findByEmail(String email);
	Employee findByUserName(String employeeUserName);
	Employee findByEmployeeId(String employeeId);
}

