package com.flapkap.vending.domain.machine.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MachineMapper {

  MachineMapper INSTANCE = Mappers.getMapper(MachineMapper.class);

  MachineDTO toDTO(MachineEntity entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "balance", ignore = true)
  @Mapping(target = "slots", ignore = true)
  @Mapping(target = "maxSlots", ignore = true)
  @Mapping(target = "fives", ignore = true)
  @Mapping(target = "tens", ignore = true)
  @Mapping(target = "twenties", ignore = true)
  @Mapping(target = "fifties", ignore = true)
  @Mapping(target = "hundreds", ignore = true)
  MachineEntity toEntity(MachineCreationRequest request);
}
