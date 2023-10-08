package com.flapkap.vending.domain.slot.model;

import com.flapkap.vending.domain.machine.model.MachineEntity;
import com.flapkap.vending.domain.product.model.ProductEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "slots")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "machine_id", nullable = false)
  private MachineEntity machine;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = true)
  private ProductEntity product;

  private int capacity;
  private int quantity;
}
