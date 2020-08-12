package com.ali.hyacinth.ims.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ali.hyacinth.ims.service.CustomerService;
import com.ali.hyacinth.ims.shared.dto.CustomerDTO;
import com.ali.hyacinth.ims.ui.request.CustomerRequestDetails;
import com.ali.hyacinth.ims.ui.response.CustomerRest;
import com.ali.hyacinth.ims.ui.response.OperationStatusModel;
import com.ali.hyacinth.ims.ui.response.RequestOperationName;
import com.ali.hyacinth.ims.ui.response.RequestOperationStatus;

@RestController
@RequestMapping("customers") // http://localhost:8080/customers
public class CustomerController {

	@Autowired
	CustomerService customerService;

	// http://localhost:8080/customers
	@PostMapping(path = "/{employeeId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public void createCustomer(@PathVariable String employeeId,
			@RequestBody CustomerRequestDetails customerRequestDetails) throws Exception {

		ModelMapper modelMapper = new ModelMapper();
		CustomerDTO customerDTO = modelMapper.map(customerRequestDetails, CustomerDTO.class);
		customerService.createCustomer(customerDTO, employeeId);
	}

	@PutMapping(path = "/{employeeId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public void updateEmployee(@PathVariable String employeeId,
			@RequestBody CustomerRequestDetails customerRequestDetails) {

		ModelMapper modelMapper = new ModelMapper();
		CustomerDTO customerDTO = modelMapper.map(customerRequestDetails, CustomerDTO.class);
		customerService.updateCustomer(customerDTO, employeeId);

	}

	// http://localhost:8080/customers/:id
	@DeleteMapping(path = "/{employeeId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteCustomer(@PathVariable String employeeId,
			@RequestBody CustomerRequestDetails customerRequestDeatils) {

		OperationStatusModel returnValue = new OperationStatusModel();
		customerService.deleteCustomer(customerRequestDeatils.getUserName(), employeeId);

		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	// http://localhost:8080/customers/:id
	@GetMapping(path = "/{employeeId}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public double getCustomerDebt(@PathVariable String employeeId,
			@RequestBody CustomerRequestDetails customerRequestDeatils) {

		double returnValue = customerService.getCustomerDebt(customerRequestDeatils.getUserName(), employeeId);

		return returnValue;
	}

	// http://localhost:8080/customers/:id
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<CustomerRest> getCustomers(@RequestBody CustomerRequestDetails customerRequestDeatils) {
		List<CustomerRest> returnValue = new ArrayList<CustomerRest>();
		List<CustomerDTO> customers = customerService.getCustomers();

		ModelMapper modelMapper = new ModelMapper();
		for (CustomerDTO c : customers) {
			returnValue.add(modelMapper.map(c, CustomerRest.class));
		}
		return returnValue;
	}

	// http://localhost:8080/customers/:employeeId/:userName
	@GetMapping(path = "/get/{employeeId}",produces = { MediaType.APPLICATION_JSON_VALUE })
	public CustomerRest customerLogin(@PathVariable String employeeId, @RequestBody CustomerRequestDetails customerRequestDeatils) {
		
		String userName = customerRequestDeatils.getUserName();
		CustomerDTO customer = customerService.customerLogin(userName, employeeId);

		ModelMapper modelMapper = new ModelMapper();
		CustomerRest returnValue = modelMapper.map(customer, CustomerRest.class);
		return returnValue;
	}
	

	// http://localhost:8080/transactions/:id
	@GetMapping(path = "/transactions/{employeeId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public CustomerRest getCustomerTransactions(@PathVariable String employeeId,
			@RequestBody CustomerRequestDetails customerRequestDeatils) {

		String customerUserName = customerRequestDeatils.getUserName();
		CustomerDTO customerDTO = customerService.getCustomerTransactions(customerUserName, employeeId);
		
		ModelMapper modelMapper = new ModelMapper();
		CustomerRest returnValue = modelMapper.map(customerDTO, CustomerRest.class);
		return returnValue;
	}
	
	// http://localhost:8080/customers/logout/:userName
	@PutMapping(path = "/logout/{userName}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public void logoutCustomer(@PathVariable String userName) {

		customerService.customerLogout(userName);

	}
}
