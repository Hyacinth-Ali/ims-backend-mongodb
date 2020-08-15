package com.ali.hyacinth.ims;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.model.Customer;
import com.ali.hyacinth.ims.model.Employee;

@SpringBootApplication
public class ImsBackendApplication {
	private static List<Employee> currentEmployees;


	public static void setCurrentEmployees(List<Employee> currentEmployees) {
		ImsBackendApplication.currentEmployees = currentEmployees;
	}

	public static List<Employee> getCurrentEmployees() {
		return currentEmployees;
	}

	private static List<Customer> currentCustomers;

	public static void setCurrentCustomers(List<Customer> currentCustomers) {
		ImsBackendApplication.currentCustomers = currentCustomers;
	}

	public static List<Customer> getCurrentCustomers() {
		return currentCustomers;
	}

	public static void main(String[] args) {
		init();
		SpringApplication.run(ImsBackendApplication.class, args);
	}

	public static void removeCurrentEmployee(String userName) {
		Employee employee = null;
		for (Employee currentEmployee : getCurrentEmployees()) {
			if (currentEmployee.getUserName().equals(userName)) {
				employee = currentEmployee;
			}
		}
		if (employee == null) {
			throw new InvalidInputException("The employee is not currently logged in");
		}
		getCurrentEmployees().remove(employee);
	}

	public static String getCurrentDate() {
		// This is what I have before which uses default time zone
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		Date date = new Date();
//		return dateFormat.format(date);
		
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		// To TimeZone Europe/London
        SimpleDateFormat sdfGhana = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        TimeZone tzInGhana = TimeZone.getTimeZone("Europe/London");
        sdfGhana.setTimeZone(tzInGhana);
        
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.setTimeZone(tzInGhana);
        
        return sdfGhana.format(calendar.getTime());
	}

	public static void addCurrentCustomer(Customer currenteCustomer) {
		if (currentCustomers == null) {
			init();
			currentCustomers.add(currenteCustomer);
		} else {
			currentCustomers.add(currenteCustomer);
		}
	}

	private static void init() {
		currentEmployees = new ArrayList<Employee>();
		currentCustomers = new ArrayList<Customer>();
	}

	public static void addCurrentEmployee(Employee currenteEmployee) {
		if (currentEmployees == null) {
			init();
			currentEmployees.add(currenteEmployee);
		} else {
			currentEmployees.add(currenteEmployee);
		}

	}

}