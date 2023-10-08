package com.flapkap.vending.integration_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flapkap.vending.VendingApplication;
import lombok.RequiredArgsConstructor;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = {VendingApplication.class})
@ContextConfiguration(initializers = IntegrationTestBase.Init.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class IntegrationTestBase {

  private static final String POSTGRES_IMAGE_NAME = "postgres:latest";

  private static final String TEST_DB = "vending";

  @Autowired private ObjectMapper objectMapper;

  @ClassRule
  public static PostgreSQLContainer<?> postgresdb =
      new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME)
          .withUsername(TEST_DB)
          .withPassword(TEST_DB)
          .withDatabaseName(TEST_DB);

  public static class Init
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
      postgresdb.start();
      TestPropertyValues values =
          TestPropertyValues.of("spring.datasource.url=" + postgresdb.getJdbcUrl())
              .and("spring.datasource.username=" + postgresdb.getUsername())
              .and("spring.datasource.password=" + postgresdb.getPassword());

      values.applyTo(applicationContext);
    }
  }

  protected <T> T asObject(final String jsonString, Class<T> clazz) throws JsonProcessingException {
    return objectMapper.readValue(jsonString, clazz);
  }

  protected <T> T asObject(final String jsonString, TypeReference<T> reference)
      throws JsonProcessingException {
    return objectMapper.readValue(jsonString, reference);
  }

  protected String asJsonString(final Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsString(obj);
  }
}
