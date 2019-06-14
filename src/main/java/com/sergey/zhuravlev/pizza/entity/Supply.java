package com.sergey.zhuravlev.pizza.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "supply")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supply_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false, foreignKey = @ForeignKey(name = "fk_supply_supplier"))
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_supply_ingredient"))
    private Ingredient ingredient;

    @Column(name = "supply_time", nullable = false)
    private Long supplyTime;

    @Column(name = "supply_price", nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    @Column(name = "supply_currency", length = 3, nullable = false)
    private String currency;

}
