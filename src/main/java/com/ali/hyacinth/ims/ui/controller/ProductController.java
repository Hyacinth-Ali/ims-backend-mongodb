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
import org.springframework.web.bind.annotation.RestController;

import com.ali.hyacinth.ims.exceptions.InvalidInputException;
import com.ali.hyacinth.ims.service.ProductService;
import com.ali.hyacinth.ims.shared.dto.ProductDTO;
import com.ali.hyacinth.ims.ui.request.ProductRequestDetails;
import com.ali.hyacinth.ims.ui.response.OperationStatusModel;
import com.ali.hyacinth.ims.ui.response.ProductRest;
import com.ali.hyacinth.ims.ui.response.RequestOperationName;
import com.ali.hyacinth.ims.ui.response.RequestOperationStatus;

@RestController
@RequestMapping("products") // http://localhost:8080/products
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	 // http://localhost:8080/products
	@GetMapping(path="/{employeeId}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE }
			)
	public List<ProductRest> getProducts(@PathVariable String employeeId) {
		
		List<ProductRest> returnValue = new ArrayList<ProductRest>();
		
		List<ProductDTO> products = productService.getProducts(employeeId);
		
		ModelMapper modelMapper = new ModelMapper();
		for (ProductDTO p : products) {
			returnValue.add(modelMapper.map(p, ProductRest.class));
		}
		
		return returnValue;
	}
	

	
	/**
	 * RequestBody: to enable this method read json or xml data from a 
	 * web request
	 * @param productDetails is a class to convert the json 
	 * to java object
	 * @return
	 * @throws InvalidInputException 
	 */
	// http://localhost:8080/products
	@PostMapping(path="/{employeeId}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public void createProduct(@PathVariable String employeeId, @RequestBody ProductRequestDetails productDetails) throws Exception {
		
		ModelMapper modelMapper = new ModelMapper();
		ProductDTO productDTO = modelMapper.map(productDetails, ProductDTO.class);
		
		productService.createProduct(productDTO, employeeId);
	}
	
	/**
	 * Updates employee
	 * @param id
	 * @param userDetails
	 */
	@PutMapping(path="/{employeeId}/{oldName}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public void updateProduct(
			@PathVariable String employeeId, 
			@PathVariable String oldName, 
			@RequestBody ProductRequestDetails productDetails
			) {
	
		ProductDTO productDTO = new ProductDTO();
		BeanUtils.copyProperties(productDetails, productDTO);
		
		productService.updateProduct(oldName, productDTO, employeeId);
		
	}
	
	/**
	 * add items to a product
	 * @param id
	 * @param userDetails
	 */
	@PutMapping(path="/{employeeId}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
			)
	public void addProductItem(@PathVariable String employeeId, @RequestBody ProductRequestDetails productDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		ProductDTO productDTO = modelMapper.map(productDetails, ProductDTO.class);
		
		productService.addProductItem(productDTO.getName(), productDTO.getQuantity(), employeeId);
		
	}
	
	// http://localhost:8080/employees/:id
	@DeleteMapping(path="/{employeeId}", 
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
	)
	public OperationStatusModel deleteProduct(@PathVariable String employeeId, @RequestBody ProductRequestDetails productDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		ProductDTO productDTO = modelMapper.map(productDetails, ProductDTO.class);
		
		OperationStatusModel returnValue = new OperationStatusModel();
		
		productService.deleteProduct(productDTO.getName(), employeeId);
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}


}
