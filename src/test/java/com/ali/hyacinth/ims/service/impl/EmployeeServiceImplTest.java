package com.ali.hyacinth.ims.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import com.ali.hyacinth.ims.ImsBackendApplication;
import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.model.Employee;
import com.ali.hyacinth.ims.repository.EmployeeRepository;
import com.ali.hyacinth.ims.shared.Utils;
import com.ali.hyacinth.ims.shared.dto.EmployeeDTO;

class EmployeeServiceImplTest {
	
	@InjectMocks
	EmployeeServiceImpl employeeService;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Mock 
	Utils utils;
	
	Employee existingEmployee;
	String userName = "jason";
	String password = "ali123";
	String email = "jason@gmail.com";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		existingEmployee = new Employee();
		existingEmployee.setPassword("ali123");
		//existingEmployee.setId(1L);
		existingEmployee.setManager(true);
		existingEmployee.setFirstName("Jason");
		existingEmployee.setLastName("Chijioke");
		existingEmployee.setEmail("jason@gmail.com");
		existingEmployee.setUserName("jason");
		existingEmployee.setEmployeeId("54321");
	}
	
	@AfterEach
	void tearDown() throws Exception {
		//clear current employees
		ImsBackendApplication.setCurrentEmployees(null);
	}
	
	/** log in  ****/

	@Test
	void testGetEmployee() {
		
		//EmployeeServiceImpl employee = new EmployeeServiceImpl();
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		EmployeeDTO employeeDTO = employeeService.getEmployeeByUserName(userName, password);
		
		assertNotNull(employeeDTO);
		assertNotNull(ImsBackendApplication.getCurrentEmployees().get(0));
		//assertNotNull(ImsBackendApplication.getCurrentEmployees().get(0).getId());
		assertNotNull(employeeDTO.getEmployeeId());
		assertEquals("Jason", employeeDTO.getFirstName());
		assertEquals("ali123", employeeDTO.getPassword());
		assertTrue(employeeDTO.isManager());
		assertEquals("Chijioke", employeeDTO.getLastName());
		assertEquals("jason@gmail.com", employeeDTO.getEmail());
		assertEquals("jason@gmail.com", ImsBackendApplication.getCurrentEmployees().get(0).getEmail());
		assertEquals("ali123", ImsBackendApplication.getCurrentEmployees().get(0).getPassword());
		assertEquals("Chijioke", ImsBackendApplication.getCurrentEmployees().get(0).getLastName());
		
	}
	
	@Test
	void testGetEmployeeEmptyUserName() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByUserName("", password);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		//assertEquals("The user name is incorrect, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testGetEmployeeIncorrectUserName() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByUserName(userName + "rte", password);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("The user name is incorrect, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testGetEmployeeEmptyPassword() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByUserName( userName )). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByUserName(userName, "");
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		//assertEquals("Incorrect password, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testGetEmployeeIncorrectPassword() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByUserName( userName )). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByUserName(userName, password + "rt");
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("Incorrect password, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testGetEmployeeNullEmail() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByEmail(null, password);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		//assertEquals("The email is incorrect, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testGetEmployeeNullUserName() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByUserName(null, password);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		//assertEquals("The user name is incorrect, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testGetEmployeeEmptyEmail() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByEmail( email )). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByEmail("", password);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		//assertEquals("The email is incorrect, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testGetEmployeeIncorrectEmail() {
		String error = "";
		
		EmployeeDTO employeeDTO = null;
		when ( employeeRepository.findByEmail( email )). thenReturn( existingEmployee );
		try {
			employeeDTO = employeeService.getEmployeeByEmail(email + "try", password);
			
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("The email is incorrect, try again!", error);
		assertNull(employeeDTO);
		assertNull(ImsBackendApplication.getCurrentEmployees());
	}
	
	@Test
	void testCreateEmployee() {
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("ali123");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("jason-new@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			fail();
		}
		
		assertEquals("Jason", employeeDTO.getFirstName());
		assertEquals("ali123", employeeDTO.getPassword());
		assertTrue(employeeDTO.isManager());
		assertEquals("Chijioke", employeeDTO.getLastName());
		assertEquals("jason-new@gmail.com", employeeDTO.getEmail());
		assertEquals("jason-new", employeeDTO.getUserName());
	}
	
	@Test
	void testCreateEmployeeEmptyFirstName() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("ali123");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("jason-new@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The first name cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeNullFirstName() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("ali123");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName(null);
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("jason-new@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The first name cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeEmptyLastName() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("ali123");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("");
		employeeDTO.setEmail("jason-new@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The last name cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeNullLasstName() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("ali123");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName(null);
		employeeDTO.setEmail("jason-new@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The last name cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeEmptyPassword() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("jason-new@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The password cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeNullPassword() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword(null);
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("jason-new@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The password cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeEmptyEmail() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("12345");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The email cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeNullEmail() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("1234");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail(null);
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The email cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeEmptyUserName() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("12345");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("ali@gmail.com");
		employeeDTO.setUserName("");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The user name cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeNullUserName() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("1234");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("ali@gmail.com");
		employeeDTO.setUserName(null);
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The user name cannot be empty.", error);
	}
	
	@Test
	void testCreateEmployeeUserNameExist() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("12345");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("ali@gmail.com");
		employeeDTO.setUserName("jason");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The user name already exist.", error);
	}
	
	@Test
	void testCreateEmployeeEmailExist() {
		
		String error = "";
		when ( employeeRepository.findByUserName( userName)). thenReturn( existingEmployee );
		when ( employeeRepository.findByEmail( email)). thenReturn( existingEmployee );
		when ( utils.generateEmployeeId( anyInt())). thenReturn("12345");
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPassword("1234");
		employeeDTO.setManager(true);
		employeeDTO.setFirstName("Jason");
		employeeDTO.setLastName("Chijioke");
		employeeDTO.setEmail("jason@gmail.com");
		employeeDTO.setUserName("jason-new");
		
		try {
			employeeService.createEmployee(employeeDTO);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		assertEquals("The email already exist.", error);
	}


}
