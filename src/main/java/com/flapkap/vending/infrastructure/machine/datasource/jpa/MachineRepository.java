package com.flapkap.vending.infrastructure.machine.datasource.jpa;

import com.flapkap.vending.domain.machine.model.MachineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<MachineEntity, Integer> {}
