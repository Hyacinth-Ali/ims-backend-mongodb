package com.ali.hyacinth.ims.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.service.AddressService;
import com.ali.hyacinth.ims.service.EmployeeService;
import com.ali.hyacinth.ims.shared.dto.EmployeeDTO;
import com.ali.hyacinth.ims.ui.request.EmployeeDetailsRequest;
import com.ali.hyacinth.ims.ui.request.EmployeeLoginRequest;
import com.ali.hyacinth.ims.ui.response.EmployeeRest;
import com.ali.hyacinth.ims.ui.response.OperationStatusModel;
import com.ali.hyacinth.ims.ui.response.RequestOperationName;
import com.ali.hyacinth.ims.ui.response.RequestOperationStatus;

@RestController
@RequestMapping("employees") // http://localhost:8080/employees
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	AddressService addressService;
	 // http://localhost:8080/employees
	@GetMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE }
			)
	public EmployeeRest getEmployee(@RequestBody EmployeeLoginRequest loginDetails) {
		
		EmployeeRest returnValue = new EmployeeRest();
		EmployeeDTO returnUserDto = new EmployeeDTO();
//		EmployeeDTO employeeDTO = new EmployeeDTO();
//		BeanUtils.copyProperties(loginDetails, employeeDTO);
		if (loginDetails.getIdentifier().contains("@")) {
			returnUserDto = employeeService.getEmployeeByEmail(loginDetails.getIdentifier(), loginDetails.getPassword());
		} else {
			returnUserDto = employeeService.getEmployeeByUserName(loginDetails.getIdentifier(), loginDetails.getPassword());
		}
		
		BeanUtils.copyProperties(returnUserDto, returnValue);
		
		return returnValue;
	}
	

	
	/**
	 * RequestBody: to enable this method read json or xml data from a 
	 * web request
	 * @param employeeDetails is a class to convert the json 
	 * to java object
	 * @return
	 * @throws InvalidInputException 
	 */
	// http://localhost:8080/employees
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public void createEmployee(@RequestBody EmployeeDetailsRequest employeeDetails) throws Exception {
		
		ModelMapper modelMapper = new ModelMapper();
		EmployeeDTO employeeDTO = modelMapper.map(employeeDetails, EmployeeDTO.class);
		
		employeeService.createEmployee(employeeDTO);
	}
	
	/**
	 * Updates employee
	 * @param id
	 * @param userDetails
	 */
	@PutMapping(path="/{id}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public void updateEmployee(@PathVariable String id, @RequestBody EmployeeDetailsRequest userDetails) {
	
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(userDetails, employeeDTO);
		
		employeeService.updateEmployee(id, employeeDTO);
		
	}
	// http://localhost:8080/employees/:id
	@DeleteMapping(path="/{employeeId}", 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
	)
	public OperationStatusModel deleteEmployee(@PathVariable String employeeId) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		
		employeeService.deleteEmployee(employeeId);
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	// http://localhost:8080/employees
	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public List<EmployeeRest> getUsers(@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="limit", defaultValue="1") int limit) {
		
		List<EmployeeRest> returnValue = new ArrayList<>();
		
		List<EmployeeDTO> users = employeeService.getEmployees(page, limit);
		
		for (EmployeeDTO employeeDTO : users) {
			EmployeeRest userModel = new EmployeeRest();
			BeanUtils.copyProperties(employeeDTO, userModel);
			returnValue.add(userModel);
		}
		return returnValue;
	}


}
