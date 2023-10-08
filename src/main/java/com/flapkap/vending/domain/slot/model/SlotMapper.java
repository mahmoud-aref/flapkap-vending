package com.flapkap.vending.domain.slot.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SlotMapper {

  SlotMapper INSTANCE = Mappers.getMapper(SlotMapper.class);

  SlotDTO toDTO(SlotEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "machine", ignore = true)
  @Mapping(target = "product", ignore = true)
  @Mapping(target = "quantity", ignore = true)
  SlotEntity toEntity(SlotCreationRequest request);
}
