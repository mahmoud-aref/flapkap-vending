package com.flapkap.vending.infrastructure.slot.datasource.jpa;

import com.flapkap.vending.domain.slot.model.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<SlotEntity, Integer> {}
