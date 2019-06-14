package com.sergey.zhuravlev.pizza.entity;

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
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "employee_first_name", length = 60, nullable = false)
    private String firstName;

    @Column(name = "employee_last_name", length = 60, nullable = false)
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "fk_employee_address"))
    private Address address;

    @Column(name = "salary", nullable = false, precision = 14, scale = 2)
    private BigDecimal salary;

    @Column(name = "salary_currency", nullable = false, length = 3)
    private String salaryCurrency;

}
