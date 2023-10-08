package com.flapkap.vending.domain.machine.model;

import com.flapkap.vending.domain.slot.model.SlotEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "machines")
public class MachineEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String address;
  private String name;
  private double balance;
  private long maxSlots;

  @OneToMany(mappedBy = "machine")
  private List<SlotEntity> slots;

  private int fives;
  private int tens;
  private int twenties;
  private int fifties;
  private int hundreds;
}
