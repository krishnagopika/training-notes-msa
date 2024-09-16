package com.revature.revshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revshop.dto.ProductRequest;
import com.revature.revshop.dto.ProductResponse;
import com.revature.revshop.service.ProductService;

@RestController
@RequestMapping("/api/product/")
public class ProductController {
	
	
	private final  ProductService productService;
	
	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
		
		return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
		
		
	}
	@GetMapping
	public ResponseEntity<ProductResponse> getProductById(@RequestParam Long id){
		
		return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
		
		
	}
	
	@PutMapping
	public ResponseEntity<ProductResponse> updateProductById(@RequestBody ProductRequest productRequest, @RequestParam Long id){
		
		return new ResponseEntity<>(productService.updateProductById(productRequest, id), HttpStatus.ACCEPTED);
		
		
	}
	
	@DeleteMapping
	public ResponseEntity<Boolean> deleteProductById( @RequestParam Long id){
		
		return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.OK);
		
		
	}
	
	@GetMapping("all")
	public ResponseEntity<List<ProductResponse>> getAllProducts(){
		
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
		
		
	}
	
	

}
