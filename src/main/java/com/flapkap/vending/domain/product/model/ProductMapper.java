package com.flapkap.vending.domain.product.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  ProductDTO toDTO(ProductEntity entity);

  @Mapping(target = "id", ignore = true)
  ProductEntity toEntity(ProductCreationRequest request);
}
