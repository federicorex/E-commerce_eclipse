package com.ecommerce.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.dal.ProductDAO;
import com.ecommerce.entities.Product;

public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	private static final String LOGGER_GET_ALL_PRODUCTS = "Fetching all products";
	private static final String LOGGER_GET_PRODUCT_BY_ID = "Fetching the product with id: {}";
	private static final String LOGGER_GET_PRODUCT_BY_ID_FAIL = "Fail, product not found";
	private static final String LOGGER_ADD_PRODUCT_SUCCESS = "Success, product with id: {} added";
	private static final String LOGGER_UPDATE_PRODUCT_SUCCESS = "Success, product with id: {} updated";
	private static final String LOGGER_DELETE_PRODUCT_SUCCESS = "Success, product with id: {} deleted";
	
	private final ProductDAO productDAOrepository;
	
	public ProductServiceImpl(ProductDAO productDAO) {
		this.productDAOrepository = productDAO;
	}

	@Override
	public List<Product> getAllProducts() {
		logger.info(LOGGER_GET_ALL_PRODUCTS);
		return productDAOrepository.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		logger.info(LOGGER_GET_PRODUCT_BY_ID);
		logger.info(LOGGER_GET_PRODUCT_BY_ID_FAIL);
		
		Optional<Product> tempProduct = productDAOrepository.findById(id);
		
		if(tempProduct.isPresent()) {
			Product product = tempProduct.get();
			
			logger.info(LOGGER_GET_PRODUCT_BY_ID, id);
			return product;
		} else {
			logger.warn(LOGGER_GET_PRODUCT_BY_ID_FAIL, id);
			throw new NoSuchElementException("Product with id: " + id + "found...");
		}
	}

	@Override
	@Transactional
	public void addProduct(Product product) {
		productDAOrepository.save(product);
		logger.info(LOGGER_ADD_PRODUCT_SUCCESS);
	}

	@Override
	@Transactional
	public void updateProduct(Product product) {
		productDAOrepository.save(product);
		logger.info(LOGGER_UPDATE_PRODUCT_SUCCESS);
	}

	@Override
	@Transactional
	public void deleteProduct(Long id) {
		productDAOrepository.deleteById(id);
		logger.info(LOGGER_DELETE_PRODUCT_SUCCESS);
	}
	
}
