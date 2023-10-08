package com.flapkap.vending.domain.product.service;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.product.model.ProductCreationRequest;
import com.flapkap.vending.domain.product.model.ProductDTO;
import com.flapkap.vending.domain.product.model.ProductEditRequest;
import java.util.List;

public interface ProductService {

  BasicResponse addNewProduct(ProductCreationRequest request);

  List<ProductDTO> listAllProducts();

  BasicResponse deleteProduct(int productId);

  BasicResponse editProduct(int productId, ProductEditRequest request);
}
