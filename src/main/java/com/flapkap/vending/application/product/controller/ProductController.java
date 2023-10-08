package com.flapkap.vending.application.product.controller;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.product.model.ProductCreationRequest;
import com.flapkap.vending.domain.product.model.ProductDTO;
import com.flapkap.vending.domain.product.model.ProductEditRequest;
import com.flapkap.vending.domain.product.service.ProductService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flapkap.vending.infrastructure.security.model.UserAccessPermissions.HAS_SELLER_OR_BUYER_ROLE;
import static com.flapkap.vending.infrastructure.security.model.UserAccessPermissions.HAS_SELLER_ROLE;

@RestController
@RequestMapping(ProductController.PRODUCT_PATH)
@RequiredArgsConstructor
public class ProductController {

    public static final String PRODUCT_PATH = "/vending/api/v1/product";
    public static final String PRODUCT_ADD_PATH = "/add";
    public static final String PRODUCT_EDIT_PATH = "/{productId}/edit";
    public static final String PRODUCT_DELETE_PATH = "/{productId}/delete";

    private final ProductService productService;

    @GetMapping // gets all products in all machines
    @PreAuthorize(HAS_SELLER_OR_BUYER_ROLE)
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        return ResponseEntity.ok(productService.listAllProducts());
    }

    @PostMapping(PRODUCT_ADD_PATH)
    @PreAuthorize(HAS_SELLER_ROLE)
    public ResponseEntity<BasicResponse> addProduct(@RequestBody ProductCreationRequest request) {
        return ResponseEntity.ok(productService.addNewProduct(request));
    }

    @PutMapping(PRODUCT_EDIT_PATH)
    @PreAuthorize(HAS_SELLER_ROLE)
    public ResponseEntity<BasicResponse> editProduct(
            @RequestBody ProductEditRequest request, @PathVariable int productId) {
        return ResponseEntity.ok(productService.editProduct(productId, request));
    }

    @DeleteMapping(PRODUCT_DELETE_PATH)
    @PreAuthorize(HAS_SELLER_ROLE)
    public ResponseEntity<BasicResponse> deleteProduct(@PathVariable int productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
}
