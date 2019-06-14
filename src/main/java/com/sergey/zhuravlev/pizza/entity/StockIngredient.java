package com.sergey.zhuravlev.pizza.entity;

import com.sergey.zhuravlev.pizza.enums.StockType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_ingredients")
public class StockIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_ingredient_id")
    private Long id;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id", foreignKey = @ForeignKey(name = "fk_stock_ingredient_ingredient"))
    private Ingredient ingredient;

    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_type", nullable = false)
    private StockType stockType;

    @Column(name = "ingredient_count")
    private Long count;

    @Column(name = "ingredient_weight", precision = 14, scale = 2)
    private BigDecimal weight;

}
