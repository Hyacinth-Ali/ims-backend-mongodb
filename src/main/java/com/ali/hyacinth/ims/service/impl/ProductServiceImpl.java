package com.ali.hyacinth.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ali.hyacinth.ims.ImsBackendApplication;
import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.model.Employee;
import com.ali.hyacinth.ims.model.Product;
import com.ali.hyacinth.ims.repository.EmployeeRepository;
import com.ali.hyacinth.ims.repository.ProductRepository;
import com.ali.hyacinth.ims.service.ProductService;
import com.ali.hyacinth.ims.shared.Utils;
import com.ali.hyacinth.ims.shared.dto.ProductDTO;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	Utils utils;

	@Override
	public void callCreateProduct() throws InvalidInputException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method creates new instance of {@link Product}
	 * @param productDTO is transfer object object which contains name, price, and quantity of the product
	 */
	@Override
	public void createProduct(ProductDTO productDTO, String employeeId) throws InvalidInputException {
		
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		boolean loggedIn = false;
		if (employee == null) {
			throw new InvalidInputException("An employee must log in.");
		}
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getUserName().equals(employee.getUserName())) {
				loggedIn = true;
			}
		}
		String error = "";
		if (!loggedIn) {
			error = "An employee must log in before creating a product.";
		} else if (!employee.isManager()) {
			error = "A manager is required to create a product.";
		} else if (productDTO.getName() == null || productDTO.getName().equals("")) {
			error = "The name of a product cannot be empty.";
		} else if (productDTO.getItemPrice() < 0) {
			error = "The price of a product cannot be negative.";
		} else if (productDTO.getItemPrice() == 0) {
			error = "The price of a product cannot be zero";
		} else if (productDTO.getQuantity() <= 0) {
			error = "Quantity of a product cannot be less than one.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		ModelMapper modelMapper = new ModelMapper();
		
		Product newProduct = modelMapper.map(productDTO, Product.class);
		String publicProductId = utils.generateEmployeeId(30);
		newProduct.setProductId(publicProductId);
		
		try {
			productRepository.save(newProduct);
		} catch (Exception e) {
			//TODO: fine-tune the response
			throw new InvalidInputException(e.getMessage());
			//"Error!, seems the name already exist"
		}
		
		
	}

	/**
	 * Deletes an instance of a product
	 */
	@Transactional
	@Override
	public void deleteProduct(String name, String employeeId) throws InvalidInputException {
		
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		boolean loggedIn = false;
		if (employee == null) {
			throw new InvalidInputException("An employee must log in.");
		}
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getUserName().equals(employee.getUserName())) {
				loggedIn = true;
			}
		}
		
		String error = "";
		if (!loggedIn) {
			error = "An employee must log in before deleting a product.";
		} else if (!employee.isManager()) {
			error = "A manager is required to delete a product.";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		Product product = productRepository.findByName(name);
		
		//TODO: delete referenced value in product transaction
		
		if (product == null) {
			throw new InvalidInputException("The product does not exist.");
		}
		if (product.getProductTransactions() != null) {
			if (product.getProductTransactions().size() > 0) {
				throw new InvalidInputException("This product is used in a transaction, Not Deleted!cannot be deleted!");
			}
		}
		productRepository.delete(product);
		
	}

	/**
	 * updates the details of a given instance of a {@link Product}
	 */
	@Override
	public void updateProduct(String oldName, ProductDTO productDTO, String employeeId) throws InvalidInputException {
		
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		boolean loggedIn = false;
		if (employee == null) {
			throw new InvalidInputException("An employee must log in.");
		}
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getUserName().equals(employee.getUserName())) {
				loggedIn = true;
			}
		}
		String error = "";
		if (!loggedIn) {
			error = "An employee must log in before updating a product.";
		} else if (!employee.isManager()) {
			error = "A manager is required to update a product.";
		} else if (productDTO.getName() == null || productDTO.getName().equals("")) {
			error = "The name of a product cannot be empty.";
		} else if (productDTO.getItemPrice() < 0) {
			error = "The price of a product cannot be negative.";
		} else if (productDTO.getItemPrice() == 0) {
			error = "The price of a product cannot be zero";
		} else if (productDTO.getQuantity() <= 0) {
			error = "Quantity of a product cannot be less than one.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		
		Product product = productRepository.findByName(oldName);
		
		if (product != null) {
			product.setName(productDTO.getName());
			product.setItemPrice(productDTO.getItemPrice());
			product.setQuantity(productDTO.getQuantity());
		}
		
		try {
			productRepository.save(product);
		} catch (Exception e) {
			throw new InvalidInputException("Error!, seems the name already exist or the product does not exist");
		}
		
	}
	
	/**
	 * Get products
	 */
	public List<ProductDTO> getProducts(String employeeId) {
		
		List<ProductDTO> returnValue = new ArrayList<ProductDTO>();
		
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		boolean loggedIn = false;
		if (employee == null) {
			throw new InvalidInputException("An employee must log in.");
		}
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getUserName().equals(employee.getUserName())) {
				loggedIn = true;
			}
		}
		if (!loggedIn) {
			throw new InvalidInputException("Error! an employee must log in..");
		}  
		ModelMapper modelMapper = new ModelMapper();
		Iterable<Product> products = productRepository.findAll();

		for (Product p : products) {
			returnValue.add(modelMapper.map(p, ProductDTO.class));
		}
		return returnValue;
	}

	/**
	 * Adds item(s) to an existing product
	 * @param name of the product
	 * @param quantity of the product;
	 */
	@Override
	public void addProductItem(String name, int newQuantity, String employeeId) throws InvalidInputException {
		
		Employee employee = employeeRepository.findByEmployeeId(employeeId);
		boolean loggedIn = false;
		if (employee == null) {
			throw new InvalidInputException("An employee must log in.");
		}
		for (Employee e : ImsBackendApplication.getCurrentEmployees()) {
			if (e.getUserName().equals(employee.getUserName())) {
				loggedIn = true;
			}
		}
		
		String error = "";
		if (!loggedIn) {
			error = "An employee must log in before adding product items.";
		} else if (!employee.isManager()) {
			error = "A manager is required to add product items.";
		} 
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		if (name == null || name.length() == 0) {
			error = "The name of a product cannot be empty.";
		}
		if (newQuantity < 0) {
			error = "Quantity of a product cannot be less than zero.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		Product product = productRepository.findByName(name);
		if (product != null) {
			product.setQuantity(product.getQuantity() + newQuantity);
			productRepository.save(product);
		} else {
			throw new InvalidInputException("The product does not exist.");
		}
		
			
	}

}
