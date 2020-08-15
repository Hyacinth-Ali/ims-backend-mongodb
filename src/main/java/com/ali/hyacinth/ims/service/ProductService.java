package com.ali.hyacinth.ims.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.shared.dto.ProductDTO;

@Service
public interface ProductService {
	
	void callCreateProduct() throws InvalidInputException;
	
	void createProduct(ProductDTO productDTO, String employeeId) throws InvalidInputException;
	
	void deleteProduct(String name, String employeeId) throws InvalidInputException;
	
	void updateProduct(String oldName, ProductDTO productDTO, String employeeId) throws InvalidInputException;
	
	void addProductItem(String name, int newQuantity, String employeeId) throws InvalidInputException;
	
	List<ProductDTO> getProducts(String employeeId);

}
