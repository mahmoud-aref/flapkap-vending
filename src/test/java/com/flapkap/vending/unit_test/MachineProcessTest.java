package com.flapkap.vending.unit_test;

import com.flapkap.vending.domain.machine.model.MachineEntity;
import com.flapkap.vending.domain.machine.model.MachineMapper;
import com.flapkap.vending.domain.machine.service.impl.MachineServiceImpl;
import com.flapkap.vending.infrastructure.machine.datasource.jpa.MachineRepository;
import com.flapkap.vending.infrastructure.product.datasource.jpa.ProductRepository;
import com.flapkap.vending.infrastructure.slot.datasource.jpa.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MachineProcessTest {


}
