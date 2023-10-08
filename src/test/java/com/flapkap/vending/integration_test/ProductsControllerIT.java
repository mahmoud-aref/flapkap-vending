package com.flapkap.vending.integration_test;

import static com.flapkap.vending.application.product.controller.ProductController.PRODUCT_ADD_PATH;
import static com.flapkap.vending.application.product.controller.ProductController.PRODUCT_DELETE_PATH;
import static com.flapkap.vending.application.product.controller.ProductController.PRODUCT_EDIT_PATH;
import static com.flapkap.vending.application.product.controller.ProductController.PRODUCT_PATH;
import static com.flapkap.vending.domain.product.service.impl.ProductServiceImpl.PRODUCT_ADDED_MESSAGE;
import static com.flapkap.vending.domain.product.service.impl.ProductServiceImpl.PRODUCT_DELETED_MESSAGE;
import static com.flapkap.vending.domain.product.service.impl.ProductServiceImpl.PRODUCT_EDITED_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.product.model.ProductCreationRequest;
import com.flapkap.vending.domain.product.model.ProductDTO;
import com.flapkap.vending.domain.product.model.ProductEditRequest;
import com.flapkap.vending.infrastructure.product.datasource.jpa.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(
    username = "test-user",
    authorities = {"SELLER"})
@Sql(
    scripts = {"classpath:db/create_test_products.sql"},
    executionPhase = BEFORE_TEST_METHOD)
public class ProductsControllerIT extends IntegrationTestBase {

  @Autowired private MockMvc mockMvc;

  @Autowired private ProductRepository productRepository;

  @Test
  void whenListAllProducts_thenReturnAvailableOnDb() throws Exception {

    var result =
        mockMvc.perform(get(PRODUCT_PATH)).andDo(print()).andExpect(status().isOk()).andReturn();

    List<ProductDTO> products =
        asObject(result.getResponse().getContentAsString(), new TypeReference<>() {});

    assertEquals(10, products.size());

    var product1 =
        products.stream().filter(product -> product.getId() == 1).findFirst().orElse(null);

    assertNotNull(product1);
    assertEquals("TEST_PRODUCT_1", product1.getName());
  }

  @Test
  void whenAddNewProduct_productAddedSuccessfully() throws Exception {

    var result =
        mockMvc
            .perform(
                post(PRODUCT_PATH + PRODUCT_ADD_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        asJsonString(
                            ProductCreationRequest.builder()
                                .name("TEST_PRODUCT_11")
                                .price(50) // no validation unfortunately for new :v
                                .description("amazing product")
                                .build())))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    BasicResponse response =
        asObject(result.getResponse().getContentAsString(), BasicResponse.class);
    assertEquals(PRODUCT_ADDED_MESSAGE, response.getMessage());
  }

  @Test
  void whenEditProduct_productEditedSuccessfully() throws Exception {

    var result =
        mockMvc
            .perform(
                put(PRODUCT_PATH + PRODUCT_EDIT_PATH, 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                        asJsonString(
                            ProductEditRequest.builder()
                                .name("PEPSI")
                                .price(5)
                                .description("the amazing PEPSI")
                                .build())))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    var product = productRepository.findById(1).orElse(null);

    BasicResponse response =
        asObject(result.getResponse().getContentAsString(), BasicResponse.class);
    assertEquals(String.format(PRODUCT_EDITED_MESSAGE, 1), response.getMessage());
    assertNotNull(product);
    assertEquals("PEPSI", product.getName());
    assertEquals(5, product.getPrice());
  }

  @Test
  void whenDeleteProduct_productDeletedSuccessfully() throws Exception {
    var result =
        mockMvc
            .perform(
                delete(PRODUCT_PATH + PRODUCT_DELETE_PATH, 1)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

    var product = productRepository.findById(1).orElse(null);

    BasicResponse response =
        asObject(result.getResponse().getContentAsString(), BasicResponse.class);
    assertEquals(String.format(PRODUCT_DELETED_MESSAGE, 1), response.getMessage());
    assertNull(product);
  }

  @Test
  void whenDeleteProduct_productNotFound() throws Exception {
    mockMvc
        .perform(
            delete(PRODUCT_PATH + PRODUCT_DELETE_PATH, 50).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  void whenEditProduct_productNotFound() throws Exception {
    mockMvc
        .perform(
            put(PRODUCT_PATH + PRODUCT_EDIT_PATH, 50)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    asJsonString(
                        ProductEditRequest.builder()
                            .name("PEPSI")
                            .price(5)
                            .description("the amazing PEPSI")
                            .build())))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @AfterEach
  void clear() {
    productRepository.deleteAll();
  }
}
