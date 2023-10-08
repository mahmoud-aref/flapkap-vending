package com.flapkap.vending.domain.product.service.impl;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.commons.ItemNotFoundException;
import com.flapkap.vending.domain.product.model.ProductCreationRequest;
import com.flapkap.vending.domain.product.model.ProductDTO;
import com.flapkap.vending.domain.product.model.ProductEditRequest;
import com.flapkap.vending.domain.product.model.ProductMapper;
import com.flapkap.vending.domain.product.service.ProductService;
import com.flapkap.vending.infrastructure.product.datasource.jpa.ProductRepository;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    // not a good way for messaging but for task it's ok
    // proper way is to use localization and messages.properties file
    public static final String PRODUCT_ADDED_MESSAGE = "product added";
    public static final String PRODUCT_DELETED_MESSAGE = "product with id %d is deleted";
    public static final String PRODUCT_EDITED_MESSAGE = "product with id %d is edited";
    public static final String PRODUCT_NOT_FOUND_MESSAGE = "product with id %d not found";

    @Override
    public BasicResponse addNewProduct(ProductCreationRequest request) {
        var productEntity = productMapper.toEntity(request);
        productRepository.save(productEntity);
        return BasicResponse.builder().message(PRODUCT_ADDED_MESSAGE).build();
    }

    @Override
    public List<ProductDTO> listAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDTO).toList();
    }

    @Override
    public BasicResponse deleteProduct(int productId) {
        productRepository.findById(productId).orElseThrow(
                () -> new ItemNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));

        productRepository.deleteById(productId);
        return BasicResponse.builder().message(
                String.format(PRODUCT_DELETED_MESSAGE, productId)).build();
    }

    @Override
    public BasicResponse editProduct(int productId, ProductEditRequest request) {
        var product =
                productRepository
                        .findById(productId)
                        .orElseThrow(
                                () -> new ItemNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));

        if (request.getName() != null && !request.getName().isEmpty())
            product.setName(request.getName());

        if (request.getPrice() > 0) product.setPrice(request.getPrice());

        productRepository.save(product);
        return BasicResponse.builder().message(String.format(PRODUCT_EDITED_MESSAGE, productId)).build();
    }
}
