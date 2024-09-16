package com.revature.revshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revshop.dto.CategoryDto;
import com.revature.revshop.dto.ProductRequest;
import com.revature.revshop.dto.ProductResponse;
import com.revature.revshop.model.Category;
import com.revature.revshop.model.Product;
import com.revature.revshop.repository.CategoryRepository;
import com.revature.revshop.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	
	
	@Autowired
	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public Product mapToProduct(ProductRequest productRequest) {
		
		Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
		
		
		return  Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.skuCode(productRequest.getSkuCode())
				.price(productRequest.getPrice())
				.category(category)
				.build();
										
	}
	
	public ProductResponse mapToProductResponse(Product product) {
		
		Category category = product.getCategory();
		CategoryDto categoryDto = CategoryDto.builder()
				.id(category.getId())
				.name(category.getName())
				.build();
		
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.skuCode(product.getSkuCode())
				.price(product.getPrice())
				.categoryDto(categoryDto)
				.build();
	}

	public ProductResponse createProduct(ProductRequest productRequest) {
		
		Product product = mapToProduct(productRequest);
		
		product = productRepository.save(product);
		
		return mapToProductResponse(product);
		
	}
	
	public ProductResponse getProductById(Long id) {
		
		Product product = productRepository.findById(id).get();
		
		return mapToProductResponse(product);		
		
	}
	
	public ProductResponse updateProductById(ProductRequest productRequest, Long id) {
		
		Product product = mapToProduct(productRequest);
		product.setId(id);
		
		product = productRepository.save(product);
		
		return mapToProductResponse(product);
		
	}
	
	public boolean deleteProductById(Long id) {
		try {
			
			productRepository.deleteById(id);
			return true;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ProductResponse> getAllProducts(){
		
		List<Product> products = productRepository.findAll();
		
		return products.stream().map(product -> mapToProductResponse(product)).toList();
		
	}

}
