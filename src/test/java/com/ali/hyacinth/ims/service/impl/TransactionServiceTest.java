package com.ali.hyacinth.ims.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.ali.hyacinth.ims.ImsBackendApplication;
import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.model.Customer;
import com.ali.hyacinth.ims.model.Employee;
import com.ali.hyacinth.ims.model.Transaction;
import com.ali.hyacinth.ims.repository.CustomerRepository;
import com.ali.hyacinth.ims.repository.EmployeeRepository;
import com.ali.hyacinth.ims.repository.ProductRepository;
import com.ali.hyacinth.ims.repository.ProductTransactionRepository;
import com.ali.hyacinth.ims.repository.TransactionRepository;
import com.ali.hyacinth.ims.shared.dto.TransactionDTO;

class TransactionServiceTest {
	
	@InjectMocks
	TransactionServiceImpl transactionService;
	
	@Mock
	TransactionRepository transactionRepository;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	EmployeeRepository employeeRepository;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	ProductTransactionRepository productTransactionRepository;
	
	Employee employee;
	String userName = "jason";
	String password = "ali123";
	String email = "jason@gmail.com";
	
	Customer customer;
	String cName = "Mekus";
	
	List<Transaction> transactions;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		employee = new Employee();
		employee.setPassword("ali123");
		employee.setId(1L);
		employee.setManager(true);
		employee.setFirstName("Jason");
		employee.setLastName("Chijioke");
		employee.setEmail("jason@gmail.com");
		employee.setUserName("jason");
		employee.setEmployeeId("54321");
		
		customer = new Customer();
		customer.setFirstName("Emeka");
		customer.setLastName("Oyiga");
		customer.setId(1);
		customer.setUserName("Mekus");
		
		Transaction t1 = new Transaction();
		t1.setTotalAmount(100);
		t1.setAmountPaid(60);
		t1.setAmountUnpaid(40);
		t1.setTransactionDate("2020/03/04");
		t1.setId(1);
		
		Transaction t2 = new Transaction();
		t2.setTotalAmount(200);
		t2.setAmountPaid(120);
		t2.setAmountUnpaid(80);
		t2.setTransactionDate("2020/03/05");
		t2.setId(2);
		
		transactions = new ArrayList<Transaction>();
		transactions.add(t1);
		transactions.add(t2);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetCustomerTransactions() {
		
//		when ( customerRepository.findByUserName( cName)). thenReturn( customer );
//		when ( transactionRepository.findAllByBuyer(customer)). thenReturn( transactions );
//		
//		List<TransactionDTO> ts = null;
//		try {
//			 ts = transactionService.getCustomerTransactions(cName);
//		} catch (InvalidInputException e) {
//			fail();
//		}
//		
//		assertNotNull(ts);
//		assertNotNull(ts.get(0));
//		assertNotNull(ts.get(1));
//		assertEquals(ts.get(0).getTotalAmount(), 100);
//		assertEquals(ts.get(1).getTotalAmount(), 200);
//		assertEquals(ts.get(0).getAmountPaid(), 60);
//		assertEquals(ts.get(1).getAmountPaid(), 120);
//		assertEquals(ts.get(0).getDate(), "2020/03/04");
//		assertEquals(ts.get(1).getDate(), "2020/03/05");
//		assertEquals(ts.get(0).getId(), 1);
//		assertEquals(ts.get(1).getId(), 2);
		
	}
	
	@Test
	void testGetCustomerTransactionsEmptyUserName() {
		
//		when ( customerRepository.findByUserName( "")). thenReturn( null );
//		when ( transactionRepository.findAllByBuyer(customer)). thenReturn( null );
//		List<TransactionDTO> ts = null;
//		String error = "";
//		try {
//			 ts = transactionService.getCustomerTransactions("");
//		} catch (InvalidInputException e) {
//			error = e.getMessage();
//		}
//		
//		assertNull(ts);
//		assertEquals(error, "Please select the customer.");
		
	}
	
	@Test
	void testGetCustomerTransactionsNullUserName() {
		
//		when ( customerRepository.findByUserName( "")). thenReturn( null );
//		when ( transactionRepository.findAllByBuyer(customer)). thenReturn( null );
//		List<TransactionDTO> ts = null;
//		String error = "";
//		try {
//			 ts = transactionService.getCustomerTransactions("");
//		} catch (InvalidInputException e) {
//			error = e.getMessage();
//		}
//		
//		assertNull(ts);
//		assertEquals(error, "Please select the customer.");
		
	}

}
